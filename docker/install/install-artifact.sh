#!/bin/bash

set -eo pipefail

SUB_DIR="$1"

# Pre install scripts
for SCRIPT_FILE in $(test -d /install/pre-install-scripts/$SUB_DIR && find /install/pre-install-scripts/$SUB_DIR -type f | sort)
do
    echo "Execute : $SCRIPT_FILE"
    . $SCRIPT_FILE
done

# Install artifact
cd $CATALINA_BASE/webapps/bgl-gateway-api && cp /install/artifacts/api-gateway.war ./  && unzip api-gateway.war && rm api-gateway.war

# Post install scripts
cp /install/config-files/webapp/* /MIDDLE/CBW/bgl-gateway-api/webapps/bgl-gateway-api/WEB-INF/classes

for SCRIPT_FILE in $(test -d /install/post-install-scripts/$SUB_DIR && find /install/post-install-scripts/$SUB_DIR -type f | sort)
do
    echo "Execute : $SCRIPT_FILE"
    . $SCRIPT_FILE
done
