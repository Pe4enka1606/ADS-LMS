package by.it.group451051.mozol.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyAvlMap implements Map<Integer, String> {

    // Внутренний класс для узла АВЛ-дерева
    private static class Node {
        Integer key;
        String value;
        int height; // Высота поддерева с корнем в данном узле
        Node left;
        Node right;

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
            this.height = 1; // Новый узел всегда имеет высоту 1
        }
    }

    private Node root; // Корень АВЛ-дерева
    private int size;  // Количество элементов в мапе

    public MyAvlMap() {
        this.root = null;
        this.size = 0;
    }

    // Вспомогательные методы для работы с высотой и балансом
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private void updateHeight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    // Правое малое вращение вокруг узла y
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Поворот
        x.right = y;
        y.left = T2;

        // Обновляем высоты
        updateHeight(y);
        updateHeight(x);

        return x; // Новый корень поддерева
    }

    // Левое малое вращение вокруг узла x
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Поворот
        y.left = x;
        x.right = T2;

        // Обновляем высоты
        updateHeight(x);
        updateHeight(y);

        return y; // Новый корень поддерева
    }

    // Балансировка узла поддерева
    private Node balance(Node node) {
        updateHeight(node);
        int balance = getBalance(node);

        // Левый-левый случай (LL) -> одинарный правый поворот
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        // Левый-правый случай (LR) -> большой правый поворот
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Правый-правый случай (RR) -> одинарный левый поворот
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        // Правый-левый случай (RL) -> большой левый поворот
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Рекурсивная вставка элемента в дерево
    private Node put(Node node, Integer key, String value, String[] oldValueHolder) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value, oldValueHolder);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value, oldValueHolder);
        } else {
            // Ключ уже существует, обновляем значение и сохраняем старое
            oldValueHolder[0] = node.value;
            node.value = value;
            return node;
        }

        return balance(node);
    }

    // Поиск узла с минимальным ключом (необходим для удаления узла с двумя потомками)
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Рекурсивное удаление элемента из дерева
    private Node remove(Node node, Integer key, String[] removedValueHolder) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key, removedValueHolder);
        } else if (cmp > 0) {
            node.right = remove(node.right, key, removedValueHolder);
        } else {
            // Узел найден!
            removedValueHolder[0] = node.value;
            size--;

            // Случай 1 или 2: один потомок или их нет
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                // Случай 3: у узла два потомка
                // Ищем преемника в правом поддереве (наименьший элемент справа)
                Node temp = minValueNode(node.right);
                node.key = temp.key;
                node.value = temp.value;
                // Удаляем преемника из правого поддерева
                String[] dummy = new String[1];
                node.right = remove(node.right, temp.key, dummy);
                size++; // Компенсируем уменьшение size, так как сам узел структуры не исчез, мы лишь перезаписали данные
            }
        }

        if (node == null) {
            return null;
        }

        return balance(node);
    }

    // Вспомогательный рекурсивный обход для формирования строки toString() по возрастанию
    private void inOrderString(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderString(node.left, sb);
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(node.key).append("=").append(node.value);
            inOrderString(node.right, sb);
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        inOrderString(root, sb);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        String[] oldValueHolder = new String[1];
        root = put(root, key, value, oldValueHolder);
        return oldValueHolder[0]; // Возвращает null, если ключ новый, или старое значение
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof Integer)) {
            return null;
        }
        String[] removedValueHolder = new String[1];
        root = remove(root, (Integer) key, removedValueHolder);
        return removedValueHolder[0];
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof Integer)) {
            return null;
        }
        Integer searchKey = (Integer) key;
        Node current = root;
        while (current != null) {
            int cmp = searchKey.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current.value; // Ключ найден
            }
        }
        return null; // Ключ не найден
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    /////////////////////////////////////////////////////////////////////////
    //////        Заглушки для остальных методов интерфейса Map       //////
    /////////////////////////////////////////////////////////////////////////

    @Override public boolean containsValue(Object value) { return false; }
    @Override public void putAll(Map<? extends Integer, ? extends String> m) {}
    @Override public Set<Integer> keySet() { return null; }
    @Override public Collection<String> values() { return null; }
    @Override public Set<Entry<Integer, String>> entrySet() { return null; }
}
