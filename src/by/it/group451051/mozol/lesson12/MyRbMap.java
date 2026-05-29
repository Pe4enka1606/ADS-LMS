package by.it.group451051.mozol.lesson12;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.NoSuchElementException;

public class MyRbMap implements SortedMap<Integer, String> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // Внутренний класс для узла Красно-чёрного дерева
    private static class Node {
        Integer key;
        String value;
        Node left, right, parent;
        boolean color;

        Node(Integer key, String value, boolean color, Node parent) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = parent;
        }
    }

    private Node root;
    private int size;

    public MyRbMap() {
        this.root = null;
        this.size = 0;
    }

    // Вспомогательные методы навигации и проверки цвета
    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
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

    // Восстановление баланса КЧ-дерева после вставки
    private void fixAfterInsertion(Node x) {
        x.color = RED;

        while (x != null && x != root && isRed(x.parent)) {
            if (x.parent == x.parent.parent.left) {
                Node uncle = x.parent.parent.right;
                if (isRed(uncle)) { // Случай 1: дядя красный
                    x.parent.color = BLACK;
                    uncle.color = BLACK;
                    x.parent.parent.color = RED;
                    x = x.parent.parent;
                } else {
                    if (x == x.parent.right) { // Случай 2: дядя черный, узел правый потомок
                        x = x.parent;
                        rotateLeft(x);
                    }
                    // Случай 3: дядя черный, узел левый потомок
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    rotateRight(x.parent.parent);
                }
            } else {
                Node uncle = x.parent.parent.left;
                if (isRed(uncle)) { // Случай 1
                    x.parent.color = BLACK;
                    uncle.color = BLACK;
                    x.parent.parent.color = RED;
                    x = x.parent.parent;
                } else {
                    if (x == x.parent.left) { // Случай 2
                        x = x.parent;
                        rotateRight(x);
                    }
                    // Случай 3
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    rotateLeft(x.parent.parent);
                }
            }
        }
        root.color = BLACK; // Корень всегда черный
    }

    // Восстановление баланса после удаления
    private void fixAfterDeletion(Node x) {
        while (x != root && !isRed(x)) {
            if (x == null) break;
            if (x == x.parent.left) {
                Node sib = x.parent.right;

                if (isRed(sib)) {
                    sib.color = BLACK;
                    x.parent.color = RED;
                    rotateLeft(x.parent);
                    sib = x.parent.right;
                }

                if (!isRed(sib.left) && !isRed(sib.right)) {
                    sib.color = RED;
                    x = x.parent;
                } else {
                    if (!isRed(sib.right)) {
                        if (sib.left != null) sib.left.color = BLACK;
                        sib.color = RED;
                        rotateRight(sib);
                        sib = x.parent.right;
                    }
                    sib.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (sib.right != null) sib.right.color = BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                Node sib = x.parent.left;

                if (isRed(sib)) {
                    sib.color = BLACK;
                    x.parent.color = RED;
                    rotateRight(x.parent);
                    sib = x.parent.left;
                }

                if (!isRed(sib.left) && !isRed(sib.right)) {
                    sib.color = RED;
                    x = x.parent;
                } else {
                    if (!isRed(sib.left)) {
                        if (sib.right != null) sib.right.color = BLACK;
                        sib.color = RED;
                        rotateLeft(sib);
                        sib = x.parent.left;
                    }
                    sib.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (sib.left != null) sib.left.color = BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        if (x != null) x.color = BLACK;
    }

    private Node getNode(Object key) {
        if (!(key instanceof Integer)) return null;
        Integer searchKey = (Integer) key;
        Node curr = root;
        while (curr != null) {
            int cmp = searchKey.compareTo(curr.key);
            if (cmp < 0) curr = curr.left;
            else if (cmp > 0) curr = curr.right;
            else return curr;
        }
        return null;
    }

    private void deleteNode(Node p) {
        size--;
        if (p.left != null && p.right != null) {
            Node s = successor(p);
            p.key = s.key;
            p.value = s.value;
            p = s;
        }

        Node replacement = (p.left != null ? p.left : p.right);

        if (replacement != null) {
            replacement.parent = p.parent;
            if (p.parent == null) root = replacement;
            else if (p == p.parent.left) p.parent.left = replacement;
            else p.parent.right = replacement;

            p.left = p.right = p.parent = null;

            if (p.color == BLACK) fixAfterDeletion(replacement);
        } else if (p.parent == null) {
            root = null;
        } else {
            if (p.color == BLACK) fixAfterDeletion(p);

            if (p.parent != null) {
                if (p == p.parent.left) p.parent.left = null;
                else if (p == p.parent.right) p.parent.right = null;
                p.parent = null;
            }
        }
    }

    private Node successor(Node t) {
        if (t == null) return null;
        else if (t.right != null) {
            Node p = t.right;
            while (p.left != null) p = p.left;
            return p;
        } else {
            Node p = t.parent;
            Node ch = t;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private void inOrderString(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderString(node.left, sb);
            if (sb.length() > 1) sb.append(", ");
            sb.append(node.key).append("=").append(node.value);
            inOrderString(node.right, sb);
        }
    }

    private boolean containsValue(Node node, String value) {
        if (node == null) return false;
        if (value == null ? node.value == null : value.equals(node.value)) return true;
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    // Вспомогательные методы сборки диапазонов для подкарт (headMap/tailMap)
    private void collectSubMap(Node node, Integer boundary, boolean isHead, MyRbMap target) {
        if (node == null) return;
        if (isHead) {
            if (node.key.compareTo(boundary) < 0) {
                target.put(node.key, node.value);
                collectSubMap(node.left, boundary, isHead, target);
                collectSubMap(node.right, boundary, isHead, target);
            } else {
                collectSubMap(node.left, boundary, isHead, target);
            }
        } else {
            if (node.key.compareTo(boundary) >= 0) {
                target.put(node.key, node.value);
                collectSubMap(node.left, boundary, isHead, target);
                collectSubMap(node.right, boundary, isHead, target);
            } else {
                collectSubMap(node.right, boundary, isHead, target);
            }
        }
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
        Node t = root;
        if (t == null) {
            root = new Node(key, value, BLACK, null);
            size = 1;
            return null;
        }
        Node parent;
        int cmp;
        do {
            parent = t;
            cmp = key.compareTo(t.key);
            if (cmp < 0) t = t.left;
            else if (cmp > 0) t = t.right;
            else {
                String oldVal = t.value;
                t.value = value;
                return oldVal;
            }
        } while (t != null);

        Node e = new Node(key, value, RED, parent);
        if (cmp < 0) parent.left = e;
        else parent.right = e;

        fixAfterInsertion(e);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        Node p = getNode(key);
        if (p == null) return null;
        String oldVal = p.value;
        deleteNode(p);
        return oldVal;
    }

    @Override
    public String get(Object key) {
        Node p = getNode(key);
        return p == null ? null : p.value;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(key) != null;
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
    public SortedMap<Integer, String> headMap(Integer toKey) {
        if (toKey == null) throw new NullPointerException();
        MyRbMap subMap = new MyRbMap();
        collectSubMap(root, toKey, true, subMap);
        return subMap;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        if (fromKey == null) throw new NullPointerException();
        MyRbMap subMap = new MyRbMap();
        collectSubMap(root, fromKey, false, subMap);
        return subMap;
    }

    @Override
    public Integer firstKey() {
        if (root == null) throw new NoSuchElementException();
        Node p = root;
        while (p.left != null) p = p.left;
        return p.key;
    }

    @Override
    public Integer lastKey() {
        if (root == null) throw new NoSuchElementException();
        Node p = root;
        while (p.right != null) p = p.right;
        return p.key;
    }

    /////////////////////////////////////////////////////////////////////////
    //////        Заглушки для остальных методов интерфейса Map       //////
    /////////////////////////////////////////////////////////////////////////

    @Override public Comparator<? super Integer> comparator() { return null; }
    @Override public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) { return null; }
    @Override public void putAll(Map<? extends Integer, ? extends String> m) {}
    @Override public Set<Integer> keySet() { return null; }
    @Override public Collection<String> values() { return null; }
    @Override public Set<Entry<Integer, String>> entrySet() { return null; }
}
