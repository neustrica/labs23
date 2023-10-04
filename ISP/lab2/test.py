import unittest
import os
from serializer import JsonSerializer

class SerializerTestCase(unittest.TestCase):
    def setUp(self):
        self.serializer = JsonSerializer()

    def test_dump(self):
        data = {'name': 'John', 'age': 30}
        filename = 'test.json'

        # Сериализация объекта в файл
        self.serializer.dump(data, filename)

        # Проверка, что файл создан
        self.assertTrue(os.path.exists(filename))
        print(f"Файл {filename} успешно создан.")

        # Десериализация объекта из файла
        loaded_data = self.serializer.load(filename)

        # Проверка, что десериализованный объект совпадает с исходным
        self.assertEqual(data, loaded_data)
        print("Данные успешно десериализованы.")

        # Удаление временного файла
        os.remove(filename)
        print(f"Файл {filename} успешно удален.")

    def test_dumps(self):
        data = {'name': 'John', 'age': 30}

        # Сериализация объекта в строку
        serialized_data = self.serializer.dumps(data)

        # Проверка, что сериализованная строка не пустая
        self.assertIsNotNone(serialized_data)
        print("Объект успешно сериализован в строку:", serialized_data)

        # Десериализация объекта из строки
        loaded_data = self.serializer.loads(serialized_data)

        # Проверка, что десериализованный объект совпадает с исходным
        self.assertEqual(data, loaded_data)
        print("Строка успешно десериализована в объект.")

    def test_load(self):
        data = {'name': 'John', 'age': 30}
        filename = 'test.json'

        # Сохранение данных в файл
        with open(filename, 'w') as file:
            file.write('{"name": "John", "age": 30}')

        # Загрузка данных из файла
        loaded_data = self.serializer.load(filename)

        # Проверка, что загруженные данные совпадают с исходными
        self.assertEqual(data, loaded_data)
        print("Данные успешно загружены из файла.")

    def test_loads(self):
        data = {'name': 'John', 'age': 30}
        serialized_data = '{"name": "John", "age": 30}'

        # Загрузка данных из строки
        loaded_data = self.serializer.loads(serialized_data)

        # Проверка, что загруженные данные совпадают с исходными
        self.assertEqual(data, loaded_data)
        print("Данные успешно загружены из строки.")

if __name__ == '__main__':
    unittest.main()