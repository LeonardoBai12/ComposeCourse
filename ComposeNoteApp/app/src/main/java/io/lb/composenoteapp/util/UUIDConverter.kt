package io.lb.composenoteapp.util

import androidx.room.TypeConverter
import java.util.*

class UUIDConverter {
    @TypeConverter
    fun uuidFromString(string: String?): UUID {
        return UUID.fromString(string)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }
}