package codeasus.projects.bank.eco.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.nio.ByteBuffer
import java.security.InvalidKeyException
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object AESKeyProtector {
    private const val KEYSTORE_ALIAS = "ECO_AES_KEY"
    private const val PROVIDER = "AndroidKeyStore"
    private const val KEY_SIZE = 256
    private const val ENCRYPTION_MODE = "AES/GCM/NoPadding"
    private const val GCM_IV_LENGTH = 12
    private const val GCM_TAG_LENGTH = 128

    private fun generateSecretKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, PROVIDER)
        val keyGenParameterSpec = KeyGenParameterSpec
            .Builder(KEYSTORE_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .build()
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey? {
        val keyStore = KeyStore.getInstance(PROVIDER).apply { load(null) }
        if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
            generateSecretKey()
        }
        val secretKeyEntry = keyStore.getEntry(KEYSTORE_ALIAS, null) as? KeyStore.SecretKeyEntry
        return secretKeyEntry?.secretKey
    }

    fun encrypt(data: ByteArray): ByteArray {
        try {
            val cipher = Cipher.getInstance(ENCRYPTION_MODE)
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())

            val iv = cipher.iv
            val encryptedBytes = cipher.doFinal(data)

            val byteBuffer = ByteBuffer.allocate(iv.size + encryptedBytes.size)
            byteBuffer.put(iv)
            byteBuffer.put(encryptedBytes)
            return byteBuffer.array()

        } catch (e: Exception) {
            if (e is InvalidKeyException) {
                deleteInvalidKey()
                val cipher = Cipher.getInstance(ENCRYPTION_MODE)
                cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
                val iv = cipher.iv
                val encryptedBytes = cipher.doFinal(data)

                val byteBuffer = ByteBuffer.allocate(iv.size + encryptedBytes.size)
                byteBuffer.put(iv)
                byteBuffer.put(encryptedBytes)
                return byteBuffer.array()
            }
            throw e
        }
    }

    fun decrypt(cipherDataWithIV: ByteArray): ByteArray {
        try {
            if (cipherDataWithIV.size < GCM_IV_LENGTH) throw IllegalArgumentException("Invalid data format")

            val iv = cipherDataWithIV.copyOfRange(0, GCM_IV_LENGTH)
            val encryptedData = cipherDataWithIV.copyOfRange(GCM_IV_LENGTH, cipherDataWithIV.size)

            val cipher = Cipher.getInstance(ENCRYPTION_MODE)
            val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)

            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
            return cipher.doFinal(encryptedData)

        } catch (e: Exception) {
            if (e is InvalidKeyException) {
                deleteInvalidKey()
            }
            throw e
        }
    }

    private fun deleteInvalidKey() {
        try {
            val keyStore = KeyStore.getInstance(PROVIDER).apply { load(null) }
            keyStore.deleteEntry(KEYSTORE_ALIAS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}