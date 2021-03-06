# Курсовой проект "Сервис перевода денег"

## Описание проекта

Приложение REST-сервис реализует интерфейс перевода денег с карты на карту для ранее разработанного веб-приложения https://serp-ya.github.io/card-transfer/ 
по спецификации https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml

## Функциональность

### Transfer

Создание перевода с какрты на карту

### ConfirmOperation

Подтверждение созданного ранее перевода с помощью 4-х значного кода(генерация 4-х значного кода отсутствует)

### Логирование

Происходит логирование всех событий: регистрация нового перевода; регистрация подтверждения на перевод; обработка перевода; возникновение ошибок при обработке перевода, 
подтверждения на перевод, работы приложения.


## Описание работы приложения

Запущенное приложение(port `5500` указан в настройках `application.properties` и `Dockerfile`) ожидает входящих POST-запросов:

### Запрос на регистрацию денежного перевода с карты на карту

http://localhost:5500/transfer 

`Content-Type: application/json`

#### *пример:*

`{"cardFromNumber":"8888888888888888",
  "cardFromValidTill":"10/25",
  "cardFromCVV":"888",
  "cardToNumber":"9999999999999999",
  "amount":{
    "value":10000,
    "currency":"RUR"
  }
}`

#### *Результаты обработки:*

+ *создание перевода, успешный ответ с кодом "200" и id созданной операции*

+ *неуспешный ответ с кодом "400"(ошибка в данных) и текстом ошибки*

+ *неуспешный ответ с кодом "500"(ошибка перевода) и текстом ошибки*

### Подтверждение операции на перевод

http://localhost:5500/confirmOperation

`Content-Type: application/json`

#### *пример:*

`{"operationId":"2","code":"0000"}`

#### *Результаты обработки:*

+ *подтверждение операции, выполнение операции, успешный ответ с кодом "200" и id операции*

+ *неуспешный ответ с кодом "400"(ошибка в данных) и текстом ошибки*

+ *неуспешный ответ с кодом "500"(ошибка перевода) и текстом ошибки*

### Примеры ответов:

`HTTP/1.1 200`
`Content-Type: text/plain;charset=UTF-8`
`{"operationId":"3"}`

`HTTP/1.1 400` 
`Content-Type: text/plain;charset=UTF-8`
`{"message":"Не найдена карта отправителя!","id":"0"}`

`HTTP/1.1 500` 
`Content-Type: text/plain;charset=UTF-8`
`{"message":"Недостаточно средств для списания!","id":"0"}`


## Структура проекта

+ package config        - конфигурация
+ package repository    - репозиторий
+ package service       - сервис
+ package controller    - контроллер
+ package model         - модель данных
+ package exceptions    - выбрасываемые исключения
+ package logger        - логирование

### Запуск

Для запуска приложения в терминале выполнить команду `docker-compose up`
