package gre.prep.grevocabwords.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import gre.prep.grevocabwords.DefinitionResponseItem
import gre.prep.grevocabwords.Resource
import gre.prep.grevocabwords.WordData
import gre.prep.grevocabwords.WordDetailsViewModel

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
        wordDetailsViewModel.getWordDetails(word ?: return@LaunchedEffect)
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
            wordDetailsViewModel.insertWord(definition.value.data ?: return)
        }
        is Resource.Uninitialised -> {
            //nothing here
        }
    }
}

@Composable
fun WordDetails(currentWord: DefinitionResponseItem?) {
    //show error view ideally
    currentWord ?: return
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {

            Column {
                Text(
                    text = buildAnnotatedString {
                        append(currentWord.word + "   ")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Blue,
                                fontWeight = FontWeight.Light,
                                fontStyle = FontStyle.Italic,
                                fontSize = 24.sp,
                            )
                        ) {
                            append((currentWord.phonetic ?: ""))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    style = MaterialTheme.typography.h1,
                )
                Divider()
                currentWord.meanings.forEach { meaning ->
                    meaning.definitions.forEachIndexed { index, currentDefinition ->
                        Text(
                            text = (index + 1).toString() + ". " + currentDefinition.definition,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp)
                        )
                        SynonymRow(currentDefinition.synonyms)
                        if(index == meaning.definitions.lastIndex && currentDefinition.synonyms.isNotEmpty()) Spacer(modifier = Modifier.height(8.dp))
                    }
                    Divider()
                }
            }
    }
}

@Composable
fun SynonymRow(words: List<String>) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(start = 20.dp)
    ) {
        words.forEach { word ->
            Text(
                text = word,
                fontSize = 18.sp,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.Blue),
                        shape = CircleShape
                    )
                    .padding(horizontal = 8.dp, vertical = 3.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}