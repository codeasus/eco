package codeasus.projects.bank.eco.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object AESKeyProtector {
    private const val KEYSTORE_ALIAS = "AESKey"
    private const val PROVIDER = "AndroidKeyStore"
    private const val KEY_SIZE = 256
    private const val ENCRYPTION_MODE_AES_GCM_NO_PADDING = "AES/GCM/NoPadding"

    private fun generateSecretKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, PROVIDER)
        val keyGenParameterSpec = KeyGenParameterSpec
            .Builder(KEYSTORE_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(false)
            .setKeySize(KEY_SIZE)
            .build()
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey? {
        val keyStore = KeyStore.getInstance(PROVIDER).apply {
            load(null)
        }
        if (keyStore.containsAlias(KEYSTORE_ALIAS)) {
            val secretKeyEntry = keyStore.getEntry(KEYSTORE_ALIAS, null) as KeyStore.SecretKeyEntry
            return secretKeyEntry.secretKey
        }
        throw RuntimeException("SecretKey has not been generated")
    }

    fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ENCRYPTION_MODE_AES_GCM_NO_PADDING)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        return cipher.doFinal(data)
    }

    fun decrypt(cipherData: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ENCRYPTION_MODE_AES_GCM_NO_PADDING)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey())
        return cipher.doFinal(cipherData)
    }
}