package io.lb.composemovies.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import io.lb.composemovies.model.Movie

@ExperimentalAnimationApi
@Composable
fun MovieRow(
    movie: Movie,
    onItemClick: (String) -> Unit = {}
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = CircleShape,
                elevation = 4.dp) {

                movie.images.takeIf {
                    !it.isNullOrEmpty()
                }?.let {
                    Image(
                        painter = rememberImagePainter(
                            data = it[0],
                            builder = {
                                crossfade(true)
                                transformations(CircleCropTransformation())
                            }
                        ),
                        contentDescription = "${movie.title} poster",
                    )
                } ?: DefaultIcon()
            }
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .width(150.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "Director: ${movie.director}",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "Release: ${movie.year}",
                    style = MaterialTheme.typography.caption
                )

                AnimatedVisibility(visible = isExpanded.value) {
                    ExpandedContent(movie)
                }
            }
            Spacer(modifier = Modifier.width(50.dp))
            Icon(
                modifier = Modifier
                    .size(25.dp)
                    .clickable { isExpanded.value = !isExpanded.value },
                imageVector = if (isExpanded.value) {
                    Icons.Default.KeyboardArrowRight
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                contentDescription = "Expand arrow",
                tint = Color.DarkGray
            )
        }
    }
}

@Composable
private fun ExpandedContent(movie: Movie) {
    Divider(modifier = Modifier.padding(8.dp).height(1.dp))
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    Color.DarkGray,
                    fontSize = 13.sp,
                )
            ) {
                append("Plot: ")
            }
            withStyle(
                style = SpanStyle(
                    Color.DarkGray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
            ) {
                append(movie.plot)
            }
        },
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(8.dp),
    )
}

@Composable
private fun DefaultIcon() {
    Icon(
        imageVector = Icons.Default.AccountBox,
        contentDescription = "Movie Image"
    )
}