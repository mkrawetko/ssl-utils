#!/usr/bin/env bash
set -euo pipefail

USAGE="Usage: ./convertJksToPkcs12.sh /path/to/keystore.conf"
: "${1?"${USAGE}"}"

keystoreConfigPath="$1"
keystorePath=$(awk -F "=" '/keystorePath/ {print $2}' "$keystoreConfigPath")
if [ "$keystorePath" == "${keystorePath#/}" ]; then
  keystorePath=$(pwd)/$keystorePath
fi
storePassword=$(awk -F "=" '/storePassword/ {print $2}' "$keystoreConfigPath")
keyPassword=$(awk -F "=" '/keyPassword/ {print $2}' "$keystoreConfigPath")
keystoreAlias=$(awk -F "=" '/keystoreAlias/ {print $2}' "$keystoreConfigPath")

rm -rf tmp
mkdir "tmp"
cd tmp

# Convert jks to PKCS12 format
# Different store and key passwords not supported for PKCS12 KeyStores. Ignoring user-specified -destkeypass value.
keytool -importkeystore -srckeystore "$keystorePath" -srcstorepass "$storePassword" -srckeypass "$keyPassword" -srcalias "$keystoreAlias" \
  -destkeystore identity.p12 -deststoretype PKCS12 -deststorepass "$keyPassword" -destkeypass "$keyPassword" -destalias "$keystoreAlias"