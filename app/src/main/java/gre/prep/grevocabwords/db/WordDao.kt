package gre.prep.grevocabwords.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import gre.prep.grevocabwords.DefinitionResponseItem

@Dao
interface WordDao {
    @Query("SELECT * FROM definitionTable")
    fun getAll(): List<DefinitionResponseItem>

    @Insert
    fun insertAll(word: DefinitionResponseItem)

    @Query("SELECT * FROM definitionTable WHERE word = :target")
    fun findWords(target: String): List<DefinitionResponseItem>
}