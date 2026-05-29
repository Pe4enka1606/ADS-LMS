package by.it.group451051.mozol.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {

    // Внутренний массив для хранения элементов в отсортированном порядке
    private E[] elements;
    // Текущее количество элементов в множестве
    private int size;
    // Дефолтная начальная емкость массива
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public MyTreeSet() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Вспомогательный метод автоматического расширения массива при заполнении
    @SuppressWarnings("unchecked")
    private void grow() {
        int newCapacity = elements.length * 3 / 2 + 1; // Увеличение емкости в ~1.5 раза
        E[] newElements = (E[]) new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    // Реализация бинарного поиска. Возвращает индекс элемента, если он найден.
    // Если не найден — возвращает (-(индекс предполагаемой вставки) - 1).
    @SuppressWarnings("unchecked")
    private int binarySearch(Object key) {
        int low = 0;
        int high = size - 1;
        Comparable<? super E> searchKey = (Comparable<? super E>) key;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            E midVal = elements[mid];
            int cmp = searchKey.compareTo(midVal);

            if (cmp > 0) {
                low = mid + 1;
            } else if (cmp < 0) {
                high = mid - 1;
            } else {
                return mid; // Элемент найден
            }
        }
        return -(low + 1); // Элемент не найден
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
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException("TreeSet не поддерживает элементы null");
        }

        int index = binarySearch(e);

        // Если индекс >= 0, значит элемент уже существует, а Set хранит только уникальные значения
        if (index >= 0) {
            return false;
        }

        // Вычисляем правильную позицию для вставки отсортированного элемента
        int insertIndex = -(index + 1);

        if (size == elements.length) {
            grow();
        }

        // Сдвигаем элементы вправо, освобождая место под insertIndex
        System.arraycopy(elements, insertIndex, elements, insertIndex + 1, size - insertIndex);
        elements[insertIndex] = e;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;

        int index = binarySearch(o);

        // Элемент не найден в массиве
        if (index < 0) {
            return false;
        }

        // Сдвигаем элементы влево, затирая удаляемый элемент
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }

        elements[--size] = null; // Зануляем последний элемент для GC
        return true;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        return binarySearch(o) >= 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        // Поскольку массив всегда поддерживается в отсортированном виде,
        // мы просто выводим элементы линейно слева направо.
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
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object e : c) {
            if (remove(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                i--; // Корректируем индекс после сдвига элементов влево при удалении
                modified = true;
            }
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