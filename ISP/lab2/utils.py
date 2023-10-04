import argparse
import os
import configparser

def read_file(file):
    with open(file, 'r') as f:
        return f.read()

def write_file(data, file):
    with open(file, 'w') as f:
        f.write(data)

def parse_arguments():
    parser = argparse.ArgumentParser(description='Serialization Converter')
    parser.add_argument('source_file', help='Path to the source file')
    parser.add_argument('target_file', help='Path to the target file')
    parser.add_argument('source_format', help='Source file format')
    parser.add_argument('target_format', help='Target file format')
    return parser.parse_args()

def read_config(file):
    config = configparser.ConfigParser()
    config.read(file)
    return config