package com.kheang.firebaseauthenticationloginregister.data.local

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoManager @Inject constructor() {

    companion object{
        private const val KEYSTORE = "AndroidKeyStore"
        private const val KEY_ALIAS = "my_app_token_key"
        private const val TRANSFORMATION =
            "AES/GCM/NoPadding"

        private const val IV_SIZE = 12
        private const val TAG_LENGTH = 128
    }

    fun getOrCreateKey(): SecretKey{

        val keyStore = KeyStore.getInstance(KEYSTORE).apply { load(null) }

        val existingKey = keyStore.getKey(KEY_ALIAS, null) as? SecretKey
        if (existingKey != null) {
            return existingKey
        }

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            KEYSTORE
        )

        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setKeySize(256)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }


    fun encrypt(value : String): String{

        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        }

        val iv = cipher.iv
        val encrypted = cipher.doFinal(value.toByteArray(StandardCharsets.UTF_8))
        val combined = iv + encrypted

        return Base64.encodeToString(
            combined,
            Base64.NO_WRAP
        )
    }
    fun decrypt(value: String): String {

        val combined = Base64.decode(
            value,
            Base64.NO_WRAP
        )

        val iv = combined.copyOfRange(
            0,
            IV_SIZE
        )

        val encrypted = combined.copyOfRange(
            IV_SIZE,
            combined.size
        )

        val cipher = Cipher.getInstance(
            TRANSFORMATION
        )

        val spec = GCMParameterSpec(
            TAG_LENGTH,
            iv
        )

        cipher.init(
            Cipher.DECRYPT_MODE,
            getOrCreateKey(),
            spec
        )

        val decrypted = cipher.doFinal(
            encrypted
        )

        return String(
            decrypted,
            StandardCharsets.UTF_8
        )
    }



}