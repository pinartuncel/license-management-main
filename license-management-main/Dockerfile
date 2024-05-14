# pull official base image
FROM node:16.15-alpine as build-frontend

WORKDIR /app

COPY ./frontend/licence-management-ui/package.json .
COPY ./frontend/licence-management-ui/package-lock.json .
RUN npm install

COPY ./frontend/licence-management-ui/ .

ARG REACT_APP_API_ENDPOINT_URL
ENV REACT_APP_API_ENDPOINT_URL $REACT_APP_API_ENDPOINT_URL

RUN npm run build

FROM amazoncorretto:11 as build-backend

WORKDIR /app

COPY . .
COPY --from=build-frontend /app/build/ ./src/main/resources/META-INF/resources/

RUN ./gradlew build -x test -Dquarkus.package.type=uber-jar

FROM amazoncorretto:11 as build-app

WORKDIR /app

COPY --from=build-backend /app/build/*SNAPSHOT-runner.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
