package gre.prep.grevocabwords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailsViewModel @Inject constructor(
    val repository: WordDetailsRepository
): ViewModel() {

    private val _definitionState =
        MutableStateFlow<Resource<List<DefinitionResponseItem>>>(Resource.Uninitialised())
    val definitionState = _definitionState.asStateFlow()

    fun getDefinition(word: String) {
        _definitionState.value = Resource.Loading()
        viewModelScope.launch {
            _definitionState.value =
                repository.getWordDefinition(word = word)
        }
    }

}