package io.lb.composetrivia.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.lb.composetrivia.model.Question
import io.lb.composetrivia.util.AppColors

@Composable
fun QuestionScreen(viewModel: QuestionViewModel = hiltViewModel()) {
    val questions = viewModel.data.value.data?.shuffled()
    val loading = viewModel.data.value.loading

    Scaffold(
        modifier = Modifier.padding(4.dp),
        backgroundColor = AppColors.mDarkPurple
    ) {
        if (loading == true) {
            CircularProgressIndicator()
        } else {
            questions?.let {
                QuestionsDisplay(questions)
            }
        }
    }
}

@Composable
private fun QuestionsDisplay(questions: List<Question>) {
    var position by remember {
        mutableStateOf(0)
    }

    val correctAnswer = remember(questions[position]) {
        mutableStateOf<Boolean?>(null)
    }

    val selectedAnswer = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        ShowProgress(position + 1)

        QuestionsCounter(position + 1, 10)
        DrawDottedLine(
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(10f, 10f),
                0f
            )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DisplayQuestionAndChoices(
                question = questions[position],
                correctAnswer = correctAnswer,
                selectedAnswer = selectedAnswer
            ) {
                if (position >= 9) {
                    return@DisplayQuestionAndChoices
                }

                position++
            }
        }
    }
}

@Composable
private fun DisplayQuestionAndChoices(
    question: Question,
    correctAnswer: MutableState<Boolean?>,
    selectedAnswer: MutableState<String>,
    onClickButton: () -> Unit,
) {
    Text(
        text = question.question,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxHeight(0.3f),
        color = AppColors.mOffWhite,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 22.sp
    )

    LazyColumn {
        items(question.choices) { choice ->
            ChoiceRow(
                choice = choice,
                selectedAnswer = selectedAnswer.value,
                correctedAnswer = correctAnswer.value == true
            ) {
                selectedAnswer.value = it
                correctAnswer.value = selectedAnswer.value == question.answer
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
    Button(onClick = { onClickButton() }) {
        Text(text = "Next")
    }
}

@Composable
private fun ChoiceRow(
    choice: String,
    selectedAnswer: String,
    correctedAnswer: Boolean,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(40.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(15.dp),
                brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.mOffDarkPurple,
                        AppColors.mOffDarkPurple
                    ),
                )
            )
            .clip(RoundedCornerShape(percent = 5))
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.padding(horizontal = 12.dp),
            selected = choice == selectedAnswer,
            colors = RadioButtonDefaults.colors(
                colorAccordingToAnswer(correctedAnswer, choice == selectedAnswer)
            ),
            onClick = {
                onClick(choice)
            }
        )
        Text(
            text = choice,
            color = colorAccordingToAnswer(correctedAnswer, choice == selectedAnswer),
        )
    }
}

@Composable
private fun colorAccordingToAnswer(
    correctedAnswer: Boolean,
    isSelected: Boolean
): Color {
    if (!isSelected) {
        return AppColors.mOffWhite
    } else if (correctedAnswer) {
        return Color.Green.copy(alpha = 0.8f)
    }

    return Color.Red.copy(alpha = 0.8f)
}

@Composable
fun QuestionsCounter(count: Int, outOff: Int) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                ) {
                    append("Question $count/$outOff")
                }
            }
        }
    )
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        onDraw = {
            drawLine(
                color = AppColors.mLightGray,
                start = Offset.Zero,
                end = Offset(size.width, 0f),
                pathEffect = pathEffect
            )
        }
    )
}

@Composable
fun ShowProgress(score: Int) {
    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)))

    val scoreMeter by remember(score) {
        mutableStateOf(score * 0.1f)
    }

    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(AppColors.mLightPurple, AppColors.mLightPurple)),
                shape = RoundedCornerShape(34.dp)
        ).clip(RoundedCornerShape(percent = 50))
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.fillMaxWidth(scoreMeter)
                .background(brush = gradient),
            ) {
            Text(
                text = (score * 10).toString(),
                modifier = Modifier.clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center)
        }
    }
}