FROM openjdk:17
COPY ./build/libs/*.war app.war
ENTRYPOINT ["java","-jar","app.war"]
