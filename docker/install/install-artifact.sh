#!/bin/bash

set -eo pipefail

SUB_DIR="$1"

# Pre install scripts
for SCRIPT_FILE in $(test -d /install/pre-install-scripts/$SUB_DIR && find /install/pre-install-scripts/$SUB_DIR -type f | sort)
do
    echo "Execute : $SCRIPT_FILE"
    . $SCRIPT_FILE
done
ls $CATALINA_BASE
ls $CATALINA_BASE/webapps
# Install artifact
cd $CATALINA_BASE/webapps/job-offer-producer && cp /install/artifacts/job-offer-producer.war ./  && unzip job-offer-producer.war && rm job-offer-producer.war

# Post install scripts
ls /install
echo "ls install"
ls /install/config-files
cp /install/config-files/webapp/* /MIDDLE/CBW/job-offer-producer/webapps/job-offer-producer/WEB-INF/classes

for SCRIPT_FILE in $(test -d /install/post-install-scripts/$SUB_DIR && find /install/post-install-scripts/$SUB_DIR -type f | sort)
do
    echo "Execute : $SCRIPT_FILE"
    . $SCRIPT_FILE
done
