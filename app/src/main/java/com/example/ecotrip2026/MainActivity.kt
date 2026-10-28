package com.example.ecotrip2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ecotrip2026.navigation.FormularioRoute
import com.example.ecotrip2026.navigation.ResumenRoute
import com.example.ecotrip2026.ui.EcoTripViewModel
import com.example.ecotrip2026.ui.ecoTripViewModelFactory
import com.example.ecotrip2026.ui.screens.FormularioScreen
import com.example.ecotrip2026.ui.screens.ResumenScreen
import com.example.ecotrip2026.ui.theme.EcoTrip2026Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            EcoTrip2026Theme {

                val navController =
                    rememberNavController()

                val ecoTripViewModel:
                        EcoTripViewModel =
                    viewModel(
                        factory =
                        ecoTripViewModelFactory(
                            applicationContext
                        )
                    )

                val uiState by
                ecoTripViewModel
                    .uiState
                    .collectAsState()

                NavHost(
                    navController =
                    navController,

                    startDestination =
                    FormularioRoute
                ) {

                    composable<FormularioRoute> {

                        FormularioScreen(

                            uiState = uiState,

                            onNombreChange =
                            ecoTripViewModel::actualizarNombre,

                            onDestinoChange =
                            ecoTripViewModel::actualizarDestino,

                            onDiasChange =
                            ecoTripViewModel::actualizarDias,

                            onTransporteChange =
                            ecoTripViewModel::actualizarTransporte,

                            onBajaHuellaChange =
                            ecoTripViewModel::actualizarBajaHuella,

                            onContinuar = {
                                    nombre,
                                    destino,
                                    dias,
                                    transporte,
                                    bajaHuella ->

                                navController.navigate(

                                    ResumenRoute(
                                        nombreViajero =
                                        nombre,

                                        destino =
                                        destino,

                                        diasDuracion =
                                        dias,

                                        tipoTransporte =
                                        transporte,

                                        bajaHuellaCarbono =
                                        bajaHuella
                                    )
                                ) {

                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable<ResumenRoute> {
                            backStackEntry ->

                        val resumen:
                                ResumenRoute =
                            backStackEntry.toRoute()

                        ResumenScreen(

                            nombreViajero =
                            resumen.nombreViajero,

                            destino =
                            resumen.destino,

                            diasDuracion =
                            resumen.diasDuracion,

                            tipoTransporte =
                            resumen.tipoTransporte,

                            bajaHuellaCarbono =
                            resumen.bajaHuellaCarbono,

                            onSalir = {

                                navController.navigate(
                                    FormularioRoute
                                ) {

                                    popUpTo(
                                        FormularioRoute
                                    ) {
                                        inclusive = true
                                    }

                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}