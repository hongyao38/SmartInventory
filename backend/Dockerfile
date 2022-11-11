FROM openjdk:17

WORKDIR /opt/app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw install

CMD ["./mvnw", "spring-boot:run","main.env"]
