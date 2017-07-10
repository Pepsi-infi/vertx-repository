FROM java:8-jre

ENV VERTICLE_FILE target/xservice-channel-fat.jar

# Set the location of the verticles
ENV VERTICLE_HOME /opt/verticles

EXPOSE 9090

COPY $VERTICLE_FILE $VERTICLE_HOME/
COPY config.json $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar xservice-channel-fat.jar -cluster -conf config.json"]