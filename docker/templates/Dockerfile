FROM openjdk:11.0.5 AS BUILD_IMAGE

ARG SBT_VERSION=1.5.2
ENV APP_HOME=/app/

WORKDIR $APP_HOME
ADD ../.. $APP_HOME

RUN \
  mkdir /working/ && \
  cd /working/ && \
  curl -L -o sbt-$SBT_VERSION.deb https://repo.scala-sbt.org/scalasbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  cd && \
  rm -r /working/ && \
  sbt sbtVersion

RUN  sbt clean stage


FROM adoptopenjdk/openjdk11:jre-11.0.14_9-alpine

ENV APP_HOME=/app/
ENV PLAY_SECRET_KEY=""

WORKDIR $APP_HOME
COPY --from=BUILD_IMAGE $APP_HOME/target/universal/stage/ .

RUN apk add bash

EXPOSE 9000
ENTRYPOINT bash bin/playkube -Dplay.http.secret.key=${PLAY_SECRET_KEY}
