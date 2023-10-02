import json
from ISP.lab2.serializer.serializer import JsonSerializer

def test_json_serializer():
    obj = {'name': 'John', 'age': 30}

    # Тест метода dumps
    serialized_obj = JsonSerializer.dumps(obj)
    expected_serialized_obj = json.dumps(obj)
    assert serialized_obj == expected_serialized_obj

    # Тест метода loads
    deserialized_obj = JsonSerializer.loads(serialized_obj)
    expected_deserialized_obj = json.loads(serialized_obj)
    assert deserialized_obj == expected_deserialized_obj