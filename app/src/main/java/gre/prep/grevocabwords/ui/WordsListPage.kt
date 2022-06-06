package gre.prep.grevocabwords.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gre.prep.grevocabwords.WordData.indexGroupMap

@Composable
fun GroupWordList(navController: NavController, listPosition: String) {
    val wordList = indexGroupMap[listPosition.toInt() + 1] ?: return
    Scaffold(topBar = {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "Words in this Group : ${wordList.size} ✌",
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            style = MaterialTheme.typography.h1,
        )
    }) {
        LazyColumn {
            items(count = wordList.size) { index ->
                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("word_details/${listPosition}/${index}")
                        }
                        .fillMaxWidth()
                        .border(1.dp, Color.Black)
                        .padding(12.dp),
                    text = wordList[index],
                    style = MaterialTheme.typography.h2,
                    fontSize = 24.sp
                )
            }
        }
    }

}