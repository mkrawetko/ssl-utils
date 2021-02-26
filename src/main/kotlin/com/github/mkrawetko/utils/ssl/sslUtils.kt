package com.github.mkrawetko.utils.ssl

import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.security.Key
import java.security.KeyStore
import java.security.cert.Certificate
import java.util.*
import javax.crypto.Cipher

class KeystoreConfig(keyStoreConfig: File) {

    private val conf: Properties = Properties().apply { load(keyStoreConfig.inputStream()) }

    val keystorePath: String = conf.getProperty("keystorePath")
    val storePassword: String = conf.getProperty("storePassword")
    val keystoreAlias: String = conf.getProperty("keystoreAlias")
    val keyPassword: String = conf.getProperty("keyPassword")
}

class KeystoreHolder(keyStoreFile: File, private val keyStorePassword: String) {
    constructor(keyStorePath: String, keyStorePassword: String) : this(
        Paths.get(keyStorePath).toFile(),
        keyStorePassword
    )

    private val keyStore = KeyStore.getInstance(keyStoreFile.extension).apply {
        load(FileInputStream(keyStoreFile), keyStorePassword.toCharArray())
    }

    fun getType() = keyStore.type!!

    fun getEncryptor(alias: String) = Encryptor(keyStore.getCertificate(alias))
    fun getDecryptor(alias: String, password: String) = Decryptor(keyStore.getKey(alias, password.toCharArray()))
}


class Decryptor(private val privateKey: Key) {
    private val base64Decoder = Base64.getDecoder()

    fun decrypt(encryptedText: String): String {
        val cipher = Cipher.getInstance("RSA").apply {
            init(Cipher.DECRYPT_MODE, privateKey)
        }
        return String(cipher.doFinal(base64Decoder.decode(encryptedText)))
    }
}


class Encryptor(private val publicKey: Certificate) {

    private val base64Encoder = Base64.getEncoder()

    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance("RSA").apply {
            init(Cipher.ENCRYPT_MODE, publicKey)
        }
        return base64Encoder.encodeToString(cipher.doFinal(text.toByteArray()))
    }
}
