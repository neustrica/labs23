class MCBBlock:

    def __init__(self, data):
        self.data = data
        self.next = None
        self.prev = None

    def __str__(self):
        return f"MCBBlock({self.data})"

    def add_next(self, new_block):
        """
        Добавляет новый блок памяти после текущего блока.
        """
        new_block.prev = self
        new_block.next = self.next
        if self.next is not None:
            self.next.prev = new_block
        self.next = new_block

    def add_prev(self, new_block):
        """
        Добавляет новый блок памяти перед текущим блоком.
        """
        new_block.next = self
        new_block.prev = self.prev
        if self.prev is not None:
            self.prev.next = new_block
        self.prev = new_block

    def remove(self):
        """
        Удаляет текущий блок памяти.
        """
        if self.prev is not None:
            self.prev.next = self.next
        if self.next is not None:
            self.next.prev = self.prev
        self.prev = None
        self.next = None

    def find(self, data):
        """
        Находит блок памяти с заданным данными.
        """
        current = self
        while current is not None:
            if current.data == data:
                return current
            current = current.next
        return None


class List:

    def __init__(self):
        self.head = None
        self.tail = None

    def push_front(self, data):
        """
        Добавление элемента в начало списка.
        """
        new_node = MCBBlock(data)
        new_node.next = self.head
        if self.head is not None:
            self.head.prev = new_node
        self.head = new_node
        if self.tail is None:
            self.tail = new_node

    def push_back(self, data):
        """
        Добавление элемента в конец списка.
        """
        new_node = MCBBlock(data)
        if self.tail is not None:
            self.tail.next = new_node
            new_node.prev = self.tail
        self.tail = new_node
        if self.head is None:
            self.head = new_node

    def pop_front(self):
        """
        Удаление элемента из начала списка.
        """
        if self.head is None:
            raise ValueError("Список пуст")
        data = self.head.data
        self.head = self.head.next
        if self.head is None:
            self.tail = None
        return data

    def pop_back(self):
        """
        Удаление элемента из конца списка.
        """
        if self.tail is None:
            raise ValueError("Список пуст")
        data = self.tail.data
        self.tail = self.tail.prev
        if self.tail is None:
            self.head = None
        return data

    def __len__(self):
        """
        Возвращает количество элементов в списке.
        """
        current = self.head
        count = 0
        while current is not None:
            count += 1
            current = current.next
        return count

    def __iter__(self):
        """
        Итератор по элементам списка.
        """
        current = self.head
        while current is not None:
            yield current.data
            current = current.next


class Queue:

    def __init__(self):
        self.head = None
        self.tail = None

    def enqueue(self, data):
        """
        Добавление элемента в очередь.
        """
        new_node = MCBBlock(data)
        if self.head is None:
            self.head = new_node
            self.tail = new_node
        else:
            self.tail.next = new_node
            new_node.prev = self.tail
            self.tail = new_node

    def dequeue(self):
        """
        Удаление элемента из очереди.
        """
        if self.head is None:
            raise ValueError("Очередь пуста")
        data = self.head.data
        self.head = self.head.next
        if self.head is None:
            self.tail = None
        return data

    def __len__(self):
        """
        Возвращает количество элементов в очереди.
        """
        current = self.head
        count = 0
        while current is not None:
            count += 1
            current = current.next
        return count
    

list = List()
list.push_front(1)
list.push_front(2)
list.push_back(3)
print(list)
# [2, 1, 3]

list.pop_front()
print(list)
# [1, 3]

list.pop_back()
print(list)
# [1]


queue = Queue()
queue.enqueue(1)
queue.enqueue(2)
queue.enqueue(3)
print(queue)
# [1, 2, 3]

queue.dequeue()
print(queue)
# [2, 3]

queue.dequeue()
print(queue)
# [3]