package by.it.group451051.mozol.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.Deque;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {

    // Внутренний статический класс для узла списка
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data) {
            this.data = data;
        }
    }

    private Node<E> first; // Ссылка на первый узел (head)
    private Node<E> last;  // Ссылка на последний узел (tail)
    private int size = 0;  // Текущее количество элементов

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> current = first;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    // Удаление по индексу (требуется по условию задания)
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        E oldData = current.data;
        unlink(current);
        return oldData;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> current = first; current != null; current = current.next) {
                if (current.data == null) {
                    unlink(current);
                    return true;
                }
            }
        } else {
            for (Node<E> current = first; current != null; current = current.next) {
                if (o.equals(current.data)) {
                    unlink(current);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element);
        if (last == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.data;
    }

    @Override
    public E getLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.data;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (first == null) {
            return null;
        }
        E data = first.data;
        Node<E> nextNode = first.next;

        if (nextNode == null) {
            first = null;
            last = null;
        } else {
            nextNode.prev = null;
            first = nextNode;
        }
        size--;
        return data;
    }

    @Override
    public E pollLast() {
        if (last == null) {
            return null;
        }
        E data = last.data;
        Node<E> prevNode = last.prev;

        if (prevNode == null) {
            first = null;
            last = null;
        } else {
            prevNode.next = null;
            last = prevNode;
        }
        size--;
        return data;
    }

    // Вспомогательный приватный метод для "вырезания" узла из цепочки ссылок
    private void unlink(Node<E> node) {
        Node<E> nextNode = node.next;
        Node<E> prevNode = node.prev;

        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }

        node.data = null;
        size--;
    }

    /////////////////////////////////////////////////////////////////////////
    //////        Заглушки для остальных методов Deque/Queue           //////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        E res = pollFirst();
        if (res == null) throw new NoSuchElementException();
        return res;
    }

    @Override
    public E removeLast() {
        E res = pollLast();
        if (res == null) throw new NoSuchElementException();
        return res;
    }

    @Override
    public E peekFirst() {
        return first == null ? null : first.data;
    }

    @Override
    public E peekLast() {
        return last == null ? null : last.data;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            for (Node<E> current = last; current != null; current = current.prev) {
                if (current.data == null) {
                    unlink(current);
                    return true;
                }
            }
        } else {
            for (Node<E> current = last; current != null; current = current.prev) {
                if (o.equals(current.data)) {
                    unlink(current);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Node<E> curr = first; curr != null; curr = curr.next) {
            if (o == null ? curr.data == null : o.equals(curr.data)) return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }
    @Override
    public boolean offer(E e) {
        return false;
    }
}
