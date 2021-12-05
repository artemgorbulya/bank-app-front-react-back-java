##API

Spring Sequrity

user admin
password admin

###Customers endpoints

- get http://localhost:9000/customers/{id}
- get - http://localhost:9000/customers?page=1
- get - http://localhost:9000/customers?page=2&size=5
- delete - http://localhost:9000/customers/{id}
- post - http://localhost:9000/customers
- put - http://localhost:9000/customers

###Accounts endpoints

- get - http://localhost:9000/accounts
- get - http://localhost:9000/accounts/{id}
- get - http://localhost:9000/accounts/customer/{customerid}
- delete - http://localhost:9000/accounts/{id}
- post - http://localhost:9000/accounts/{customerid}
- post - http://localhost:9000/accounts/addmoney?accountnumber={accountnumber}&value={value}
- post - http://localhost:9000/accounts/withdraw?accountnumber={accountnumber}&value={value}
- post -http://localhost:9000/accounts/transfer?fromaccountnumber={fromaccountnumber}&toaccountnumber={toaccountnumber}&value={value}

###Employer endpoints

- get - http://localhost:9000/employers
- get - http://localhost:9000/employers/{id}
- delete - http://localhost:9000/employers/{id}
- post - http://localhost:9000/employers


## Testing, Spring security

## Задание

- Протестировать работу приложения
- Подключить Spring Security

#### Технические требования:

- Добавить юнит-тесты для всех методов контроллеров и сервисов в приложении
- Подключить к проекту Spring Security
- Закрыть доступ ко всем ендпоинтам неавторизованным пользователям. Позволять получать информацию только после авторизации
- При создании и сохранении пользователя в базу данных, автоматически кодировать его пароль, используя одну из реализаций PasswordEncoder

#### Необязательное задание продвинутой сложности:

- Написать также несколько тестов для проверки работы слоя Dao/Repository. Для прогона этих тестов создать и подключить базу данных H2
- Добавить страницу логина на фронт-енд. Готовый шаблон можно найти [здесь](https://material-ui.com/getting-started/templates/)
- Добавить возможность авторизации пользователя с помощью OAuth2.0 и сервиса Google

#### Литература:
- [Тестирование вместе с Spring Boot. Часть 1](https://otus.ru/nest/post/428/)
- [Тестирование вместе с Spring Boot. Часть 2](https://otus.ru/nest/post/429/)
- [Introduction to Java Config for Spring Security](https://www.baeldung.com/java-config-spring-security)
