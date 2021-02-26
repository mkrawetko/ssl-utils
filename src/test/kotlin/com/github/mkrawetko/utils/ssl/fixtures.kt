package com.github.mkrawetko.utils.ssl

import java.nio.file.Paths

internal val BASE64_REGEX = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$".toRegex()
private const val TEST_RESOURCES_PATH = "src/test/resources"
internal val JKS_KEYSTORE_CONF_PATH = Paths.get(TEST_RESOURCES_PATH, "jksKeystore.conf")
internal val PKCS12_KEYSTORE_CONF_PATH = Paths.get(TEST_RESOURCES_PATH, "pkcs12Keystore.conf")