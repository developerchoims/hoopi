# AWS Corretto 17 기반 이미지 사용
FROM amazoncorretto:17

# 작업 디렉토리 설정
ARG JAR_FILE=*.jar

# JAR 파일을 컨테이너에 복사 (jar 파일이 하나만 생기도록 설정)
COPY ${JAR_FILE} app.jar

# 포트 설정
EXPOSE 8080

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]