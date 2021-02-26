## Utility to decrypt/encrypt text with provided key store ( .jks, pkcs12 )

### Usage:

###### Example of keystore config file: [src/test/resources/jksKeystore.conf](src/test/resources/jksKeystore.conf)


##### Gradle application plugin

```bash
./gradlew -q --console=plain run --args="src/test/resources/jksKeystore.conf ENCRYPT"
./gradlew -q --console=plain run --args="src/test/resources/jksKeystore.conf DECRYPT"
```

##### bash keytool and openssl

```bash
echo "textToEncrypt" | ./scripts/encrypt.sh src/test/resources/jksKeystore.conf
echo "textToDecryptInBase64Format" | scripts/decrypt.sh src/test/resources/jksKeystore.conf
```
