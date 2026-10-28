package com.example.ecotrip2026.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenScreen(
    nombreViajero: String,
    destino: String,
    diasDuracion: Int,
    tipoTransporte: String,
    bajaHuellaCarbono: Boolean,
    onSalir: () -> Unit
) {

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Resumen de Ruta")
                },

                actions = {

                    TextButton(
                        onClick = onSalir
                    ) {
                        Text("Salir")
                    }
                },

                colors =
                TopAppBarDefaults.topAppBarColors(

                    containerColor =
                    MaterialTheme.colorScheme.primaryContainer,

                    titleContentColor =
                    MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),

            verticalArrangement =
            Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Viaje configurado",
                style =
                MaterialTheme.typography.headlineSmall
            )

            Card(
                modifier =
                Modifier.fillMaxWidth(),

                colors =
                CardDefaults.cardColors(
                    containerColor =
                    MaterialTheme.colorScheme.secondaryContainer
                )
            ) {

                Column(
                    modifier =
                    Modifier.padding(20.dp),

                    verticalArrangement =
                    Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text =
                        "Viajero: $nombreViajero",
                        style =
                        MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text =
                        "Destino: $destino",
                        style =
                        MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text =
                        "Duración: $diasDuracion días",
                        style =
                        MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text =
                        "Transporte: $tipoTransporte",
                        style =
                        MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text =
                        "Ruta de baja huella: ${
                            if (bajaHuellaCarbono) {
                                "Sí"
                            } else {
                                "No"
                            }
                        }",
                        style =
                        MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}