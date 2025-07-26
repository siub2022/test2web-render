FROM openjdk:17-alpine
WORKDIR /app
COPY . .
RUN javac Test2web.java
CMD ["java", "Test2web"]