package by.it.group451051.mozol.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    // Внутренний класс узла, совмещающий логику бакета хэш-таблицы и двунаправленного списка
    private static class Node<E> {
        E data;
        Node<E> next;   // Ссылка на следующий узел в цепочке коллизий бакета
        Node<E> before; // Ссылка на элемент, добавленный ПЕРЕД текущим
        Node<E> after;  // Ссылка на элемент, добавленный ПОСЛЕ текущего

        Node(E data) {
            this.data = data;
        }
    }

    private Node<E>[] table; // Массив корзин (бакетов) хэш-таблицы
    private int size;        // Текущее количество уникальных элементов

    private Node<E> head;    // Голова двунаправленного списка (самый первый добавленный элемент)
    private Node<E> tail;    // Хвост двунаправленного списка (самый свежий добавленный элемент)

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        this.table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    // Вспомогательный метод вычисления индекса корзины
    private int getIndex(Object o, int capacity) {
        if (o == null) return 0;
        return (o.hashCode() & 0x7FFFFFFF) % capacity;
    }

    // Динамический рехэш (Rehash) при превышении Load Factor
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = table.length * 2;
        Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

        // Так как у нас есть глобальный связный список (head -> tail),
        // перехэшировать элементы гораздо проще последовательным обходом по нему!
        Node<E> current = head;
        while (current != null) {
            int newIndex = getIndex(current.data, newCapacity);

            // Перепривязываем указатель коллизий в рамках новой таблицы
            current.next = newTable[newIndex];
            newTable[newIndex] = current;

            current = current.after;
        }
        this.table = newTable;
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
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(E e) {
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(e, table.length);
        Node<E> current = table[index];

        // Проверяем уникальность элемента в бакете
        while (current != null) {
            if (e == null ? current.data == null : e.equals(current.data)) {
                return false; // Элемент уже есть, дубликаты в Set запрещены
            }
            current = current.next;
        }

        // Создаем новый узел
        Node<E> newNode = new Node<>(e);

        // 1. Вставляем в хэш-таблицу (в начало односвязного списка коллизий текущей корзины)
        newNode.next = table[index];
        table[index] = newNode;

        // 2. Связываем в глобальный двунаправленный список порядка добавления
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.after = newNode;
            newNode.before = tail;
            tail = newNode;
        }

        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o, table.length);
        Node<E> current = table[index];
        Node<E> prev = null;

        while (current != null) {
            if (o == null ? current.data == null : o.equals(current.data)) {

                // 1. Удаляем узел из цепочки коллизий хэш-таблицы
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }

                // 2. Вырезаем узел из глобального двунаправленного списка порядка добавления
                if (current.before == null) {
                    head = current.after; // Удаляемый элемент был первым
                } else {
                    current.before.after = current.after;
                }

                if (current.after == null) {
                    tail = current.before; // Удаляемый элемент был последним
                } else {
                    current.after.before = current.before;
                }

                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o, table.length);
        Node<E> current = table[index];
        while (current != null) {
            if (o == null ? current.data == null : o.equals(current.data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        // ВАЖНО: Обход делаем по связному списку от head к tail, а не по массиву бакетов!
        Node<E> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.after != null) {
                sb.append(", ");
            }
            current = current.after;
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) modified = true;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object e : c) {
            if (remove(e)) modified = true;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<E> current = head;
        while (current != null) {
            Node<E> nextNode = current.after; // Запоминаем ссылку, так как текущий узел можем удалить
            if (!c.contains(current.data)) {
                remove(current.data);
                modified = true;
            }
            current = nextNode;
        }
        return modified;
    }

    /////////////////////////////////////////////////////////////////////////
    //////        Заглушки для остальных методов интерфейса Set       //////
    /////////////////////////////////////////////////////////////////////////

    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
}
