# Подключаемся к базе данных
import psycopg2

connection = psycopg2.connect(
    host="localhost",
    port=5432,
    database="tasks",
    user="postgres",
    password="Gykmex-sixka4-nimpep",
)
cursor = connection.cursor()

# Создаем таблицу users
cursor.execute(
    """CREATE TABLE users (
      id INT PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      login VARCHAR(255) NOT NULL,
      password VARCHAR(255) NOT NULL
    );"""
)
connection.commit()

# Создаем таблицу tasks
cursor.execute(
    """CREATE TABLE tasks (
      id SERIAL PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      description TEXT,
      deadline DATE,
      user_id INT
    );"""
)
connection.commit()

# Закрываем соединение
connection.close()