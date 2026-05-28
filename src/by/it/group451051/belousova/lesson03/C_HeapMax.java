package by.it.group451051.belousova.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C_HeapMax {

    private class MaxHeap {
        private List<Long> heap = new ArrayList<>();

        // Просеивание вниз (рекурсивное)
        int siftDown(int i) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;
            int size = heap.size();

            if (left < size && heap.get(left) > heap.get(largest)) {
                largest = left;
            }

            if (right < size && heap.get(right) > heap.get(largest)) {
                largest = right;
            }

            if (largest != i) {
                swap(i, largest);
                siftDown(largest);
            }

            return i;
        }

        // Просеивание вверх (рекурсивное)
        int siftUp(int i) {
            if (i == 0) return i;

            int parent = (i - 1) / 2;
            if (heap.get(i) > heap.get(parent)) {
                swap(i, parent);
                return siftUp(parent);
            }

            return i;
        }

        // Вставка элемента
        void insert(Long value) {
            heap.add(value);
            siftUp(heap.size() - 1);
        }

        // Извлечение и удаление максимума
        Long extractMax() {
            if (heap.isEmpty()) {
                return null;
            }

            Long max = heap.get(0);
            int lastIndex = heap.size() - 1;

            // Перемещаем последний элемент в корень
            heap.set(0, heap.get(lastIndex));
            heap.remove(lastIndex);

            // Просеиваем новый корень вниз
            if (!heap.isEmpty()) {
                siftDown(0);
            }

            return max;
        }

        // Вспомогательный метод для обмена элементов
        private void swap(int i, int j) {
            Long temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    }

    // Процедура чтения данных из файла (ИСПРАВЛЕННАЯ ВЕРСИЯ)
    Long findMaxValue(InputStream stream) {
        Long maxValue = 0L;
        MaxHeap heap = new MaxHeap();
        Scanner scanner = new Scanner(stream);

        // Читаем количество операций
        Integer count = scanner.nextInt();
        scanner.nextLine(); // Важно: считываем оставшийся символ новой строки

        for (int i = 0; i < count; i++) {
            String s = scanner.nextLine().trim();

            if (s.equalsIgnoreCase("extractMax")) {
                Long res = heap.extractMax();
                if (res != null) {
                    System.out.println(res); // Выводим извлеченный максимум
                    if (res > maxValue) {
                        maxValue = res;
                    }
                }
            } else if (s.startsWith("Insert")) {
                String[] parts = s.split(" ");
                if (parts.length >= 2) {
                    Long value = Long.parseLong(parts[1]);
                    heap.insert(value);
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
}