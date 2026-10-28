package com.example.ecotrip2026.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ecotrip2026.ui.EcoTripUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    uiState: EcoTripUiState,
    onNombreChange: (String) -> Unit,
    onDestinoChange: (String) -> Unit,
    onDiasChange: (String) -> Unit,
    onTransporteChange: (String) -> Unit,
    onBajaHuellaChange: (Boolean) -> Unit,
    onContinuar: (
        nombre: String,
        destino: String,
        dias: Int,
        transporte: String,
        bajaHuella: Boolean
    ) -> Unit
) {

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("EcoTrip 2026")
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =
                    MaterialTheme.colorScheme.primaryContainer,

                    titleContentColor =
                    MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    val diasSeguros =
                        uiState.diasDuracion.toIntOrNull()

                    val nombreSeguro =
                        uiState.nombreViajero.trim()

                    val destinoSeguro =
                        uiState.destino.trim()

                    if (
                        nombreSeguro.isNotEmpty() &&
                        destinoSeguro.isNotEmpty() &&
                        diasSeguros != null &&
                        diasSeguros > 0
                    ) {

                        onContinuar(
                            nombreSeguro,
                            destinoSeguro,
                            diasSeguros,
                            uiState.tipoTransporte,
                            uiState.bajaHuellaCarbono
                        )
                    }
                },

                containerColor =
                MaterialTheme.colorScheme.secondaryContainer,

                contentColor =
                MaterialTheme.colorScheme.onSecondaryContainer
            ) {

                Text("→")
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                ),

            verticalArrangement =
            Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Configuración del Viaje",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Ingrese los datos de su ruta ecológica.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            OutlinedTextField(
                value = uiState.nombreViajero,

                onValueChange = onNombreChange,

                label = {
                    Text("Nombre del viajero")
                },

                modifier =
                Modifier.fillMaxWidth(),

                singleLine = true
            )

            OutlinedTextField(
                value = uiState.destino,

                onValueChange = onDestinoChange,

                label = {
                    Text("Destino")
                },

                modifier =
                Modifier.fillMaxWidth(),

                singleLine = true
            )

            OutlinedTextField(
                value = uiState.diasDuracion,

                onValueChange = onDiasChange,

                label = {
                    Text("Duración en días")
                },

                keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                    KeyboardType.Number
                ),

                modifier =
                Modifier.fillMaxWidth(),

                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Transporte ecológico",
                style = MaterialTheme.typography.titleMedium
            )

            OpcionTransporte(
                texto = "Tren",
                seleccionado =
                uiState.tipoTransporte == "Tren",
                onSeleccionar = {
                    onTransporteChange("Tren")
                }
            )

            OpcionTransporte(
                texto = "Bicicleta",
                seleccionado =
                uiState.tipoTransporte == "Bicicleta",
                onSeleccionar = {
                    onTransporteChange("Bicicleta")
                }
            )

            OpcionTransporte(
                texto = "Vehículo eléctrico",
                seleccionado =
                uiState.tipoTransporte ==
                        "Vehículo eléctrico",
                onSeleccionar = {
                    onTransporteChange(
                        "Vehículo eléctrico"
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Row(
                modifier =
                Modifier.fillMaxWidth(),

                verticalAlignment =
                Alignment.CenterVertically,

                horizontalArrangement =
                Arrangement.SpaceBetween
            ) {

                Text(
                    text =
                    "Solo rutas de baja huella",
                    style =
                    MaterialTheme.typography.bodyLarge
                )

                Switch(
                    checked =
                    uiState.bajaHuellaCarbono,

                    onCheckedChange =
                    onBajaHuellaChange
                )
            }

            Spacer(
                modifier = Modifier.height(80.dp)
            )
        }
    }
}

@Composable
private fun OpcionTransporte(
    texto: String,
    seleccionado: Boolean,
    onSeleccionar: () -> Unit
) {

    Row(
        verticalAlignment =
        Alignment.CenterVertically
    ) {

        RadioButton(
            selected = seleccionado,
            onClick = onSeleccionar
        )

        Text(
            text = texto
        )
    }
}