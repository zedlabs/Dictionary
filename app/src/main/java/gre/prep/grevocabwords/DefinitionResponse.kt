package gre.prep.grevocabwords

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "definitionTable")
data class DefinitionResponseItem(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val meanings: List<Meaning>,
    val phonetic: String?,
    val word: String
)
data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String?,
    val synonyms: List<String>
)

data class Definition(
    val antonyms: List<String>,
    val definition: String?,
    val example: String?,
    val synonyms: List<String>
)

class MeaningTypeConvertor {
    private val gson: Gson = Gson()
    private val type: Type = object : TypeToken<List<Meaning?>?>() {}.type

    @TypeConverter
    fun stringToMeaning(json: String?): List<Meaning> {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun meaningToString(nestedData: List<Meaning>): String {
        return gson.toJson(nestedData, type)
    }
}

class DefinitionTypeConvertor {
    private val gson: Gson = Gson()
    private val type: Type = object : TypeToken<List<Definition?>?>() {}.type

    @TypeConverter
    fun stringToDefinition(json: String?): List<Definition> {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun definitionToString(nestedData: List<Definition>): String {
        return gson.toJson(nestedData, type)
    }
}