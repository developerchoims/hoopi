# AWS Corretto 17 기반 이미지 사용
FROM amazoncorretto:17 AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar
FROM amazoncorretto:17
COPY --from=builder build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
VOLUME /tmp
# 프록시 환경 변수 설정
#ENV HTTP_PROXY=http://localhost:8080
#ENV HTTPS_PROXY=http://localhost:3000

# 작업 디렉토리 설정
#ARG JAR_FILE=*.jar

# JAR 파일을 컨테이너에 복사 (jar 파일이 하나만 생기도록 설정)
#COPY ${JAR_FILE} app.jar
# 포트 설정
#EXPOSE 8080
