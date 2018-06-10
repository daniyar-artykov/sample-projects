Технологии:
- Java 8
- Spring Boot 2.0.2
- Gradle 4.7
- InfluxDB 1.5
- Docker 18.05.0-ce

Порядок запуска приложение:
1. в папке sample-rest-api запустить команду gradle build docker
2. запустить команду docker run -p 8080:8080 -t kcell/sample-rest-api
3. вызвать команду curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"value":"100.0"}' http://localhost:8080/save
4. открыть в браузере http://localhost:8080/get, примерный ответ должен быть {"time":"1528661091658","value":"100.0"}

* InfluxDB должен быть запущен и доступен по localhost:8086, можно поменять параметры подключения к БД в классе kz.kcell.rest.controller.SampleController в методе connectToInfluxDB
