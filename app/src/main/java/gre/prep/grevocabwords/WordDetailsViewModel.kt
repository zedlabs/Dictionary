package gre.prep.grevocabwords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gre.prep.grevocabwords.db.WordDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WordDetailsViewModel @Inject constructor(
    val repository: WordDetailsRepository,
    val wordDao: WordDao,
) : ViewModel() {

    private val _definitionState =
        MutableStateFlow<Resource<DefinitionResponseItem>>(Resource.Uninitialised())
    val definitionState = _definitionState.asStateFlow()

    private val _dbWordState =
        MutableStateFlow<Resource<DefinitionResponseItem>>(Resource.Uninitialised())
    val dbWordState = _dbWordState.asStateFlow()

//    fun getDefinition(word: String) {
//        _dbWordState.value = Resource.Loading()
//        viewModelScope.launch {
//            _dbWordState.value =
//                repository.getWordDefinition(word = word)
//        }
//    }

    fun insertWord(item: DefinitionResponseItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (wordDao.findWords(item.word).isEmpty()) wordDao.insertAll(item)
            }
        }
    }

    fun getWordDetails(word: String) {
       viewModelScope.launch {
           withContext(Dispatchers.IO) {
               val result = wordDao.findWords(word)
               // if the word is present in the database
               if(result.isNotEmpty()) {
                   _definitionState.value = Resource.Success(data = result.first())
               }
               // if the word is not present in the database,
               // then load from network -> update state flow -> add to database
               else {
                   val definition = async {
                       repository.getWordDefinition(word = word)
                   }
                   val apiResult = definition.await().data?.first() ?: return@withContext
                   _definitionState.value = Resource.Success(data = apiResult)
                   insertWord(apiResult)
               }
           }
       }
    }

}