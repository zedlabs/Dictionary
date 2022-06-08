package gre.prep.grevocabwords.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gre.prep.grevocabwords.DefinitionResponseItem
import gre.prep.grevocabwords.DefinitionTypeConvertor
import gre.prep.grevocabwords.MeaningTypeConvertor

@Database(entities = [DefinitionResponseItem::class], version = 2)
@TypeConverters(MeaningTypeConvertor::class, DefinitionTypeConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}