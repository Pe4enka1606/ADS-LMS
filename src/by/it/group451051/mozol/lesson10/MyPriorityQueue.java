package by.it.group451051.mozol.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.NoSuchElementException;

public class MyPriorityQueue<E> implements Queue<E> {

    // Внутренний массив для хранения элементов двоичной кучи
    private E[] elements;
    // Текущее количество элементов в очереди
    private int size;
    // Дефолтная начальная емкость массива
    private static final int DEFAULT_CAPACITY = 11;

    @SuppressWarnings("unchecked")
    public MyPriorityQueue() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Вспомогательный метод расширения массива при заполнении кучи
    @SuppressWarnings("unchecked")
    private void grow() {
        int oldCapacity = elements.length;
        // Наращиваем емкость: аналогично стандартной PriorityQueue (примерно в 1.5-2 раза)
        int newCapacity = oldCapacity + ((oldCapacity < 64) ? (oldCapacity + 2) : (oldCapacity >> 1));
        E[] newElements = (E[]) new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    // Процедура "всплытия" элемента вверх по куче (восстановление свойств min-heap)
    @SuppressWarnings("unchecked")
    private void siftUp(int index) {
        Comparable<? super E> key = (Comparable<? super E>) elements[index];
        while (index > 0) {
            int parent = (index - 1) >>> 1; // Формула индекса родителя: (i - 1) / 2
            E parentVal = elements[parent];
            // Если текущий элемент больше или равен родителю, свойство min-heap соблюдено
            if (key.compareTo(parentVal) >= 0) {
                break;
            }
            // Сдвигаем родителя вниз
            elements[index] = parentVal;
            index = parent;
        }
        elements[index] = (E) key;
    }

    // Процедура "просеивания" элемента вниз по куче (восстановление свойств min-heap)
    @SuppressWarnings("unchecked")
    private void siftDown(int index) {
        Comparable<? super E> key = (Comparable<? super E>) elements[index];
        int half = size >>> 1; // У нелистовых узлов индексы строго меньше half
        while (index < half) {
            int leftChild = (index << 1) + 1;  // Индекс левого потомка: 2 * i + 1
            int rightChild = leftChild + 1;    // Индекс правого потомка: 2 * i + 2

            E childVal = elements[leftChild];
            int bestChild = leftChild;

            // Если есть правый потомок, и он меньше левого, выбираем его
            if (rightChild < size && ((Comparable<? super E>) childVal).compareTo(elements[rightChild]) > 0) {
                childVal = elements[rightChild];
                bestChild = rightChild;
            }

            // Если элемент меньше или равен наименьшему из потомков, останавливаемся
            if (key.compareTo(childVal) <= 0) {
                break;
            }

            // Меняем местами с наименьшим потомком
            elements[index] = childVal;
            index = bestChild;
        }
        elements[index] = (E) key;
    }

    // Вспомогательный метод для безопасного удаления элемента по его внутреннему индексу в массиве
    private E removeAt(int i) {
        size--;
        if (size == i) {
            elements[i] = null; // Если удаляем последний элемент массива, перелинковка не нужна
        } else {
            E moved = elements[size];
            elements[size] = null;
            elements[i] = moved;
            // Сначала пробуем просеять вниз, если не пошло — пробуем протолкнуть вверх
            siftDown(i);
            if (elements[i] == moved) {
                siftUp(i);
            }
        }
        return null;
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(E element) {
        return offer(element);
    }

    @Override
    public E remove() {
        E x = poll();
        if (x != null) {
            return x;
        } else {
            throw new NoSuchElementException("Queue is empty");
        }
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean offer(E element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        if (size >= elements.length) {
            grow();
        }
        elements[size] = element;
        siftUp(size);
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E result = elements[0]; // Корень кучи всегда содержит минимальный приоритет
        size--;
        E lastElement = elements[size];
        elements[size] = null; // Зачищаем ссылку для GC

        if (size > 0) {
            elements[0] = lastElement;
            siftDown(0); // Опускаем новый корень на его законное место
        }
        return result;
    }

    @Override
    public E peek() {
        return (size == 0) ? null : elements[0];
    }

    @Override
    public E element() {
        E x = peek();
        if (x != null) {
            return x;
        } else {
            throw new NoSuchElementException("Queue is empty");
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) throw new NullPointerException();
        if (c == this) throw new IllegalArgumentException();
        boolean modified = false;
        for (E e : c) {
            if (offer(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                elements[newSize++] = elements[i];
            }
        }
        boolean modified = (newSize != size);
        if (modified) {
            size = newSize;
            // После удаления нужно перестроить кучу (heapify)
            for (int i = (size >>> 1) - 1; i >= 0; i--) {
                siftDown(i);
            }
            // Зануляем хвост для GC
            for (int i = size; i < elements.length; i++) elements[i] = null;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(elements[i])) {
                elements[newSize++] = elements[i];
            }
        }
        boolean modified = (newSize != size);
        if (modified) {
            size = newSize;
            for (int i = (size >>> 1) - 1; i >= 0; i--) {
                siftDown(i);
            }
            for (int i = size; i < elements.length; i++) elements[i] = null;
        }
        return modified;
    }

    /////////////////////////////////////////////////////////////////////////
    //////        Необязательные методы интерфейса Queue/Collection   //////
    /////////////////////////////////////////////////////////////////////////

    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
    @Override public boolean remove(Object o) {
        if (o == null) return false;
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }
}