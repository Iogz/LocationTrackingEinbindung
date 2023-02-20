package com.example.locationtrackingeinbindung

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.viewModelFactory


@Composable
fun HomeScreen () {


    val bottomState by remember { mutableStateOf("LearnToGo" ) }


    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    },
        /** Weiterleitung führt bei allen zu New Note, muss überarbeitet werden*/
        /** Weiterleitung führt bei allen zu New Note, muss überarbeitet werden*/
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
            ) {
                BottomNavigationItem(
                    selected = bottomState == "Home",
                    onClick = { },
                    label = { Text(text = "Home") },
                    icon = {Icon(imageVector = Icons.Default.Home, contentDescription = null)}
                )

                BottomNavigationItem(
                    selected = bottomState == "Account",
                    onClick = { },
                    label = { Text(text = "Account") },
                    icon = {Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)}
                )

                BottomNavigationItem(
                    selected = bottomState == "Location",
                    onClick = { },
                    label = { Text(text = "Location") },
                    icon = {Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)}
                )
            }
        }
    ) {
        Column {
            Text ("Irmak")
        }
    }
}