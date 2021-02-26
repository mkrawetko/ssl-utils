#!/usr/bin/env bash

USAGE="Usage: ./decrypt.sh /path/to/keystore.conf"
: "${1?"${USAGE}"}"
source ./scripts/convertJksToPkcs12.sh

# openssl pkcs12 –help
# Exporting private key from PKCS12
openssl pkcs12 -in identity.p12 -nodes -nocerts -password "pass:${keyPassword:?}" -out private_key.pem

read -r -s encryptedText
# base64 decode encrypted text and decrypt
echo "$encryptedText" | base64 -d | openssl rsautl -decrypt -inkey private_key.pem
