# GIF по курсам валют (Giphy rates)
Spring Boot, Feign, JUnit, Docker

## Описание
Создать сервис, который обращается к сервису курсов валют, и отображает gif:
- если курс по отношению к USD за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https://giphy.com/search/rich
- если ниже - отсюда https://giphy.com/search/broke
Ссылки
- REST API курсов валют - https://docs.openexchangerates.org/
- REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide
Must Have
- Сервис на Spring Boot 2 + Java / Kotlin
- Запросы приходят на HTTP endpoint (должен быть написан в соответствии с rest conventions), туда передается код валюты по отношению с которой сравнивается USD
- Для взаимодействия с внешними сервисами используется Feign
- Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки
- На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
- Для сборки должен использоваться Gradle
- Результатом выполнения должен быть репо на GitHub с инструкцией по запуску
Nice to Have
- Сборка и запуск Docker контейнера с этим сервисом

## Решение

### Запуск
*На Windows для шагов 1 и 2.3 и можно использовать [Git Bash](https://git-scm.com/download), для шага 2.1 - [PowerShell](https://docs.microsoft.com/ru-ru/virtualization/windowscontainers/manage-docker/manage-windows-dockerfile#docker-build)*  
*Все действия выполняются в директории проекта*

#### 1. Сборка
&emsp; `./gradlew build -x test`  

#### 2. Запуск (3 варианта)
&emsp; **2.1 Через Docker**  
&emsp; Собрать образ:  
&emsp; `docker build -t giphyrates .`  
&emsp; Запустить контейнер:  
&emsp; `docker run -d -p 8081:8081 giphyrates`

&emsp; **2.2 JAR напрямую**  
&emsp; ` java -jar app.jar`  

&emsp; **2.3 Через Spring Boot (шаг [1. Сборка](#1.-Сборка) не обязателен)**  
&emsp; `./gradlew bootrun`  

Endpoints:
---  

GET /api/rates/{Char Code}
```  
Получить GIF для курса RUB:  
http://localhost:8081/api/rates/RUB 
