# Wallet-Service

Cервис управляет кредитными/дебетовыми транзакциями от имени игроков.

## Описание
Денежный счет содержит текущий баланс игрока. Баланс можно изменить, зарегистрировав транзакции на счете,
либо дебетовые транзакции (удаление средств), либо кредитные транзакции (добавление средств).

## Требования
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

## Запуск
- Запуск приложения осуществляется из класса _**App.class**_
- Далее, следовать инструкциям на экране =)

## ToDo
1. [ ] Unit-тесты
2. [ ] JavaDoc