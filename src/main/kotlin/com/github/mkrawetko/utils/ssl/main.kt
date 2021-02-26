package com.github.mkrawetko.utils.ssl

import com.github.mkrawetko.utils.ssl.Actions.DECRYPT
import com.github.mkrawetko.utils.ssl.Actions.ENCRYPT
import java.io.File

private enum class Actions {
    ENCRYPT, DECRYPT
}

internal fun main(args: Array<String>) {
    if (args.size < 2) {
        val actions = Actions.values().joinToString("|")
        println(
            """Usage:
            | ./gradlew -q --console=plain run --args="src/test/resources/jksKeystore.conf $actions"
            | keystore.conf file example:
            | keystorePath=src/test/resources/encdec.jks
            | storePassword=storepassword
            | keyPassword=keypassword
            | keystoreAlias=encdec
        """.trimMargin()
        )
        return
    }

    val config = KeystoreConfig(File(args[0]))
    val keyStoreHolder = KeystoreHolder(config.keystorePath, config.storePassword)
    val operation = Actions.valueOf(args[1].toUpperCase())

    print("Enter text to $operation: ")
    val readLine = readLine()!!
    val result = when (operation) {
        ENCRYPT -> keyStoreHolder.getEncryptor(config.keystoreAlias).encrypt(readLine)
        DECRYPT -> keyStoreHolder.getDecryptor(config.keystoreAlias, config.keyPassword).decrypt(readLine)
    }
    println("$operation result: $result")
}

