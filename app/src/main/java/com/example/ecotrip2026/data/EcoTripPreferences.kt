package com.example.ecotrip2026.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "ecotrip_preferences"
)

class EcoTripPreferences(
    private val context: Context
) {

    private object Keys {

        val NOMBRE_VIAJERO =
            stringPreferencesKey("nombre_viajero")

        val TIPO_TRANSPORTE =
            stringPreferencesKey("tipo_transporte")

        val BAJA_HUELLA =
            booleanPreferencesKey("baja_huella_carbono")
    }

    val nombreViajero: Flow<String> =
        context.dataStore.data.map { preferences ->

            preferences[Keys.NOMBRE_VIAJERO] ?: ""
        }

    val tipoTransporte: Flow<String> =
        context.dataStore.data.map { preferences ->

            preferences[Keys.TIPO_TRANSPORTE] ?: "Tren"
        }

    val bajaHuellaCarbono: Flow<Boolean> =
        context.dataStore.data.map { preferences ->

            preferences[Keys.BAJA_HUELLA] ?: false
        }

    suspend fun guardarPreferencias(
        preferencia: PreferenciaViaje
    ) {

        context.dataStore.edit { preferences ->

            preferences[Keys.NOMBRE_VIAJERO] =
                preferencia.nombreViajero

            preferences[Keys.TIPO_TRANSPORTE] =
                preferencia.tipoTransporte

            preferences[Keys.BAJA_HUELLA] =
                preferencia.bajaHuellaCarbono
        }
    }
}