#!/bin/sh

# Pack pixnews.properties and sets github actions secret
zstd -19 -T0 -c config/pixnews.properties | base64 -w 0 | gh secret set PIXNEWS_CONFIG

# Pack config and signing keys and sets github actions secret
tar -c -I 'zstd -19 -T0' -f - config/signing/release.jks config/signing/release.properties config/pixnews.properties \
  | base64 -w 0 | gh secret set SIGN_KEYS_AND_SECRETS
