#!/usr/bin/env bash

USAGE="Usage: ./decrypt.sh /path/to/keystore.conf"
: "${1?"${USAGE}"}"
source ./scripts/convertJksToPkcs12.sh

# openssl pkcs12 –help
#extract public key:
openssl pkcs12 -in identity.p12 -nokeys -password "pass:${keyPassword:?}" -out cert.pem

read -r -s textToEncrypt
# encrypt
echo "$textToEncrypt" | openssl rsautl -encrypt -inkey cert.pem -certin | base64
