# Импортируем библиотеку SQLAlchemy
from sqlalchemy import create_engine, String, Integer
from sqlalchemy.orm import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy import Column

# Создаем движок базы данных
engine = create_engine("sqlite:///my_database.db")

# Создаем базовую модель
Base = declarative_base()

# Создаем классы задач и пользователей
class Task(Base):
    __tablename__ = "tasks"

    id = Column(Integer, primary_key=True, autoincrement=True)
    title = Column(String(255))
    description = Column(String(255))
    status = Column(String(255))

class User(Base):
    __tablename__ = "users"
    
    id = Column(Integer, primary_key=True)
    username = Column(String(255), unique=True)
    password = Column(String(255))

# Создаем сеансовую фабрику
Session = sessionmaker(bind=engine)

# Создаем сессию
session = Session()

# Создаем таблицы
Base.metadata.create_all(engine)

# Добавляем задачу
task = Task(title="My first task", description="This is my first task.", status="New")

session.add(task)
session.commit()

# Получаем все задачи
tasks = session.query(Task).all()

for task in tasks:
    print(task.title)

# Закрываем сессию
session.close()