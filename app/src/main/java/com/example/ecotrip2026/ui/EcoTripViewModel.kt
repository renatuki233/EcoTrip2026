package com.example.ecotrip2026.ui

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ecotrip2026.data.EcoTripPreferences
import com.example.ecotrip2026.data.PreferenciaViaje
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EcoTripUiState(

    val nombreViajero: String = "",

    val destino: String = "",

    val diasDuracion: String = "",

    val tipoTransporte: String = "Tren",

    val bajaHuellaCarbono: Boolean = false
)

class EcoTripViewModel(

    private val savedStateHandle: SavedStateHandle,

    private val preferences: EcoTripPreferences

) : ViewModel() {

    private val _uiState = MutableStateFlow(

        EcoTripUiState(

            destino =
            savedStateHandle["destino"] ?: "",

            diasDuracion =
            savedStateHandle["dias_duracion"] ?: ""
        )
    )

    val uiState: StateFlow<EcoTripUiState> =
        _uiState.asStateFlow()

    init {

        viewModelScope.launch {

            combine(
                preferences.nombreViajero,
                preferences.tipoTransporte,
                preferences.bajaHuellaCarbono
            ) { nombre, transporte, bajaHuella ->

                Triple(
                    nombre,
                    transporte,
                    bajaHuella
                )

            }.collect { datos ->

                _uiState.update { actual ->

                    actual.copy(
                        nombreViajero = datos.first,
                        tipoTransporte = datos.second,
                        bajaHuellaCarbono = datos.third
                    )
                }
            }
        }
    }

    fun actualizarNombre(
        nuevoNombre: String
    ) {

        _uiState.update {
            it.copy(
                nombreViajero = nuevoNombre
            )
        }

        guardarPreferencias()
    }

    fun actualizarDestino(
        nuevoDestino: String
    ) {

        savedStateHandle["destino"] =
            nuevoDestino

        _uiState.update {
            it.copy(
                destino = nuevoDestino
            )
        }
    }

    fun actualizarDias(
        nuevosDias: String
    ) {

        val valorSeguro =
            if (nuevosDias.isBlank()) {
                ""
            } else {
                nuevosDias.toIntOrNull()
                    ?.toString()
                    ?: return
            }

        savedStateHandle["dias_duracion"] =
            valorSeguro

        _uiState.update {
            it.copy(
                diasDuracion = valorSeguro
            )
        }
    }

    fun actualizarTransporte(
        nuevoTransporte: String
    ) {

        _uiState.update {
            it.copy(
                tipoTransporte = nuevoTransporte
            )
        }

        guardarPreferencias()
    }

    fun actualizarBajaHuella(
        nuevoValor: Boolean
    ) {

        _uiState.update {
            it.copy(
                bajaHuellaCarbono = nuevoValor
            )
        }

        guardarPreferencias()
    }

    private fun guardarPreferencias() {

        val estadoActual =
            _uiState.value

        viewModelScope.launch {

            preferences.guardarPreferencias(

                PreferenciaViaje(
                    nombreViajero =
                    estadoActual.nombreViajero,

                    tipoTransporte =
                    estadoActual.tipoTransporte,

                    bajaHuellaCarbono =
                    estadoActual.bajaHuellaCarbono
                )
            )
        }
    }
}

fun ecoTripViewModelFactory(
    context: Context
): ViewModelProvider.Factory =

    viewModelFactory {

        initializer {

            EcoTripViewModel(

                savedStateHandle =
                createSavedStateHandle(),

                preferences =
                EcoTripPreferences(
                    context.applicationContext
                )
            )
        }
    }