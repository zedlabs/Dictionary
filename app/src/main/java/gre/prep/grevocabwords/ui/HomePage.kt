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
import gre.prep.grevocabwords.Constants.GROUP_COUNT


@Composable
fun HomePageWordList(navController: NavController) {
    Scaffold(topBar = {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "GRE Word Groups🏌️‍♂",
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            style = MaterialTheme.typography.h1,
        )
    }) {
        LazyColumn {
            items(count = GROUP_COUNT) { index ->
                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("word_list/${index}")
                        }
                        .fillMaxWidth()
                        .border(2.dp, Color.Black)
                        .padding(12.dp),
                    text = "Group #${index + 1}",
                    style = MaterialTheme.typography.h2,
                    fontSize = 24.sp
                )
            }
        }
    }
}

