package by.it.group451051.mozol.lesson12;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.NoSuchElementException;

public class MySplayMap implements NavigableMap<Integer, String> {

    // Внутренний класс для узла Splay-дерева
    private static class Node {
        Integer key;
        String value;
        Node left, right, parent;

        Node(Integer key, String value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }

    private Node root;
    private int size;

    public MySplayMap() {
        this.root = null;
        this.size = 0;
    }

    // Вспомогательные методы ротаций
    private void rotateLeft(Node x) {
        Node y = x.right;
        if (y == null) return;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        if (y == null) return;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // Операция Splay: проталкивает узел x в корень дерева
    private void splay(Node x) {
        if (x == null) return;
        while (x.parent != null) {
            Node p = x.parent;
            Node g = p.parent;
            if (g == null) {
                // Случай Zig
                if (x == p.left) rotateRight(p);
                else rotateLeft(p);
            } else if (x == p.left && p == g.left) {
                // Случай Zig-Zig
                rotateRight(g);
                rotateRight(p);
            } else if (x == p.right && p == g.right) {
                // Случай Zig-Zig
                rotateLeft(g);
                rotateLeft(p);
            } else if (x == p.right && p == g.left) {
                // Случай Zig-Zag
                rotateLeft(p);
                rotateRight(g);
            } else {
                // Случай Zig-Zag
                rotateRight(p);
                rotateLeft(g);
            }
        }
    }

    // Поиск узла по ключу. Если точного совпадения нет, делает splay для последнего посещенного узла.
    private Node findNode(Integer key) {
        Node curr = root;
        Node last = null;
        while (curr != null) {
            last = curr;
            int cmp = key.compareTo(curr.key);
            if (cmp < 0) curr = curr.left;
            else if (cmp > 0) curr = curr.right;
            else {
                splay(curr);
                return curr;
            }
        }
        if (last != null) {
            splay(last); // Продвигаем ближайший узел в корень для оптимизации последующих запросов
        }
        return null;
    }

    // Рекурсивный центрированный обход дерева для формирования строки
    private void inOrderString(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderString(node.left, sb);
            if (sb.length() > 1) sb.append(", ");
            sb.append(node.key).append("=").append(node.value);
            inOrderString(node.right, sb);
        }
    }

    // Поиск узла с минимальным ключом в поддереве
    private Node minimum(Node node) {
        if (node == null) return null;
        while (node.left != null) node = node.left;
        return node;
    }

    // Поиск узла с максимальным ключом в поддереве
    private Node maximum(Node node) {
        if (node == null) return null;
        while (node.right != null) node = node.right;
        return node;
    }

    private boolean containsValue(Node node, String value) {
        if (node == null) return false;
        if (value == null ? node.value == null : value.equals(node.value)) return true;
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    // Навигационные функции поиска ключей (lower, floor, ceiling, higher)
    private Node findClosest(Integer key, boolean strict, boolean isLower) {
        Node curr = root;
        Node best = null;
        while (curr != null) {
            int cmp = key.compareTo(curr.key);
            if (isLower) {
                if (cmp > 0 || (!strict && cmp == 0)) {
                    best = curr;
                    curr = curr.right; // пытаемся найти большее значение, но меньшее/равное key
                } else {
                    curr = curr.left;
                }
            } else {
                if (cmp < 0 || (!strict && cmp == 0)) {
                    best = curr;
                    curr = curr.left;  // пытаемся найти меньшее значение, но большее/равное key
                } else {
                    curr = curr.right;
                }
            }
        }
        if (best != null) {
            splay(best);
        }
        return best;
    }

    // Наполнение подкарт диапазонов
    private void collectSubMap(Node node, Integer boundary, boolean isHead, MySplayMap target) {
        if (node == null) return;
        collectSubMap(node.left, boundary, isHead, target);
        int cmp = node.key.compareTo(boundary);
        if (isHead && cmp < 0) {
            target.put(node.key, node.value);
        } else if (!isHead && cmp >= 0) {
            target.put(node.key, node.value);
        }
        collectSubMap(node.right, boundary, isHead, target);
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

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
        if (key == null) throw new NullPointerException();
        if (root == null) {
            root = new Node(key, value, null);
            size = 1;
            return null;
        }

        findNode(key); // Если ключ есть, он теперь в корне `root`
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            String oldVal = root.value;
            root.value = value;
            return oldVal;
        }

        Node newNode = new Node(key, value, null);
        if (cmp < 0) {
            newNode.right = root;
            newNode.left = root.left;
            if (root.left != null) root.left.parent = newNode;
            root.left = null;
            root.parent = newNode;
        } else {
            newNode.left = root;
            newNode.right = root.right;
            if (root.right != null) root.right.parent = newNode;
            root.right = null;
            root.parent = newNode;
        }
        root = newNode;
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof Integer)) return null;
        Node node = findNode((Integer) key);
        if (node == null || !node.key.equals(key)) return null;

