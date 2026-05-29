package by.it.group451051.mozol.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.Deque;
import java.util.NoSuchElementException;

public class MyArrayDeque<E> implements Deque<E> {

    // Внутренний массив для хранения элементов
    private E[] elements;
    // Указатель на начало очереди
    private int head;
    // Указатель на конец очереди (индекс для вставки следующего элемента в хвост)
    private int tail;

    // Константа начальной емкости (обязательно степень двойки для быстрой битовой маски или арифметики)
    private static final int INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        this.elements = (E[]) new Object[INITIAL_CAPACITY];
        this.head = 0;
        this.tail = 0;
    }

    // Вспомогательный метод динамического расширения кольцевого буфера
    @SuppressWarnings("unchecked")
    private void grow() {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity << 1; // Увеличиваем емкость в 2 раза
        if (newCapacity < 0) {
            throw new IllegalStateException("Sorry, deque too big");
        }

        E[] newElements = (E[]) new Object[newCapacity];

        // Копируем элементы от head до конца старого массива
        int r = oldCapacity - head;
        System.arraycopy(elements, head, newElements, 0, r);
        // Копируем оставшиеся элементы от начала массива до tail
        System.arraycopy(elements, 0, newElements, r, head);

        this.elements = newElements;
        this.head = 0;
        this.tail = oldCapacity; // Размер заполненной структуры был равен старой емкости
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int h = head;
        int t = tail;
        int mask = elements.length - 1;

        while (h != t) {
            sb.append(elements[h]);
            h = (h + 1) & mask; // Шагаем вперед по кольцу
            if (h != t) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        // Вычисляем размер в циклическом кольце через битовую маску длины
        return (tail - head) & (elements.length - 1);
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public void addFirst(E element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        // Сдвигаем head влево в рамках кольцевого буфера
        head = (head - 1) & (elements.length - 1);
        elements[head] = element;

        // Если буфер закольцевался и заполнился — расширяем массив
        if (head == tail) {
            grow();
        }
    }

    @Override
    public void addLast(E element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        elements[tail] = element;
        // Сдвигаем tail вправо в рамках кольцевого буфера
        tail = (tail + 1) & (elements.length - 1);

        // Если указатели встретились — структура полностью заполнена
        if (tail == head) {
            grow();
        }
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        E element = elements[head];
        if (element == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        return element;
    }

    @Override
    public E getLast() {
        // Последний добавленный элемент лежит в ячейке перед tail
        E element = elements[(tail - 1) & (elements.length - 1)];
        if (element == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        return element;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        int h = head;
        E result = elements[h];
        if (result == null) {
            return null; // Метод poll обязан возвращать null, если очередь пуста
        }
        elements[h] = null; // Освобождаем ссылку для Garbage Collector
        head = (h + 1) & (elements.length - 1); // Смещаем head вправо
        return result;
    }

    @Override
    public E pollLast() {
        // Вычисляем индекс последнего занятого элемента
        int t = (tail - 1) & (elements.length - 1);
        E result = elements[t];
        if (result == null) {
            return null;
        }
        elements[t] = null; // Освобождаем ссылку
        tail = t; // Новое положение указателя tail
        return result;
    }

    /////////////////////////////////////////////////////////////////////////
    //////        Заглушки для остальных методов интерфейса Deque     //////
    /////////////////////////////////////////////////////////////////////////

    @Override public boolean offerFirst(E e) { addFirst(e); return true; }
    @Override public boolean offerLast(E e) { addLast(e); return true; }
    @Override public E removeFirst() { E res = pollFirst(); if (res == null) throw new NoSuchElementException(); return res; }
    @Override public E removeLast() { E res = pollLast(); if (res == null) throw new NoSuchElementException(); return res; }
    @Override public E peekFirst() { return elements[head]; }
    @Override public E peekLast() { return elements[(tail - 1) & (elements.length - 1)]; }
    @Override public boolean removeFirstOccurrence(Object o) { return false; }
    @Override public boolean removeLastOccurrence(Object o) { return false; }
    @Override public boolean offer(E e) { return offerLast(e); }
    @Override public E remove() { return removeFirst(); }
    @Override public E peek() { return peekFirst(); }
    @Override public void push(E e) { addFirst(e); }
    @Override public E pop() { return removeFirst(); }
    @Override public boolean remove(Object o) { return false; }
    @Override public boolean containsAll(Collection<?> c) { return false; }
    @Override public boolean addAll(Collection<? extends E> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public void clear() { head = 0; tail = 0; }
    @Override public boolean isEmpty() { return head == tail; }
    @Override public boolean contains(Object o) { return false; }
    @Override public Iterator<E> iterator() { return null; }
    @Override public Iterator<E> descendingIterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
}