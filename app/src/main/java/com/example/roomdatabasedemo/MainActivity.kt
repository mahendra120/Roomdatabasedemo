package com.example.roomdatabasedemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.roomdatabasedemo.model.User
import com.example.roomdatabasedemo.model.UserDatabase
import com.example.roomdatabasedemo.ui.theme.RoomdatabasedemoTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val userList = mutableStateListOf<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        GlobalScope.launch {
            val db = UserDatabase.getDatabase(this@MainActivity)
            val list = db.userDao().getUsers()
            userList.addAll(list)
        }

        setContent {
            RoomdatabasedemoTheme {
//               home()

                var currentRotation by remember { mutableStateOf(0f) }
                val rotation = remember { Animatable(currentRotation) }

                LaunchedEffect(true){
                    rotation.animateTo(
                        targetValue = currentRotation + 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(5000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        )
                    ) {
                        currentRotation = value
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.bg),
                        contentDescription = null,
                        modifier = Modifier
                            .size(400.dp)
                            .rotate(currentRotation)
                    )
                }

            }
        }
    }

    @Composable
    private fun home() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    val intent = Intent(this@MainActivity, AddActivity::class.java)
                    startActivity(intent)
                    finish()
                }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }) { innerPadding ->

            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(userList.size) {
                    val model = userList[it]
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(model.name, modifier = Modifier.padding(horizontal = 15.dp))
                        Text(model.phone, modifier = Modifier.padding(horizontal = 15.dp))
                    }
                }
            }
        }
    }

}
