package by.it.group451051.mozol.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {

    private static class Node {
        Integer key;
        String value;
        Node left, right, parent;
        Node(Integer key, String value, Node parent) {
            this.key = key; this.value = value; this.parent = parent;
        }
    }

    private Node root;
    private int size;

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

    private void splay(Node x) {
        while (x.parent != null) {
            Node p = x.parent, g = p.parent;
            if (g == null) {
                if (x == p.left) rotateRight(p); else rotateLeft(p);
            } else if (x == p.left && p == g.left) { rotateRight(g); rotateRight(p); }
            else if (x == p.right && p == g.right) { rotateLeft(g); rotateLeft(p); }
            else if (x == p.right && p == g.left) { rotateLeft(p); rotateRight(g); }
            else { rotateRight(p); rotateLeft(g); }
        }
    }

    private void rotateLeft(Node x) {
        Node y = x.right; x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x; x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left; x.left = y.right;
        if (y.right != null) y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.right) x.parent.right = y;
        else x.parent.left = y;
        y.right = x; x.parent = y;
    }

    private Node findNode(Integer key) {
        Node curr = root, last = null;
        while (curr != null) {
            last = curr;
            int cmp = key.compareTo(curr.key);
            if (cmp < 0) curr = curr.left;
            else if (cmp > 0) curr = curr.right;
            else { splay(curr); return curr; }
        }
        if (last != null) splay(last);
        return null;
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null) throw new NullPointerException();
        if (root == null) { root = new Node(key, value, null); size = 1; return null; }
        Node found = findNode(key);
        if (found != null && found.key.equals(key)) {
            String old = found.value; found.value = value; return old;
        }
        Node newNode = new Node(key, value, null);
        if (key < root.key) {
            newNode.right = root; newNode.left = root.left;
            if (root.left != null) root.left.parent = newNode;
            root.left = null; root.parent = newNode;
        } else {
            newNode.left = root; newNode.right = root.right;
            if (root.right != null) root.right.parent = newNode;
            root.right = null; root.parent = newNode;
        }
        root = newNode; size++; return null;
    }

    @Override public String remove(Object key) {
        if (!(key instanceof Integer)) return null;
        Node node = findNode((Integer) key);
        if (node == null || !node.key.equals(key)) return null;
        String val = node.value;
        if (node.left == null) { root = node.right; if (root != null) root.parent = null; }
        else {
            Node leftSub = node.left; leftSub.parent = null; root = leftSub;
            Node max = root; while (max.right != null) max = max.right;
            splay(max); root.right = node.right;
            if (node.right != null) node.right.parent = root;
        }
        size--; return val;
    }

    @Override public String get(Object key) {
        if (!(key instanceof Integer)) return null;
        Node n = findNode((Integer) key); return (n != null && n.key.equals(key)) ? n.value : null;
    }

    @Override public boolean containsKey(Object key) {
        return (key instanceof Integer) && findNode((Integer) key) != null;
    }

    @Override public boolean containsValue(Object value) {
        return checkVal(root, value);
    }

    private boolean checkVal(Node n, Object val) {
        if (n == null) return false;
        return Objects.equals(n.value, val) || checkVal(n.left, val) || checkVal(n.right, val);
    }

    @Override public int size() { return size; }
    @Override public void clear() { root = null; size = 0; }
    @Override public boolean isEmpty() { return size == 0; }

    @Override public Integer firstKey() { if(root==null) throw new NoSuchElementException(); Node n = root; while(n.left!=null) n=n.left; splay(n); return n.key; }
    @Override public Integer lastKey() { if(root==null) throw new NoSuchElementException(); Node n = root; while(n.right!=null) n=n.right; splay(n); return n.key; }

    @Override public SortedMap<Integer, String> headMap(Integer to) { MySplayMap m = new MySplayMap(); collect(root, m, to, true); return m; }
    @Override public SortedMap<Integer, String> tailMap(Integer from) { MySplayMap m = new MySplayMap(); collect(root, m, from, false); return m; }
    private void collect(Node n, MySplayMap t, Integer key, boolean head) {
        if(n==null) return;
        if(head ? n.key < key : n.key >= key) t.put(n.key, n.value);
        collect(n.left, t, key, head); collect(n.right, t, key, head);
    }

    @Override
    public Integer lowerKey(Integer key) {
        Node curr = root;
        Integer result = null;
        while (curr != null) {
            if (curr.key < key) {
                result = curr.key;
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        return result;
    }

    @Override
    public Integer floorKey(Integer key) {
        Node curr = root;
        Integer result = null;
        while (curr != null) {
            if (curr.key <= key) {
                result = curr.key;
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        return result;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        Node curr = root;
        Integer result = null;
        while (curr != null) {
            if (curr.key >= key) {
                result = curr.key;
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return result;
    }

    @Override
    public Integer higherKey(Integer key) {
        Node curr = root;
        Integer result = null;
        while (curr != null) {
            if (curr.key > key) {
                result = curr.key;
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return result;
    }

    @Override public Comparator<? super Integer> comparator() { return null; }
    @Override public SortedMap<Integer, String> subMap(Integer f, Integer t) { return null; }
    @Override public void putAll(Map<? extends Integer, ? extends String> m) {}
    @Override public Set<Integer> keySet() { return null; }
    @Override public Collection<String> values() { return null; }
    @Override public Set<Entry<Integer, String>> entrySet() { return null; }
    @Override public Entry<Integer, String> lowerEntry(Integer k) { return null; }
    @Override public Entry<Integer, String> floorEntry(Integer k) { return null; }
    @Override public Entry<Integer, String> ceilingEntry(Integer k) { return null; }
    @Override public Entry<Integer, String> higherEntry(Integer k) { return null; }
    @Override public Entry<Integer, String> firstEntry() { return null; }
    @Override public Entry<Integer, String> lastEntry() { return null; }
    @Override public Entry<Integer, String> pollFirstEntry() { return null; }
    @Override public Entry<Integer, String> pollLastEntry() { return null; }
    @Override public NavigableMap<Integer, String> descendingMap() { return null; }
    @Override public NavigableSet<Integer> navigableKeySet() { return null; }
    @Override public NavigableSet<Integer> descendingKeySet() { return null; }
    @Override public NavigableMap<Integer, String> subMap(Integer f, boolean fi, Integer t, boolean ti) { return null; }
    @Override public NavigableMap<Integer, String> headMap(Integer t, boolean i) { return null; }
    @Override public NavigableMap<Integer, String> tailMap(Integer f, boolean i) { return null; }
}