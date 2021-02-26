#!/usr/bin/env bash

set -euo pipefail
read -r -s -p "Please enter a password for the key & keystore (default: password):" PASSWORD
PASSWORD=${PASSWORD:=password}
openssl req -x509 -newkey rsa:2048 -utf8 -days 3650 -nodes -config client-cert.conf -keyout client-cert.key -out client-cert.crt
openssl pkcs12 -export -inkey client-cert.key -in client-cert.crt -out client-cert.p12 -password "pass:$PASSWORD"
keytool -importkeystore -deststorepass "$PASSWORD" -destkeypass "$PASSWORD" -srckeystore client-cert.p12 -srcstorepass "$PASSWORD" -deststoretype pkcs12 -destkeystore client-cert.pkcs12
rm client-cert.key client-cert.p12
