FROM openjdk:8-jre-alpine

RUN apk add bash

VOLUME /tmp
COPY wait-for-it.sh wait-for-it.sh
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 9191
ENTRYPOINT ["./wait-for-it.sh", "configserver:9091", "--", "java","-cp","app:app/lib/*","com.huawei.orderservice.OrderServiceApplication"]
