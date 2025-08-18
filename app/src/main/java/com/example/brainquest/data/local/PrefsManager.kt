package com.example.brainquest.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class PrefsManager @Inject constructor(
    // Injeta o contexto da aplicação de forma segura, sem risco de memory leaks.
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Companion object para manter as chaves de forma organizada e evitar erros de digitação.
    companion object {
        const val KEY_USER_ID = "user_id"
        const val KEY_USER_EMAIL = "user_email"
        const val KEY_USER_NAME = "user_name" // É uma boa prática salvar o nome também.
    }

    /**
     * Salva as informações essenciais do usuário no SharedPreferences após um login bem-sucedido.
     * @param user O objeto FirebaseUser retornado pela autenticação do Firebase.
     */
    fun saveUser(user: FirebaseUser) {
        prefs.edit {
            putString(KEY_USER_ID, user.uid)
            putString(KEY_USER_EMAIL, user.email)
            // O nome do usuário pode vir do perfil do Firebase ou do cadastro inicial.
            putString(KEY_USER_NAME, user.displayName)
            // Salva as alterações de forma assíncrona (não bloqueia a thread principal).
        }
    }

    /**
     * Recupera o ID do usuário salvo.
     * Usado na MainActivity para decidir se o usuário já está logado.
     * @return O UID do usuário como String, ou null se não houver usuário salvo.
     */
    fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, null)
    }

    /**
     * Limpa todas as preferências do usuário salvas no dispositivo.
     * Essencial para a funcionalidade de logout.
     */
    fun clearUser() {
        prefs.edit {
            clear()
        }
    }
}