#!/usr/bin/env bash

#Keytool Arguments
#Argument	Description
#-alias	The name in the Java KeyStore the generated key should be identified by. Remember, an alias can only point to one key.
# -keyalg	The name of the algorithm used to generate the key. A common value is RSA meaning the RSA algorithm should be used to generate the key pair.
# -keysize	The size in bits of the key to generate. Normally key sizes are multiples of 8 which aligns with a number of bytes. Additionally, different algorithms may only support certain preset key sizes. You will need to check what the key size should be for the key you want to generate.
# -sigalg	The signature algorithm used to sign the key pair.
# -dname	The Distinguished Name from the X.500 standard. This name will be associated with the alias for this key pair in the KeyStore. The dname is also used as the "issuer" and "subject" fields in the self signed certificate.
# -keypass	The key pair password needed to access this specific key pair within the KeyStore.
# -validity	The number of days the certificate attached to the key pair should be valid.
# -storetype	The file format the KeyStore should be saved in. The default is JKS. Another option is the value PKCS11 which represents the standard PKCS11 format.
# -keystore	The name of the KeyStore file to store the generated key pair in. If the file does not exist, it will be created.
# -file	The name of the file to read from or write to (certificate or certificate request).
# -storepass	The password for the whole KeyStore. Anyone who wants to open this KeyStore later will need this password. The storepass is not the same as the keypass. The keypass password only counts for a single key. You will need both the KeyStore password and the key password to access any given key stored in a KeyStore.
# -rfc	If this flag is included (it has no value following it) then Keytool will use a textual format rather than binary format e.g. for export or import of certificates. The value -rfc refers to the RFC 1421 standard.
# -providerName	The name of the cryptographic API provider you want to use (if any) when generating the key pair. The provider name must be listed in the Java security property files for this to work.
# -providerClass	The name of the root class of the cryptographic API provider you want to use. Use this when the provider name is not listed in the Java security property files.
# -providerArg	Arguments you can pass to your cryptographic provider at initialization (if needed by the provider).
# -v	Short for "verbose" (?!?), meaning the Keytool will print out a lot of extra information into the command line in a humanly readable format.
# -protected	Specifies whether or not the KeyStore password should be provided by some external mechanism like a pin reader. Valid values are true and false.
# -Jjavaoption	A Java option string (Java VM options) which can be passed to the Java VM that generates the key pair and creates the KeyStore.

keytool -genkeypair -alias encdec -keyalg RSA -dname "CN=mkrawetko, OU=mkrawetko github, O=mkrawetko apps, L=Wroclaw, C=Poland" -keystore encdec.jks -keypass keypassword -storepass storepassword
