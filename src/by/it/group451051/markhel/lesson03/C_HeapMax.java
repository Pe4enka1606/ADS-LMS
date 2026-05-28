package by.it.group451051.markhel.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class C_HeapMax {

    private class MaxHeap {
        private List<Long> heap = new ArrayList<>();

        // Просеивание вниз (восстановление свойства кучи сверху вниз)
        int siftDown(int i) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            // Сравниваем с левым потомком
            if (left < heap.size() && heap.get(left) > heap.get(largest)) {
                largest = left;
            }

            // Сравниваем с правым потомком
            if (right < heap.size() && heap.get(right) > heap.get(largest)) {
                largest = right;
            }

            // Если текущий элемент не самый большой, меняем местами и продолжаем просеивание
            if (largest != i) {
                Collections.swap(heap, i, largest);
                return siftDown(largest);
            }

            return i;
        }

        // Просеивание вверх (восстановление свойства кучи снизу вверх)
        int siftUp(int i) {
            if (i == 0) return i; // Если корень, выходим

            int parent = (i - 1) / 2; // Индекс родителя

            // Если текущий элемент больше родителя, меняем местами и продолжаем
            if (heap.get(i) > heap.get(parent)) {
                Collections.swap(heap, i, parent);
                return siftUp(parent);
            }

            return i;
        }

        // Вставка нового элемента в кучу
        void insert(Long value) {
            heap.add(value); // Добавляем в конец
            siftUp(heap.size() - 1); // Восстанавливаем свойства кучи
        }

        // Извлечение максимального элемента (корня)
        Long extractMax() {
            if (heap.isEmpty()) return null;

            Long result = heap.get(0); // Максимальный элемент - корень
            heap.set(0, heap.get(heap.size() - 1)); // Перемещаем последний элемент в корень
            heap.remove(heap.size() - 1); // Удаляем последний элемент

            if (!heap.isEmpty()) {
                siftDown(0); // Восстанавливаем свойства кучи
            }

            return result;
        }

        // Для отладки (не требуется по заданию)
        @Override
        public String toString() {
            return heap.toString();
        }
    }

    // Эта процедура читает данные из файла, ее можно не менять.
    Long findMaxValue(InputStream stream) {
        Long maxValue = 0L;
        MaxHeap heap = new MaxHeap();
        Scanner scanner = new Scanner(stream);
        Integer count = scanner.nextInt();
        scanner.nextLine(); // Считываем перевод строки после числа

        for (int i = 0; i < count; ) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                Long res = heap.extractMax();
                if (res != null) {
                    System.out.println(res);
                    if (res > maxValue) maxValue = res;
                }
                i++;
            } else if (s.contains(" ")) {
                String[] p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert")) {
                    heap.insert(Long.parseLong(p[1]));
                    i++;
                }
            }
        }
        scanner.close();
        return maxValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX=" + instance.findMaxValue(stream));
    }

    // РЕМАРКА. Это задание исключительно учебное.
    // Свои собственные кучи нужны довольно редко.
    // "В реальном бою" все существенно иначе. Изучите и используйте коллекции
    // TreeSet, TreeMap, PriorityQueue и т.д. с нужным CompareTo() для объекта внутри.
}