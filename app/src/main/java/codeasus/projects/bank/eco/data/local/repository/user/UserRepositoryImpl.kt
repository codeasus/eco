package codeasus.projects.bank.eco.data.local.repository.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import codeasus.projects.bank.eco.domain.local.model.user.UserModel
import codeasus.projects.bank.eco.domain.local.repository.user.UserRepository
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserRepositoryImpl(private val context: Context) : UserRepository {
    private val name = stringPreferencesKey("name")
    private val tagName = stringPreferencesKey("tag_name")
    private val profileImageResId = stringPreferencesKey("profile_image_res_id")

    override suspend fun saveUser(user: UserModel) {
        context.dataStore.edit { prefs ->
            prefs[name] = user.name
            prefs[tagName] = user.tagName
            prefs[profileImageResId] = user.profileImageResId.toString()
        }
    }

    override suspend fun loadUser(): UserModel {
        return UserModel(
            name = context.dataStore.data.first()[name] ?: "",
            tagName = context.dataStore.data.first()[tagName] ?: "",
            profileImageResId = context.dataStore.data.first()[profileImageResId]?.toInt() ?: 0
        )
    }
}