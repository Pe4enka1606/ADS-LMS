package by.it.group451051.markhel.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_QSortOptimized {

    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = Math.min(start, stop);
            this.stop = Math.max(start, stop);
        }

        @Override
        public int compareTo(Segment o) {
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            }
            return Integer.compare(this.stop, o.stop);
        }
    }

    // 3-разбиение с элиминацией хвостовой рекурсии
    private void quickSort3Way(Segment[] arr, int low, int high) {
        while (low < high) {
            int[] pi = partition3Way(arr, low, high);

            // Рекурсивно сортируем меньшую часть, а большую обрабатываем в цикле
            if (pi[0] - low < high - pi[1]) {
                quickSort3Way(arr, low, pi[0] - 1);
                low = pi[1] + 1;
            } else {
                quickSort3Way(arr, pi[1] + 1, high);
                high = pi[0] - 1;
            }
        }
    }

    private int[] partition3Way(Segment[] arr, int low, int high) {
        Segment pivot = arr[low + (high - low) / 2];
        int i = low;
        int j = high;
        int k = low;

        while (k <= j) {
            int cmp = arr[k].compareTo(pivot);
            if (cmp < 0) {
                swap(arr, i, k);
                i++;
                k++;
            } else if (cmp > 0) {
                swap(arr, k, j);
                j--;
            } else {
                k++;
            }
        }

        return new int[]{i, j};
    }

    private void swap(Segment[] arr, int i, int j) {
        Segment temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Бинарный поиск первого отрезка, который может покрывать точку
    private int binarySearchFirst(Segment[] segments, int point) {
        int left = 0;
        int right = segments.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (segments[mid].start <= point) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Segment[] segments = new Segment[n];
        int[] points = new int[m];
        int[] result = new int[m];

        // Читаем отрезки
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        // Читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортируем отрезки с использованием оптимизированной быстрой сортировки
        quickSort3Way(segments, 0, n - 1);

        // Для каждой точки ищем покрывающие отрезки
        for (int i = 0; i < m; i++) {
            int point = points[i];
            int count = 0;

            // Находим первый потенциально подходящий отрезок
            int firstIdx = binarySearchFirst(segments, point);

            if (firstIdx != -1) {
                // Проверяем все отрезки от 0 до firstIdx
                for (int j = 0; j <= firstIdx; j++) {
                    if (point >= segments[j].start && point <= segments[j].stop) {
                        count++;
                    }
                }
            }

            result[i] = count;
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
