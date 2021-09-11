package com.sohail.jetktor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.sohail.jetktor.model.FreeGamesItem
import com.sohail.jetktor.ui.theme.JetKtorTheme
import com.sohail.jetktor.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        setContent {
            JetKtorTheme {
                GameApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun GameApp(viewModel: GameViewModel) {
    Scaffold(
        content = {
            FreeGames(viewModel = viewModel)
        }
    )
}

@Composable
fun FreeGames(viewModel: GameViewModel) {
    val freeGames by viewModel.freeGamesFlow.collectAsState()
    freeGames?.let {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp,vertical = 16.dp)
        ) {
           items(items = it) { game ->
               GameItem(game = game)
           }
        }
    }
}

@Composable
fun GameItem(game: FreeGamesItem) {
    val context = LocalContext.current
    val webViewIntent = Intent(context,WebViewActivity::class.java)
    webViewIntent.putExtra("url",game.freetogameProfileUrl)
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                context.startActivity(webViewIntent)
            }
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(8.dp))
    ) {
        Row {
            ImageCompo(game = game)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = game.title, style = typography.h6)
                Text(text = game.developer, style = typography.caption)
            }
        }
    }
}

@Composable
fun ImageCompo(game: FreeGamesItem) {
    Image(
        painter = rememberImagePainter(game.thumbnail),
        contentDescription = null ,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}
