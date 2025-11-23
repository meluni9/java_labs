import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class RedBlackTreeApp {
    
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node {
        int data;
        Node left, right, parent;
        boolean color;

        Node(int data) {
            this.data = data;
            this.color = RED;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node root;
    private final Node TNULL;

    public RedBlackTreeApp() {
        TNULL = new Node(0);
        TNULL.color = BLACK;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    public void insert(int key) {
        Node node = new Node(key);
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = RED;

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = BLACK;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    private void fixInsert(Node k) {
        Node u;
        while (k.parent.color == RED) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == RED) {
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                if (u.color == RED) {
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = BLACK;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void printTree() {
        if (root == TNULL) {
            System.out.println("Дерево порожнє.");
            return;
        }
        System.out.println("\n--- Структура дерева ---");
        printHelper(this.root, "", true);
        System.out.println("------------------------\n");
    }

    private void printHelper(Node root, String indent, boolean last) {
        if (root != TNULL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }

            String sColor = root.color == RED ? "RED" : "BLACK";
            System.out.println(root.data + "(" + sColor + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }
    
    public void clear() {
        root = TNULL;
    }

    public static void main(String[] args) {
        RedBlackTreeApp tree = new RedBlackTreeApp();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Меню:");
            System.out.println("1. Ввести числа вручну");
            System.out.println("2. Згенерувати випадкові числа (довільний порядок)");
            System.out.println("3. Згенерувати випадкові числа (впорядковані по зростанню)");
            System.out.println("4. Відобразити дерево");
            System.out.println("5. Очистити дерево");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            String choice = "";
            if (scanner.hasNextLine()) {
                 choice = scanner.nextLine();
            }

            switch (choice) {
                case "1":
                    System.out.print("Введіть числа через пробіл: ");
                    String input = scanner.nextLine();
                    String[] parts = input.trim().split("\\s+");
                    for (String part : parts) {
                        if (!part.isEmpty()) {
                            try {
                                int val = Integer.parseInt(part);
                                tree.insert(val);
                            } catch (NumberFormatException e) {
                                System.out.println("Пропущено '" + part + "' - не є числом.");
                            }
                        }
                    }
                    tree.printTree();
                    break;

                case "2":
                    fillRandom(tree, false, scanner);
                    break;

                case "3":
                    fillRandom(tree, true, scanner);
                    break;

                case "4":
                    tree.printTree();
                    break;
                    
                case "5":
                    tree.clear();
                    System.out.println("Дерево очищено.");
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        }
        
        scanner.close();
    }

    
    private static void fillRandom(RedBlackTreeApp tree, boolean sorted, Scanner scanner) {
        System.out.print("Кількість елементів: ");
        int count = 0;
        try {
            String line = scanner.nextLine();
            count = Integer.parseInt(line);
        } catch (Exception e) {
            System.out.println("Помилка вводу (потрібно ціле число).");
            return;
        }

        if (count <= 0) {
            System.out.println("Кількість має бути більше 0.");
            return;
        }

        int[] arr = new int[count];
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            arr[i] = rnd.nextInt(100);
        }

        if (sorted) {
            Arrays.sort(arr);
            System.out.println("Згенеровано впорядкований масив:");
        } else {
            System.out.println("Згенеровано випадковий масив:");
        }
        
        System.out.println(Arrays.toString(arr));

        for (int val : arr) {
            tree.insert(val);
        }
        
        System.out.println("Елементи додано в дерево.");
        tree.printTree();
    }
}
