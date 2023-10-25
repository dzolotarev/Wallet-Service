# Wallet-Service

Cервис управляет кредитными/дебетовыми транзакциями от имени игроков (пользователей).

## Описание
Денежный счет содержит текущий баланс игрока. Баланс можно изменить, зарегистрировав транзакции на счете,
либо дебетовые транзакции (удаление средств), либо кредитные транзакции (добавление средств).


## Версия 0.0.3
### Требования:
1. [x] Все взаимодействие осуществляeтся через отправку HTTP запросов
2. [x] Сервлеты принимають JSON и отдают JSON
3. [x] Возвращаются разные статус-коды
4. [x] Метод логина выдавает JWT, остальные методы авторизационны и валидируют JWT (настройки в _resources/jwt.properties_)

### API
**Создать пользователя**
* URL: /register
* Method: POST
* Success Response: 200
* Success Response (body): true
* Body request:
{
"login": "login",
"password": "password",
"name": "John Doe"
}
* Notes: "login", "password" can't be null

**Авторизовать пользователя**
* URL: /auth
* Method: GET
* Success Response: 200
* Success Response (body):"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjk4MjM1MTgyLCJleHAiOjE2OTgyMzU5ODJ9.foSVKBBS0U9s1PUaYOLfTMR3aYByk5KXbQsryB6FfNZvLr0-UJvr5GDB-IldHs-tI1KsxRVOYbAb3y45659YBw"
* Body request:
  {
  "login": "login",
  "password": "password",
  }
* Notes: "login", "password" can't be null

**Посмотреть балланс пользователя**
* URL: /balance
* Method: GET
* Success Response: 200
* Success Response (body): Long
* Body request:
`  {
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjk4MjM1MTgyLCJleHAiOjE2OTgyMzU5ODJ9.foSVKBBS0U9s1PUaYOLfTMR3aYByk5KXbQsryB6FfNZvLr0-UJvr5GDB-IldHs-tI1KsxRVOYbAb3y45659YBw"
  }`
* Notes: "token" can't be null

**Посмотреть историю операций пользователя**
* URL: /history
* Method: GET
* Success Response: 200
* Success Response (body):
`[{
  "userId": 1,
  "id": "e34d9b3c-718b-11ee-bb4f-0242ac120002",
  "type": "CREDIT",
  "value": 1000,
  "date": 1698055612294
  },
  {
  "userId": 1,
  "id": "e77dfc1a-718b-11ee-bb4f-0242ac120002",
  "type": "DEBIT",
  "value": 100,
  "date": 1698055619324
  }]`
* Body request:
  `{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjk4MjM1MTgyLCJleHAiOjE2OTgyMzU5ODJ9.foSVKBBS0U9s1PUaYOLfTMR3aYByk5KXbQsryB6FfNZvLr0-UJvr5GDB-IldHs-tI1KsxRVOYbAb3y45659YBw"
  }`
* Notes: "token" can't be null

**Дебит операция со счетом пользователя**
* URL: /debit
* Method: POST
* Success Response: 200
* Success Response (body): true
* Body request:
 ` {
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjk4MjM1MTgyLCJleHAiOjE2OTgyMzU5ODJ9.foSVKBBS0U9s1PUaYOLfTMR3aYByk5KXbQsryB6FfNZvLr0-UJvr5GDB-IldHs-tI1KsxRVOYbAb3y45659YB",
  "value": "100000"
  }`
* Notes: "token" can't be null, "value" - long

**Кредит операция со счетом пользователя**
* URL: /credit
* Method: POST
* Success Response: 200
* Success Response (body): true
* Body request:
 ` {
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjk4MjM1MTgyLCJleHAiOjE2OTgyMzU5ODJ9.foSVKBBS0U9s1PUaYOLfTMR3aYByk5KXbQsryB6FfNZvLr0-UJvr5GDB-IldHs-tI1KsxRVOYbAb3y45659YB",
  "value": "100000"
  }`
