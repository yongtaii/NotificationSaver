package com.rnd.jyong.notificationsaver.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.appConfigDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_config")

object DataStoreManager {
    suspend fun save(context: Context, key: String, value: String) {

        val wrappedKey = stringPreferencesKey(key)
        context.appConfigDataStore.edit {
            it[wrappedKey] = value
        }

    }

    suspend fun save(context: Context, key: String, value: Int) {

        val wrappedKey = intPreferencesKey(key)
        context.appConfigDataStore.edit {
            it[wrappedKey] = value
        }

    }

    suspend fun save(context: Context, key: String, value: Double) {

        val wrappedKey = doublePreferencesKey(key)
        context.appConfigDataStore.edit {
            it[wrappedKey] = value
        }

    }

    suspend fun save(context: Context, key: String, value: Long) {

        val wrappedKey = longPreferencesKey(key)
        context.appConfigDataStore.edit {
            it[wrappedKey] = value
        }

    }

    suspend fun save(context: Context, key: String, value: Boolean) {

        val wrappedKey = booleanPreferencesKey(key)
        context.appConfigDataStore.edit {
            it[wrappedKey] = value
        }

    }

    suspend fun get(context: Context, key: String, default: String = ""): String {
        val wrappedKey = stringPreferencesKey(key)
        val valueFlow: Flow<String> = context.appConfigDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }

    suspend fun get(context: Context, key: String, default: Int = 0): Int {
        val wrappedKey = intPreferencesKey(key)
        val valueFlow: Flow<Int> = context.appConfigDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }

    suspend fun get(context: Context, key: String, default: Double = 0.0): Double {
        val wrappedKey = doublePreferencesKey(key)
        val valueFlow: Flow<Double> = context.appConfigDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }

    suspend fun get(context: Context, key: String, default: Long = 0L): Long {
        val wrappedKey = longPreferencesKey(key)
        val valueFlow: Flow<Long> = context.appConfigDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }

    suspend fun get(context: Context, key: String, default: Boolean = false): Boolean {
        val wrappedKey = booleanPreferencesKey(key)
        val valueFlow: Flow<Boolean> = context.appConfigDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }
}