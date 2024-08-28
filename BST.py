# Binary search tree
class Node:
    def __init__(self, data):
        self.data = data
        self.left = None
        self.right = None

def insert(root, value):
    if root is None:
        return Node(value)
    if root.data > value:
        root.left = insert(root.left, value)
    elif root.data < value:
        root.right = insert(root.right, value)
    return root

def inorder(root):
    if root:
        inorder(root.left)
        print(root.data, end=" ")
        inorder(root.right)
# LC-R-RC
def preorder(root):
    if root:
        print(root.data, end=" ")
        preorder(root.left)
        preorder(root.right)
# R-LC-RC
def postorder(root):
    if root:
        postorder(root.left)
        postorder(root.right)
        print(root.data, end=" ")
# LC-RC-R
def search(root, key):
    if root is None:
        return False
    if root.data > key:
        return search(root.left, key)
    elif root.data == key:
        return True
    else:
        return search(root.right, key)

def delete(root, val):
    if root is None:
        return root
    if root.data > val:
        root.left = delete(root.left, val)
    elif root.data < val:
        root.right = delete(root.right, val)
    else:
        # replace value from left side most value

        if root.left is None:
            return root.right
        elif root.right is None:
            return root.left
        temp = root.right
        while temp.left is not None:
            temp = temp.left
        root.data = temp.data
        root.right = delete(root.right, temp.data)
    return root
def print_range(root, first_element, second_element):
    if root is None:
        return
    if root.data >= first_element and root.data <= second_element:
        print_range(root.left, first_element, second_element)
        print(root.data, end=" ")
        print_range(root.right, first_element, second_element)
    elif root.data >= second_element:
        print_range(root.left, first_element, second_element)
    else:
        print_range(root.right, first_element, second_element)

def print_path(path):
    for i in path:
        print(i, "-->", end=" ")
    print()

def print_root_to_leaf(root, path):
    if root is None:
        return
    path.append(root.data)
    if root.left is None and root.right is None:
        print_path(path)
    else:
        print_root_to_leaf(root.left, path)
        print_root_to_leaf(root.right, path)
    path.pop()

def sum_bst(root):
    if root is None:
        return 0
    return sum_bst(root.left) + root.data + sum_bst(root.right)

def lca(root, value_1, value_2):
    if root is None:
        return None
    if root.data == value_1 or root.data == value_2:
        return root
    left_lca = lca(root.left, value_1, value_2)
    right_lca = lca(root.right, value_1, value_2)
    if left_lca and right_lca:
        return root
    if left_lca:
        return left_lca
    else:
        return right_lca

def min_value(root):
    if root.left is None:
        return root.data
    return min_value(root.left)

def max_value(root):
    if root.right is None:
        return root.data
    return max_value(root.right)

if __name__ == "__main__":
    root = None

    while True:
        print("--------------------------------------------")
        print("******WELCOME TO BINARY SEARCH TREE******")
        print("--------------------------------------------")
        print("1.Insert element")
        print("2.Print inorder traversal")
        print("3.Preorder traversal")
        print("4.Postorder traversal")
        print("5.Search node")
        print("6.Delete node")
        print("7.Print range")
        print("8.Print path from root to leaf")
        print("9.Sum of all nodes")
        print("10.Lowest common ancestor of two nodes")
        print("11.Minimum value in BST")
        print("12.Maximum value in BST")
        print("13.Exit")
        choice = input("Please enter your choice: ")

        try:
            choice = int(choice)
            if choice < 1 or choice > 13:
                raise ValueError
        except ValueError:
            print("Invalid choice. Please enter a number between 1 and 13.")
            continue

        if choice == 1:
            try:
                num_of_elements = int(input("Enter how many elements you want to insert: "))
                for _ in range(num_of_elements):
                    value = int(input("Enter element: "))
                    root = insert(root, value)
            except ValueError:
                print("Invalid input. Please enter integers only.")

        elif choice in [2, 3, 4]:
            if choice == 2:
                print("Inorder:", end=" ")
                inorder(root)
            elif choice == 3:
                print("Preorder:", end=" ")
                preorder(root)
            elif choice == 4:
                print("Postorder:", end=" ")
                postorder(root)
            print()

        elif choice == 5:
            try:
                key = int(input("Enter the node you want to search: "))
                if search(root, key):
                    print("Node found!")
                else:
                    print("Node not found.")
            except ValueError:
                print("Invalid input. Please enter an integer.")

        elif choice == 6:
            try:
                delete_node = int(input("Enter the node you want to delete: "))
                if search(root, delete_node):
                    root = delete(root, delete_node)
                    print("Node deleted.")
                else:
                    print("Node not found.")
            except ValueError:
                print("Invalid input. Please enter an integer.")

        elif choice == 7:
            try:
                first_element = int(input("Enter the first element: "))
                second_element = int(input("Enter the second element: "))
                print("Elements within the range:")
                print_range(root, first_element, second_element)
                print()
            except ValueError:
                print("Invalid input. Please enter integers.")

        elif choice == 8:
            print("Paths from root to leaf:")
            print_root_to_leaf(root, [])

        elif choice == 9:
            print("Sum of all nodes:", sum_bst(root))

        elif choice == 10:
            try:
                first_node = int(input("Enter the first node: "))
                second_node = int(input("Enter the second node: "))
                if search(root, first_node) and search(root, second_node):
                    print("Lowest common ancestor:", lca(root, first_node, second_node).data)
                else:
                    print("Nodes not found.")
            except ValueError:
                print("Invalid input. Please enter integers.")

        elif choice == 11:
            print("Minimum value in BST:", min_value(root))

        elif choice == 12:
            print("Maximum value in BST:", max_value(root))

        elif choice == 13:
            print("...THANKS FOR VISITING...")
            break
