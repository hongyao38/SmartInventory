FROM openjdk:17

WORKDIR /opt/app

COPY . .

RUN ./mvnw install

CMD ["./mvnw", "spring-boot:run"]