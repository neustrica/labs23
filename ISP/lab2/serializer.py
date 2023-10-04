import json
import yaml
import toml
import pickle

class JsonSerializer:
    @staticmethod
    def dump(obj, fp):
        with open(fp, 'w') as file:
            json.dump(obj, file)

    @staticmethod
    def dumps(obj):
        return json.dumps(obj)

    @staticmethod
    def load(fp):
        with open(fp, 'r') as file:
            return json.load(file)

    @staticmethod
    def loads(s):
        return json.loads(s)

class YamlSerializer:
    @staticmethod
    def dump(obj, fp):
        with open(fp, 'w') as file:
            yaml.dump(obj, file)

    @staticmethod
    def dumps(obj):
        return yaml.dump(obj)

    @staticmethod
    def load(fp):
        with open(fp, 'r') as file:
            return yaml.load(file, Loader=yaml.Loader)

    @staticmethod
    def loads(s):
        return yaml.load(s, Loader=yaml.Loader)

class TomlSerializer:
    @staticmethod
    def dump(obj, fp):
        with open(fp, 'w') as file:
            toml.dump(obj, file)

    @staticmethod
    def dumps(obj):
        return toml.dumps(obj)

    @staticmethod
    def load(fp):
        with open(fp, 'r') as file:
            return toml.load(file)

    @staticmethod
    def loads(s):
        return toml.loads(s)

class PickleSerializer:
    @staticmethod
    def dump(obj, fp):
        with open(fp, 'wb') as file:
            pickle.dump(obj, file)

    @staticmethod
    def dumps(obj):
        return pickle.dumps(obj)

    @staticmethod
    def load(fp):
        with open(fp, 'rb') as file:
            return pickle.load(file)

    @staticmethod
    def loads(s):
        return pickle.loads(s)

def create_serializer(serializer_type):
    if serializer_type == 'json':
        return JsonSerializer()
    elif serializer_type == 'yaml':
        return YamlSerializer()
    elif serializer_type == 'toml':
        return TomlSerializer()
    elif serializer_type == 'pickle':
        return PickleSerializer()
    else:
        raise ValueError('Invalid serializer type')