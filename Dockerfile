FROM arm64v8/openjdk

WORKDIR /app

COPY target/grouPay-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]

