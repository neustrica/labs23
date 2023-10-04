from serializer import create_serializer

def convert_format(source_file, target_file, source_format, target_format):
    serializer = create_serializer(source_format)
    data = serializer.load(source_file)
    serializer = create_serializer(target_format)
    serializer.dump(data, target_file)