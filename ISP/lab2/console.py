import argparse
from ISP.lab2.serializer.serializer import JsonSerializer, YamlSerializer, TomlSerializer, PickleSerializer
from utils import read_config

def convert_format(input_file, output_file, input_format, output_format):
    # Определите типы сериализаторов на основе переданных форматов
    serializers = {
        'json': JsonSerializer,
        'yaml': YamlSerializer,
        'toml': TomlSerializer,
        'pickle': PickleSerializer
    }

    # Откройте входной файл для чтения
    with open(input_file, 'r') as f:
        data = f.read()

    # Определите типы сериализаторов на основе переданных форматов
    input_serializer = serializers[input_format]
    output_serializer = serializers[output_format]

    # Десериализуйте данные с использованием входного сериализатора
    obj = input_serializer.loads(data)

    # Сериализуйте объект с использованием выходного сериализатора
    serialized_data = output_serializer.dumps(obj)

    # Запишите сериализованные данные в выходной файл
    with open(output_file, 'w') as f:
        f.write(serialized_data)

def main():
    parser = argparse.ArgumentParser(description='Сериализатор')
    parser.add_argument('input', help='Путь к входному файлу')
    parser.add_argument('output', help='Путь к выходному файлу')
    parser.add_argument('output_format', help='Формат выходных данных', choices=['json', 'yaml', 'toml', 'pickle'])
    parser.add_argument('--input_format', help='Формат входных данных', choices=['json', 'yaml', 'toml', 'pickle'])
    parser.add_argument('--config', help='Путь к конфигурационному файлу')

    args = parser.parse_args()

    input_file = args.input
    output_file =args.output
    output_format = args.output_format
    input_format = args.input_format

    if args.config:
        config = read_config(args.config)
        input_file = config['input']
        output_file = config['output']
        input_format = config.get('input_format')
        output_format = config['output_format']

    convert_format(input_file, output_file, input_format, output_format)

if __name__ == '__main__':
    main()