package com.github.mkrawetko.utils.ssl

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.text.Charsets.UTF_8


private val PLAIN_TEXT = "plainText${System.currentTimeMillis()}"


@TestInstance(PER_CLASS)
class EncryptDecryptBashTest {

    @Test
    internal fun `should encrypt and decrypt plain text`() {
        val actualEncrypted =
            bash("echo \"$PLAIN_TEXT\" | ./scripts/encrypt.sh ${JKS_KEYSTORE_CONF_PATH.toAbsolutePath()}")
                .trimEnd(Char::isWhitespace)

        actualEncrypted shouldMatch BASE64_REGEX

        val actualDecrypted =
            bash("echo \"$actualEncrypted\" | scripts/decrypt.sh ${JKS_KEYSTORE_CONF_PATH.toAbsolutePath()}")
                .trimEnd(Char::isWhitespace)

        actualDecrypted shouldBe PLAIN_TEXT
    }
}

private fun bash(target: String): String =
    ProcessBuilder("bash", "-c", target).start()
        .apply { errorStream.reader(UTF_8).use { println("errorStreamOutput: ${it.readText()}") } }
        .inputStream.reader(UTF_8).use {
            it.readText()
        }