#!/bin/sh

# Pack google-services.json and sets github actions secret
zstd -19 -T0 -c app/src/google-services.json | base64 -w 0 | gh secret set GOOGLE_SERVICES_JSON
