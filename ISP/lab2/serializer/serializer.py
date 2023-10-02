import json
import yaml
import toml
import pickle

class JsonSerializer:
    @staticmethod
    def dump(obj, fp):
        json.dump(obj, fp)

    @staticmethod
    def dumps(obj):
        return json.dumps(obj)

    @staticmethod
    def load(fp):
        return json.load(fp)

    @staticmethod
    def loads(s):
        return json.loads(s)

class YamlSerializer:
    @staticmethod
    def dump(obj, fp):
        yaml.dump(obj, fp)

    @staticmethod
    def dumps(obj):
        return yaml.dump(obj)

    @staticmethod
    def load(fp):
        return yaml.load(fp, Loader=yaml.Loader)

    @staticmethod
    def loads(s):
        return yaml.load(s, Loader=yaml.Loader)

class TomlSerializer:
    @staticmethod
    def dump(obj, fp):
        toml.dump(obj, fp)

    @staticmethod
    def dumps(obj):
        return toml.dumps(obj)

    @staticmethod
    def load(fp):
        return toml.load(fp)

    @staticmethod
    def loads(s):
        return toml.loads(s)

class PickleSerializer:
    @staticmethod
    def dump(obj, fp):
        pickle.dump(obj, fp)

    @staticmethod
    def dumps(obj):
        return pickle.dumps(obj)

    @staticmethod
    def load(fp):
        return pickle.load(fp)

    @staticmethod
    def loads(s):
        return pickle.loads(s)