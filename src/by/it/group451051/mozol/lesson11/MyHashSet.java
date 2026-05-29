package by.it.group451051.mozol.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {

    // Внутренний класс для узла односвязного списка коллизий
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }

    // Массив корзин (бакетов)
    private Node<E>[] table;
    // Текущее количество уникальных элементов в множестве
    private int size;

    // Константы для начальной емкости и фактора загрузки (аналог стандартного Java HashSet)
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        this.table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Вспомогательный метод для вычисления индекса в массиве по хэш-коду объекта
    private int getIndex(Object o, int capacity) {
        if (o == null) {
            return 0; // Null-элементы традиционно складываем в нулевой бакет
        }
        // Маскируем знак (убираем отрицательные значения) и берем остаток от деления
        return (o.hashCode() & 0x7FFFFFFF) % capacity;
    }

    // Динамическое расширение таблицы и перехэширование всех элементов (Rehash)
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = table.length * 2;
        Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

        // Пробегаемся по всем старым корзинам
        for (int i = 0; i < table.length; i++) {
            Node<E> current = table[i];
            while (current != null) {
                Node<E> next = current.next; // Сохраняем ссылку на следующий элемент списка

                // Перевычисляем индекс для новой таблицы
                int newIndex = getIndex(current.data, newCapacity);

                // Вставляем узел в начало нового списка (в начало новой корзины)
                current.next = newTable[newIndex];
                newTable[newIndex] = current;

                current = next;
            }
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
    public void clear() {
        // Зануляем все бакеты, чтобы сборщик мусора очистил цепочки узлов
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        // Проверяем, если коэффициент загрузки превышен, расширяем таблицу перед вставкой
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(e, table.length);
        Node<E> current = table[index];

        // Ищем элемент в цепочке, чтобы избежать дубликатов
        while (current != null) {
            if (e == null ? current.data == null : e.equals(current.data)) {
                return false; // Элемент уже существует, Set не изменяется
            }
            current = current.next;
        }

        // Если дошли сюда — элемента нет в таблице. Создаем узел и вставляем в начало списка корзины
        Node<E> newNode = new Node<>(e);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o, table.length);
        Node<E> current = table[index];
        Node<E> prev = null;

        // Поиск элемента в односвязном списке коллизий
        while (current != null) {
            if (o == null ? current.data == null : o.equals(current.data)) {
                // Если элемент найден — вырезаем узел из цепочки
                if (prev == null) {
                    table[index] = current.next; // Удаление из головы списка
                } else {
                    prev.next = current.next;    // Удаление из середины/конца списка
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false; // Элемент не найден
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o, table.length);
        Node<E> current = table[index];

        // Линейный поиск внутри бакета (в среднем $O(1)$, если мало коллизий)
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
        boolean firstElement = true;

        for (int i = 0; i < table.length; i++) {
            Node<E> current = table[i];
            while (current != null) {
                if (!firstElement) {
                    sb.append(", ");
                }
                sb.append(current.data);
                firstElement = false;
                current = current.next;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /////////////////////////////////////////////////////////////////////////
    //////        Заглушки для остальных методов интерфейса Set       //////
    /////////////////////////////////////////////////////////////////////////

    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
    @Override public boolean containsAll(Collection<?> c) { return false; }
    @Override public boolean addAll(Collection<? extends E> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
}