        String oldVal = node.value;
        // Корень дерева гарантированно является удаляемым узлом благодаря findNode
        if (node.left == null) {
            root = node.right;
            if (root != null) root.parent = null;
        } else {
            Node leftSubtree = node.left;
            leftSubtree.parent = null;
            root = leftSubtree;
            // Ищем максимум в левом поддереве и выталкиваем его наверх
            Node maxLeft = maximum(leftSubtree);
            splay(maxLeft);
            // Привязываем правое поддерево к новому корню
            root.right = node.right;
            if (node.right != null) node.right.parent = root;
        }
        size--;
        return oldVal;
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof Integer)) return null;
        Node node = findNode((Integer) key);
        return (node != null && node.key.equals(key)) ? node.value : null;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof Integer)) return false;
        Node node = findNode((Integer) key);
        return node != null && node.key.equals(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, (String) value);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Integer firstKey() {
        if (root == null) throw new NoSuchElementException();
        Node minNode = minimum(root);
        splay(minNode);
        return minNode.key;
    }

    @Override
    public Integer lastKey() {
        if (root == null) throw new NoSuchElementException();
        Node maxNode = maximum(root);
        splay(maxNode);
        return maxNode.key;
    }

    @Override
    public Integer lowerKey(Integer key) {
        Node node = findClosest(key, true, true);
        return node == null ? null : node.key;
    }

    @Override
    public Integer floorKey(Integer key) {
        Node node = findClosest(key, false, true);
        return node == null ? null : node.key;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        Node node = findClosest(key, false, false);
        return node == null ? null : node.key;
    }

    @Override
    public Integer higherKey(Integer key) {
        Node node = findClosest(key, true, false);
        return node == null ? null : node.key;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        if (toKey == null) throw new NullPointerException();
        MySplayMap subMap = new MySplayMap();
        collectSubMap(root, toKey, true, subMap);
        return subMap;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        if (fromKey == null) throw new NullPointerException();
        MySplayMap subMap = new MySplayMap();
        collectSubMap(root, fromKey, false, subMap);
        return subMap;
    }

    /////////////////////////////////////////////////////////////////////////
    //////   Заглушки под остальные методы интерфейса NavigableMap    //////
    /////////////////////////////////////////////////////////////////////////

    @Override public Entry<Integer, String> lowerEntry(Integer key) { return null; }
    @Override public Entry<Integer, String> floorEntry(Integer key) { return null; }
    @Override public Entry<Integer, String> ceilingEntry(Integer key) { return null; }
    @Override public Entry<Integer, String> higherEntry(Integer key) { return null; }
    @Override public Entry<Integer, String> firstEntry() { return null; }
    @Override public Entry<Integer, String> lastEntry() { return null; }
    @Override public Entry<Integer, String> pollFirstEntry() { return null; }
    @Override public Entry<Integer, String> pollLastEntry() { return null; }
    @Override public NavigableMap<Integer, String> descendingMap() { return null; }
    @Override public NavigableSet<Integer> navigableKeySet() { return null; }
    @Override public NavigableSet<Integer> descendingKeySet() { return null; }
    @Override public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) { return null; }
    @Override public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) { return null; }
    @Override public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) { return null; }
    @Override public Comparator<? super Integer> comparator() { return null; }
    @Override public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) { return null; }
    @Override public void putAll(Map<? extends Integer, ? extends String> m) {}
    @Override public Set<Integer> keySet() { return null; }
    @Override public Collection<String> values() { return null; }
    @Override public Set<Entry<Integer, String>> entrySet() { return null; }
}