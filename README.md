### Как развернуть приложение с помощью maven:
#### Приложению нужно для работы нужно:
1. PostgreSQL(можно в докере)
2. Maven (для развёртывания с помощью maven, логично)
3. JDK 8
4. Подключение к интернету

#### Установка PostgreSQL
1. Установка Docker'a:
В случае linux есть отличная статья от DigitalOcean:
 - [Как установить и использовать Docker в Ubuntu 16.04](https://www.digitalocean.com/community/tutorials/docker-ubuntu-16-04-ru)
(Аналоги под Centos/Fedora так же есть на DigitalOcean.)
Отмечу два момента при установке под линукс:
- Обязательно дайте возможность докеру запускаться без sudo. 
- После выполнения пункта выше перезагрузите компьютер иначе будет ошибка связанная с permsission.
 - Для Window есть статья на сайте самого докера.
 [Install Docker for Windows](https://docs.docker.com/docker-for-windows/install/#about-windows-containers)
 2. Установка PostgreSQL:
 Используйте команду:
 ````
 docker pull postgres:10.4
 ````
 После скачивания докером запустите его используя команду
 ````
 docker run -d  -p 5432:5432 postgres:10.4 -e
 ````
 Это поднимет его на его стандартном порту 5432 как daemon. Т.е по просту в фоне.
 
 Если вы пользуетесь Intellij Idea рекомендую установить расширение для неё "Docker Integration"
  - Для этого нажмите Ctrl+Shift+A
  - Введите Plugins
  - Выберите пункт и откройте его
  - Нажмите Browse Repository
  - Введите в строке поиска Docker Integration
  - Нажмите Install
  - После завершения процесса установки нажмите Restart Intellij Idea (это перезагрузит среду разработки)
  - После перезагрузки IDE(среды разработки) нажмите Ctrl+Shift+A
  - В выпадающем меню введите Docker, появится иконка в виде синего кита, выберите её
  - После чего откроется и закрепится в нижней части IDE панель с плагином Docker'a.
  - Если она закрыта нажмите на неё
  - Нажмите configure если подключение к докеру не созданно.
  - В появившемся меню нажмите '+' и выберите Unix Socket если это linux и tcp socket если это Windows
  - #####В случае Window: 
  - Нужно для localhost'a разрешить подключения без tls. 
  - Для этого откройте в трее Docker нажав по нему правой кнопкой мыши и выбрав Settings
  - Поставьте галочку напротив "Expose daemon on tcp://localhost:2375 without TLS"
  - После этого подключение должно проходить
  - #####В случае Linux:
  - Подключение может не проходить потому что вы не разрешали докеру запускаться без sudo
  - Для этого введите в терминале линукс команду ```` sudo usermod -aG docker $(whoami) ````
  - Перезагрузите компьютер. Все опыты на линуксе проходили под Ubuntu/Arch Linux/Linux Mint 
  - Все диструбиутивы linux использовали ядро линукса 4.10+
  - Тесты проводились и на Centos, но в качестве desktop системы она не подходит(по понятным причинам)
  - ##### После успешного подключения
  - Откройте панель инструментов Docker(находится на нижней панели Intellij Idea)
  - В открывшемся окне два раза нажмите на Docker
  - Отобразится список контейнеров
  - Нажмите левой кнопкой мыши по контейнеру и убедитесь, что это Postgres открыв вкладку Properties в строке Image ID
  - Нажмите правой кнопкой мыши по контейнеру и убедитесь, что он запущен, конечно, делайте это, если не уверены, что он запустился
  - Если выключен запустите. 
  
  
После установки Postgresql необходимо указать в директории `src/main/resources` в файле `properties.conf`
настройки подключения. Рекомендую не использовать базу данных по умолчанию(postgres она является служебной и не подходит для работы приложения)
- Если базы данных нет, создайте откройте терминал:
- Введите `docker exec -it имя_контейнера_без_слеша bash`
- Должно получится что-то вроде `docker exec -it reverent_ardinghelli bash`
- Вы увидите что-то вроде этого:
> root@fawfawffaw:/# 

- Далее в терминале введите `psql -U postgres`
- Окажетесь в терминале postgres, здесь и далее работает синтаксис SQL и команды postgres'a
- Для создания базы данных введите `CREATE DATABASE devdb;`
- Это создаст базу данных с именем devdb; 
- Далее для выхода из postgres введите ```\q``` и потом ``exit`` для выхода из контейнера
- Теперь в `properties.conf` можно указать базу данных devdb
Примерно вот, что у вас должно получится:
```
  host=log4jdbc:postgresql://localhost
  port=5432
  database=devdb
  username=postgres
  password=
  ```
- Программа настроенна для работы с postgresql замена postgresql в properties.conf на скажем mysql или mssql не поможет ей научиться работать с этими базами данных.
- Чтобы она с ними заработала нужно поменять библиотеку jdbc на соотвествующую. 
- Так же необходимо поменять в классе DBConnector строку `ds.setDriverClassName("org.postgresql.Driver");` 
- Где то что нужно менять это `org.postgresql.Driver` например для mysql это `com.mysql.jdbc.Driver`

> Минутка для шутки.
> - Программа умеет грабить корованы (умеет создавать таблицы сама, если их нет вместе с колонками)

После установки postgresql и работы с бд необходимо установить maven
- Для установки maven на линукс в общем случае поможет команда `sudo apt-get install maven` или `sudo pacman -S maven` или `yum install maven`
- Для установки на Windows используйте [руководство](http://www.apache-maven.ru/install.html)

После установки maven 
- Используйте следующую команду в катологе программы: `mvm clean install`
- Если во время выполнения тесты упали(а они упали), не разочаровывайтесь, посмотрите ошибку.
- Если ошибка говорит об отсутствии подключения или бд, то у вас ошибка.
- Если выдаёт ошибку о том, что таблицы не созданы, то программа ещё не была запущенна и не создала таблицы.
- Т.е всё ок.
- Далее используйте команду `mvn jetty:run`
- После запуска перейдите по ссылке [localhost:8080/companyregister](http://localhost:8080/companyregister)
- Можно нажать Ctrl+C чтобы завершить выполнение программы
- И выполнить снова команду `mvn clean install` после чего пользователь для авторизации будет создан
- Теперь запустите приложение вновь с помощью `mvn jetty:run`
- И используйте логин user пароль password для авторизации.
######Если авторизация не проходит и в консоли ошибка, но форма авторизации отображается.
- Значит maven не запустил все тесты и пользователь не создан. 
- Необходимо вручную его создать.
- Для этого есть два варианта. 
- Первый это напрямую в базу данных залить user'a 
######(Краткий гайд)
 ```
 docker exec -it reverent_ardinghelli bash
 psql -U postgres
 INSERT INTO public.users (id, username, password) VALUES (1, 'user', '5f4dcc3b5aa765d61d8327deb882cf99');
 \q
 exit
 ```
- Второй у вас нет подключения к базе данных, так как приложение пытается на каждое подключение, если пользователей == 0 создать пользователя.

Готово. Вы великолепны!