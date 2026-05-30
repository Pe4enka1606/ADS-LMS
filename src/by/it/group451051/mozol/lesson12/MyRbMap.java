package by.it.group451051.mozol.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

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

    private boolean isRed(Node n) { return n != null && n.color == RED; }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public void clear() { root = null; size = 0; }

    @Override
    public String get(Object key) {
        Node n = getNode(key);
        return n == null ? null : n.value;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null) throw new NullPointerException();
        if (root == null) {
            root = new Node(key, value, BLACK, null);
            size = 1;
            return null;
        }
        Node curr = root, parent = null;
        int cmp = 0;
        while (curr != null) {
            parent = curr;
            cmp = key.compareTo(curr.key);
            if (cmp < 0) curr = curr.left;
            else if (cmp > 0) curr = curr.right;
            else {
                String old = curr.value;
                curr.value = value;
                return old;
            }
        }
        Node node = new Node(key, value, RED, parent);
        if (cmp < 0) parent.left = node; else parent.right = node;
        fixAfterInsertion(node);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        Node p = getNode(key);
        if (p == null) return null;
        String val = p.value;
        deleteNode(p);
        size--;
        return val;
    }

    private void deleteNode(Node p) {
        if (p.left != null && p.right != null) {
            Node s = p.right;
            while (s.left != null) s = s.left;
            p.key = s.key; p.value = s.value;
            p = s;
        }
        Node replacement = (p.left != null ? p.left : p.right);
        if (replacement != null) {
            replacement.parent = p.parent;
            if (p.parent == null) root = replacement;
            else if (p == p.parent.left) p.parent.left = replacement;
            else p.parent.right = replacement;
            if (p.color == BLACK) fixAfterDeletion(replacement);
        } else if (p.parent == null) {
            root = null;
        } else {
            if (p.color == BLACK) fixAfterDeletion(p);
            if (p.parent != null) {
                if (p == p.parent.left) p.parent.left = null;
                else p.parent.right = null;
            }
        }
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x; x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.right) x.parent.right = y;
        else x.parent.left = y;
        y.right = x; x.parent = y;
    }

    private void fixAfterInsertion(Node x) {
        while (x != root && isRed(x.parent)) {
            if (x.parent == x.parent.parent.left) {
                Node y = x.parent.parent.right;
                if (isRed(y)) { x.parent.color = BLACK; y.color = BLACK; x.parent.parent.color = RED; x = x.parent.parent; }
                else { if (x == x.parent.right) { x = x.parent; rotateLeft(x); } x.parent.color = BLACK; x.parent.parent.color = RED; rotateRight(x.parent.parent); }
            } else {
                Node y = x.parent.parent.left;
                if (isRed(y)) { x.parent.color = BLACK; y.color = BLACK; x.parent.parent.color = RED; x = x.parent.parent; }
                else { if (x == x.parent.left) { x = x.parent; rotateRight(x); } x.parent.color = BLACK; x.parent.parent.color = RED; rotateLeft(x.parent.parent); }
            }
        }
        root.color = BLACK;
    }

    private void fixAfterDeletion(Node x) {
        while (x != root && !isRed(x)) {
            if (x == x.parent.left) {
                Node sib = x.parent.right;
                if (isRed(sib)) { sib.color = BLACK; x.parent.color = RED; rotateLeft(x.parent); sib = x.parent.right; }
                if (!isRed(sib.left) && !isRed(sib.right)) { sib.color = RED; x = x.parent; }
                else { if (!isRed(sib.right)) { sib.left.color = BLACK; sib.color = RED; rotateRight(sib); sib = x.parent.right; } sib.color = x.parent.color; x.parent.color = BLACK; sib.right.color = BLACK; rotateLeft(x.parent); x = root; }
            } else {
                Node sib = x.parent.left;
                if (isRed(sib)) { sib.color = BLACK; x.parent.color = RED; rotateRight(x.parent); sib = x.parent.left; }
                if (!isRed(sib.right) && !isRed(sib.left)) { sib.color = RED; x = x.parent; }
                else { if (!isRed(sib.left)) { sib.right.color = BLACK; sib.color = RED; rotateLeft(sib); sib = x.parent.left; } sib.color = x.parent.color; x.parent.color = BLACK; sib.left.color = BLACK; rotateRight(x.parent); x = root; }
            }
        }
        if (x != null) x.color = BLACK;
    }

    private Node getNode(Object key) {
        if (!(key instanceof Integer)) return null;
        Node curr = root;
        Integer k = (Integer) key;
        while (curr != null) {
            int cmp = k.compareTo(curr.key);
            if (cmp < 0) curr = curr.left;
            else if (cmp > 0) curr = curr.right;
            else return curr;
        }
        return null;
    }

    @Override
    public String toString() {
        if (root == null) return "{}";
        StringBuilder sb = new StringBuilder("{");
        inOrder(root, sb);
        sb.setLength(sb.length() - 2);
        return sb.append("}").toString();
    }

    private void inOrder(Node n, StringBuilder sb) {
        if (n == null) return;
        inOrder(n.left, sb);
        sb.append(n.key).append("=").append(n.value).append(", ");
        inOrder(n.right, sb);
    }

    @Override
    public Integer firstKey() {
        if (root == null) throw new NoSuchElementException();
        Node n = root;
        while (n.left != null) n = n.left;
        return n.key;
    }

    @Override
    public Integer lastKey() {
        if (root == null) throw new NoSuchElementException();
        Node n = root;
        while (n.right != null) n = n.right;
        return n.key;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MyRbMap map = new MyRbMap();
        fillMap(root, map, toKey, true);
        return map;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MyRbMap map = new MyRbMap();
        fillMap(root, map, fromKey, false);
        return map;
    }

    private void fillMap(Node n, MyRbMap target, Integer key, boolean head) {
        if (n == null) return;
        if (head ? (n.key < key) : (n.key >= key)) target.put(n.key, n.value);
        fillMap(n.left, target, key, head);
        fillMap(n.right, target, key, head);
    }

    @Override
    public boolean containsValue(Object value) {
        return checkVal(root, value);
    }

    private boolean checkVal(Node n, Object val) {
        if (n == null) return false;
        if (Objects.equals(n.value, val)) return true;
        return checkVal(n.left, val) || checkVal(n.right, val);
    }

    @Override public Comparator<? super Integer> comparator() { return null; }
    @Override public SortedMap<Integer, String> subMap(Integer f, Integer t) { return null; }
    @Override public void putAll(Map<? extends Integer, ? extends String> m) {}
    @Override public Set<Integer> keySet() { return null; }
    @Override public Collection<String> values() { return null; }
    @Override public Set<Entry<Integer, String>> entrySet() { return null; }
}