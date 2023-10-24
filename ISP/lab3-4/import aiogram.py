import aiogram
from aiogram.dispatcher import Dispatcher
from aiogram.types import Message

bot = aiogram.Bot(token="YOUR_TOKEN")
dp = Dispatcher(bot)

# Импортируем библиотеку для работы с базой данных PostgreSQL
import psycopg2


# Создаем соединение с базой данных
connection = psycopg2.connect(
    host="localhost",
    port=5432,
    database="tasks",
    user="postgres",
    password="password",
)
cursor = connection.cursor()


# Функции для работы с задачами

@dp.message_handler(commands="create")
async def create_task(message: Message):
    # Получаем данные о задаче из сообщения
    name = message.text.split(" ")[1]
    description = message.text.split(" ")[2]
    deadline = message.text.split(" ")[3]

    # Сохраняем задачу в базе данных
    cursor.execute(
        """INSERT INTO tasks (name, description, deadline)
        VALUES (?, ?, ?)""",
        (name, description, deadline),
    )
    connection.commit()

    # Отправляем сообщение пользователю
    await message.answer(f"Задача {name} создана")


@dp.message_handler(commands="list")
async def list_tasks(message: Message):
    # Получаем список задач из базы данных
    cursor.execute("SELECT * FROM tasks")
    tasks = cursor.fetchall()

    # Формируем сообщение с списком задач
    message_text = "Список задач:\n"
    for task in tasks:
        message_text += f"* {task[0]} - {task[1]} - {task[2]}\n"

    # Отправляем сообщение пользователю
    await message.answer(message_text)


@dp.message_handler(commands="detail")
async def detail_task(message: Message):
    # Получаем id задачи из сообщения
    task_id = message.text.split(" ")[1]

    # Получаем задачу из базы данных
    cursor.execute("SELECT * FROM tasks WHERE id = ?", (task_id,))
    task = cursor.fetchone()

    # Если задача не найдена, отправляем сообщение об ошибке
    if task is None:
        await message.answer("Задача не найдена")
        return

    # Формируем сообщение с деталями задачи
    message_text = f"Детали задачи: \n"
    message_text += f"* Название: {task[0]} \n"
    message_text += f"* Описание: {task[1]} \n"
    message_text += f"* Срок выполнения: {task[2]} \n"

    # Отправляем сообщение пользователю
    await message.answer(message_text)


@dp.message_handler(commands="update")
async def update_task(message: Message):
    # Получаем id задачи из сообщения
    task_id = message.text.split(" ")[1]

    # Получаем новые данные о задаче из сообщения
    name = message.text.split(" ")[2]
    description = message.text.split(" ")[3]
    deadline = message.text.split(" ")[4]

    # Обновляем задачу в базе данных
    cursor.execute(
        """UPDATE tasks
        SET name = ?, description = ?, deadline = ?
        WHERE id = ?""",
        (name, description, deadline, task_id),
    )
    connection.commit()

    # Отправляем сообщение пользователю
    await message.answer(f"Задача {name} обновлена")


@dp.message_handler(commands="delete")
async def delete_task(message: Message):
    # Получаем id задачи из сообщения
    task_id = message.text.split(" ")[1]

    # Удаляем задачу из базы данных
    cursor.execute("DELETE FROM tasks WHERE id = ?", (task_id,))
 
