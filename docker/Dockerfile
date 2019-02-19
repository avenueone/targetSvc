FROM openjdk:8-jre-alpine

ENV AVO_DIR /avo/app

COPY image/target-svc*.jar $AVO_DIR/target-svc.jar
RUN chmod +x ${AVO_DIR}/target-svc.jar

COPY entrypoint.sh ${AVO_DIR}/bin/
RUN chmod +x ${AVO_DIR}/bin/entrypoint.sh

EXPOSE 8090

# Command that is executed when you start the image
CMD ${AVO_DIR}/bin/entrypoint.sh
