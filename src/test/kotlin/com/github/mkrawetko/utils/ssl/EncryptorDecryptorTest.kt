package com.github.mkrawetko.utils.ssl

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.lang.Runtime.getRuntime
import java.util.concurrent.Callable
import java.util.concurrent.Executors


private val PLAIN_TEXT = "plainText${System.currentTimeMillis()}"


@TestInstance(PER_CLASS)
class EncryptorDecryptorTest {

    @ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] {0}")
    @MethodSource("keystoreTypeEncryptorAndDecryptor")
    internal fun `should encrypt and decrypt plain text`(type: String, encryptor: Encryptor, decryptor: Decryptor) {
        val actualEncrypted = encryptor.encrypt(PLAIN_TEXT)

        actualEncrypted shouldMatch BASE64_REGEX

        val actualDecrypted = decryptor.decrypt(actualEncrypted)

        actualDecrypted shouldBe PLAIN_TEXT
    }


    @ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] {0}")
    @MethodSource("keystoreTypeEncryptorAndDecryptor")
    internal fun `should be thread safe`(type: String, encryptor: Encryptor, decryptor: Decryptor) {
        val initialTextsToEncrypt = (0..1000).map { "text$it" }
        val threadPool = threadPoolOfNumberOfProcessorsOrCoerceAtLeast3()

        val actualEncrypted = threadPool
            .invokeAll(initialTextsToEncrypt.map { Callable { encryptor.encrypt(it) } })
            .map { it.get() }

        actualEncrypted.forEach { actual -> actual shouldMatch BASE64_REGEX }

        val actualDecrypted = threadPool
            .invokeAll(actualEncrypted.map { Callable { decryptor.decrypt(it) } })
            .map { it.get() }

        actualDecrypted shouldContainExactlyInAnyOrder initialTextsToEncrypt
    }

    private fun threadPoolOfNumberOfProcessorsOrCoerceAtLeast3() =
        getRuntime().availableProcessors().coerceAtLeast(3).let { numberOfThreads ->
            println("executing with $numberOfThreads threads!")
            Executors.newFixedThreadPool(numberOfThreads)
        }


    private fun keystoreTypeEncryptorAndDecryptor() = listOf(JKS_KEYSTORE_CONF_PATH, PKCS12_KEYSTORE_CONF_PATH)
        .map { configPath -> KeystoreConfig(configPath.toFile()) }
        .map { config ->
            KeystoreHolder(File(config.keystorePath), config.storePassword).let {
                Arguments.arguments(
                    it.getType(),
                    it.getEncryptor(config.keystoreAlias),
                    it.getDecryptor(config.keystoreAlias, config.keyPassword)
                )
            }
        }
}
