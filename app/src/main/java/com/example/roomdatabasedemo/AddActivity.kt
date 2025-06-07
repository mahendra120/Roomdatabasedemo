package com.example.roomdatabasedemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.roomdatabasedemo.model.User
import com.example.roomdatabasedemo.model.UserDatabase
import com.example.roomdatabasedemo.ui.theme.RoomdatabasedemoTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddActivity : ComponentActivity() {

    var name by mutableStateOf("")
    var phone by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomdatabasedemoTheme {
                home()
            }
        }
    }

    @Composable
    private fun home() {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("enter name") },
                    modifier = Modifier.padding(10.dp)
                )
                TextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("enter phone") },
                    modifier = Modifier.padding(10.dp)
                )
                ElevatedButton(onClick = {
                    addUser()
                }) {
                    Text("Add")
                }

            }
        }
    }

    fun addUser() {

        val model = User(name = name, phone = phone)
        val db = UserDatabase.getDatabase(this@AddActivity)

        GlobalScope.launch {
            db.userDao().addUser(model)

            val intent = Intent(this@AddActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}
