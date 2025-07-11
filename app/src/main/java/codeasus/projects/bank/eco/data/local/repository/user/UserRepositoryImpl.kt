package codeasus.projects.bank.eco.data.local.repository.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import codeasus.projects.bank.eco.data.local.repository.adapter.LocalDateTimeAdapter
import codeasus.projects.bank.eco.domain.local.model.user.UserModel
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserRepositoryImpl(private val context: Context) : UserRepository {
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    private val userData = stringPreferencesKey("user_data")

    override suspend fun saveUser(user: UserModel) {
        val jsonString = gson.toJson(user)
        context.dataStore.edit { prefs -> prefs[userData] = jsonString }
    }

    override suspend fun loadUser(): UserModel? {
        val prefs = context.dataStore.data.first()
        return prefs[userData]?.let {
            val type = object : TypeToken<UserModel>() {}.type
            gson.fromJson(it, type)
        }
    }
}