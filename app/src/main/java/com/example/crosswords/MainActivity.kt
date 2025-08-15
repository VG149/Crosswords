package com.example.crossword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.crosswords.ui.theme.CrosswordTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrosswordTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CrosswordScreen()
                }
            }
        }
    }
}

@Composable
fun CrosswordScreen() {
    CrosswordGrid()
}

@Composable
fun CrosswordGrid() {
    val solution = listOf(
        listOf('K','O','T','L','I'),
        listOf('A',' ',' ',' ',' '),
        listOf('V',' ',' ',' ',' '),
        listOf('A',' ',' ',' ',' '),
        listOf(' ',' ',' ',' ',' ')
    )
    val rows = solution.size
    val cols = solution[0].size
    val state = remember {
        MutableList(rows) {
            MutableList(cols) { mutableStateOf("") }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        for (r in 0 until rows) {
            Row {
                for (c in 0 until cols) {
                    val char = solution[r][c]
                    if (char == ' ') {
                        Spacer(modifier = Modifier
                            .size(40.dp)
                            .padding(1.dp))
                    } else {
                        TextField(
                            value = state[r][c].value,
                            onValueChange = {
                                if (it.length <= 1 && it.all { ch -> ch.isLetter() }) {
                                    state[r][c].value = it.uppercase()
                                }
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .padding(1.dp)
                                .border(1.dp, Color.Black),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                var correct = true
                for (r in 0 until rows) for (c in 0 until cols) {
                    if (solution[r][c] != ' ') {
                        val entered = state[r][c].value
                        if (entered.isEmpty() || entered[0] != solution[r][c]) {
                            correct = false
                        }
                    }
                }
                val msg = if (correct) "Parabéns! Está correto." else "Tente novamente!"
                println(msg)
            }
        ) {
            Text("Verificar")
        }
    }
}
