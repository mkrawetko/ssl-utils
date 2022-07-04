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


##### Import certificates
```bash
Java 8: /Library/Java/JavaVirtualMachines/jdk1.8.0181.jdk/Contents/Home/jre/lib/security/cacerts -storepass changeit -noprompt 
Java 11: /Users/mkrawetko/Library/Java/JavaVirtualMachines/adopt-openjdk-11.0.9.1/Contents/Home 
Java 17: /Users/mkrawetko/Library/Java/JavaVirtualMachines/temurin-17.0.1/Contents/Home 

JavaHomeDir=/Users/mkrawetko/Library/Java/JavaVirtualMachines/adopt-openjdk-11.0.9.1/Contents/Home ; \ 
JavaHomeDir=/Users/mkrawetko/Library/Application\ Support/JetBrains/Toolbox/apps/IDEA-U/ch-0/212.5284.40/IntelliJ\ IDEA.app/Contents/jbr/Contents/Home ; \ 
JavaHomeDir=/Users/mkrawetko/Library/Java/JavaVirtualMachines/temurin-17.0.1/Contents/Home; \ 

sudo $JavaHomeDir/bin/keytool -importcert -alias certAlias -file ~/Downloads/certs/CERT-TO-IMPORT.cer -keystore $JavaHomeDir/lib/security/cacerts -storepass changeit -noprompt 
```