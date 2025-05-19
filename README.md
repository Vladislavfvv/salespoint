# Sales Point System Backend

## Описание

Бэкенд-система для торговых точек с использованием PostgreSQL, Redis, pgAdmin и Swagger UI в Docker-среде.

---

## Быстрый старт с Docker

### Скачайте и запустите всё

```bash
docker compose up --build
```

### Остановить и удалить контейнеры

```bash
docker compose down
```

---

## PostgreSQL

- Имя контейнера: `salespoint`
- Данные хранятся в `./salespoint-data`
- Инициализация из `./initdb`
- Доступ:
  - Пользователь: `lesson`
  - Пароль: `lesson`
  - База: `salespoint`
- Подключение: `localhost:5440`

---

## Redis

### Проверка работоспособности Redis

```bash
docker exec -it redis_cache redis-cli
ping
# Ожидаемый ответ: PONG
```

---

## Полезные команды

### Посмотреть все работающие контейнеры

```bash
docker ps
```

### Сбилдить и запустить в фоне

```bash
docker compose up --build -d
```

---

## pgAdmin

- Имя контейнера: `pgadmin`
- Интерфейс: [http://localhost:8080](http://localhost:8080)
- Логин: `admin@admin.com`
- Пароль: `admin`

---

## Swagger UI

- Имя контейнера: `salespoint-swagger-ui`
- Интерфейс: [http://localhost:8085](http://localhost:8085)
- Источник API: `http://host.docker.internal:9050/v3/api-docs`

---

## Redis — Запуск отдельно (если нужно)

```bash
docker run --name redis -p 6379:6379 -d redis
```

---

## Проверка подключения к сервису (например, API)

```bash
docker exec -it salespoint ping configuration-server
```

---

## В приложении реализованы регистрация и авторизация через форму по URL:
- Регистрация: [http://localhost:9050/register](http://localhost:9050/register)
- Регистрация: [http://localhost:9050/login](http://localhost:9050/login)


