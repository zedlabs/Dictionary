package gre.prep.grevocabwords.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import gre.prep.grevocabwords.*

@Composable
fun WordDetailsPage(
    navController: NavController,
    groupNumber: String?,
    itemPosition: String?,
    wordDetailsViewModel: WordDetailsViewModel = hiltViewModel(),
) {
    val definition = wordDetailsViewModel.definitionState.collectAsState()
    LaunchedEffect(true) {
        val wordGroup = groupNumber?.toIntOrNull()?.plus(1) ?: 0
        val word = WordData.indexGroupMap[wordGroup]?.get(itemPosition?.toInt() ?: 0)
        wordDetailsViewModel.getDefinition(word ?: return@LaunchedEffect)
    }
    when (definition.value) {
        is Resource.Error -> {
            //show Error
            WordDetails(definition.value.data)
        }
        is Resource.Loading -> {
            //show shimmer
        }
        is Resource.Success -> {
            WordDetails(definition.value.data)
        }
        is Resource.Uninitialised -> {
            //nothing here
        }
    }
}

@Composable
fun WordDetails(data: List<DefinitionResponseItem>?) {
    //show error view ideally
    data ?: return
    
    Column {
        data.forEach { currentWord ->
            Column {
                Text(text = currentWord.word)
                currentWord.meanings.forEach { meaning ->
                    meaning.definitions.forEach { currentDefinition ->
                        Text(text = currentDefinition.definition)
                        SynonymRow(currentDefinition.synonyms)
                        SynonymRow(currentDefinition.antonyms)
                    }
                }
            }
        }
    }
}

@Composable
fun SynonymRow(words: List<String>) {
    Row {
       words.forEach { word ->
           Text(text = word)
       }
    }
}