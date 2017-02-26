class Node(object):

    def __init__(self, data=None, next_node=None):
        self.data = data
        self.next_node = next_node

    def get_data(self):
        return self.data

    def get_next(self):
        return self.next_node

    def set_next(self, new_next):
        self.next_node = new_next


class LinkedList(object):

    def __init__(self, head=None):
        self.head = head

    def insert(self, data):
        new_node = Node(data)
        new_node.set_next(self.head)
        self.head = new_node

    def appendLast(self, data):
       new_node = Node(data)
       if self.head is None:
            self.head = new_node
            return

       last = self.head
       while (last.get_next()):
           last = last.get_next()

       last.set_next(new_node)

    def size(self):
        current = self.head
        count = 0
        while current:
            count += 1
            current = current.get_next()
        return count

    def search(self, data):
        current = self.head
        found = False
        while current and found is False:
            if current.get_data() == data:
                found = True
            else:
                current = current.get_next()
        if current is None:
            raise ValueError("Data not in list")
        return current

    def delete(self, data):
        current = self.head
        previous = None
        found = False
        while current and found is False:
            if current.get_data() == data:
                found = True
            else:
                previous = current
                current = current.get_next()
        if current is None:
            raise ValueError("Data not in list")
        if previous is None:
            self.head = current.get_next()
        else:
            previous.set_next(current.get_next())

    # Given a reference to the head of a list
    # and a position, delete the node at a given position
    def deleteHead(self):
        delNode = self.head
        self.head = delNode.get_next()
        delNode = None
        return

    def deleteNode(self, position):
        # If linked list is empty
        if self.head == None:
            return

        # Store head node
        temp = self.head

        # If head needs to be removed
        if position == 0:
            self.head = temp.get_next()
            temp = None
            return

        # Find previous node of the node to be deleted
        for i in range(position -1 ):
            temp = temp.get_next()
            if temp is None:
                break

        # If position is more than number of nodes
        if temp is None:
            return
        if temp.get_next() is None:
            return

        # Node temp.next is the node to be deleted
        # store pointer to the next of node to be deleted
        next = temp.get_next().get_next()

        # Unlink the node from linked list
        temp.set_next(None)

        temp.set_next(next)

    def printList(self):
        sub_list = []
        temp = self.head
        while temp:
            sub_list.append(temp.get_data())
            #print temp.data,
            temp = temp.get_next()
        return sub_list
