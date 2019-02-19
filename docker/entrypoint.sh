#!/bin/sh

echo "The application will start in ${JHIPSTER_SLEEP}s..." && sleep ${JHIPSTER_SLEEP}

# Need to add spring profile later
java ${JAVA_OPTS} -jar ${AVO_DIR}/target-svc.jar