* Notes: "token" can't be null, "value" - long

**Аудит всех операций всех пользователей**
* URL: /audit
* Method: GET
* Success Response: 200
* Success Response (body):
  `[{
  "operation": "AUTH",
  "userId": 1,
  "auditOption": "SUCCESSFUL",
  "createdAt": 1698055580728
  },
  {
  "operation": "BALANCE",
  "userId": 1,
  "auditOption": "SUCCESSFUL",
  "createdAt": 1698055585730
  }]`
* Body request:
  `{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjk4MjM1MTgyLCJleHAiOjE2OTgyMzU5ODJ9.foSVKBBS0U9s1PUaYOLfTMR3aYByk5KXbQsryB6FfNZvLr0-UJvr5GDB-IldHs-tI1KsxRVOYbAb3y45659YB",
  }`
* Notes: "token" can't be null

### ToDO
1. [ ] Аудит переделать на аспекты
2. [ ] Также реализовать на аспектах выполнение любого метода (с замером времени выполнения)
3. [ ] Сервлеты должны быть покрыты тестами

## Версия 0.0.2
### Требования
1. [x] Репозитории теперь пишут ВСЕ сущности в БД PostgreSQL
2. [x] Идентификаторы при сохранении в БД выдаваться через sequence.
3. [x] DDL-скрипты на создание таблиц и скрипты на предзаполнение таблиц выполняются инструментом миграции Liquibase
4. [x] Скрипты миграции Luiqbase написаны в нотации XML
5. [x] Скриптов миграции два. Один на создание всех таблиц, другой - не предзаполнение данными
6. [x] Служебные таблицы в отдельной схеме
7. [x] Таблицы сущностей храняться также в отдельной схеме
8. [x] В репо есть docker-compose.yml, в котором прописаны инструкции для развертывания postgre в докере
9. [x] Приложение поддерживать конфиг-файлы. Всё, что относится к подключению БД, а также к миграциям, сконфигурировано через конфиг-файлы

### Запуск
- Запустить _docker-compose up_ из _resources/docker-compose.yml_
- Запустить перед первым запуском миграцию через **_DbMigration.java_**   
- Запуск приложения осуществляется из класса _**App.class**_
- Следовать инструкциям на экране =)
- При миграции создается два тестовых пользователя (_login:111/pass:1111_ и _login:222/pass:2222_) - можно использовать для теста =)
- Настройки БД в _resources/db.properties_

### ToDo
1. [ ] Все еще нет тестов =(
2. [ ] JavaDoc оформлен частично
3. [ ] Сломал реализацию хранения в коллекциях: надо починить и использовать коллекции как локальный кэш



## Версия 0.0.1
### Требования
1. [x] Данные хранятся в памяти приложения
2. [x] Приложение должно быть консольным (никаих спрингов, взаимодействий с БД и тд, только java-core и collections)
3. [x] Регистрация игрока
4. [x] Авторизация игрока
5. [x] Текущий баланс игрока
6. [x] Дебет/снятие средств для каждого игрока. Дебетовая транзакция будет успешной только в том случае, если на счету достаточно средств (баланс - сумма дебета >= 0).
7. [x] Вызывающая сторона предоставит идентификатор транзакции, который должен быть уникальным для всех транзакций. Если идентификатор транзакции не уникален, операция должна завершиться ошибкой.
8. [x] Кредит на игрока. Вызывающая сторона предоставит идентификатор транзакции, который должен быть уникальным для всех транзакций. Если идентификатор транзакции не уникален, операция должна завершиться ошибкой.
9. [x] Просмотр истории пополнения/снятия средств игроком
10. [x] Аудит действий игрока (авторизация, завершение работы, пополнения, снятия)

### Запуск
- Запуск приложения осуществляется из класса _**App.class**_
- Далее, следовать инструкциям на экране =)

### ToDo
1. [ ] Unit-тесты
2. [ ] JavaDoc