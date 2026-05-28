package by.it.group451051.pekarskij.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_QSort {

    // отрезок
    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            // сортируем по началу отрезка
            return Integer.compare(this.start, o.start);
        }
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // читаем количество отрезков
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];

        // читаем количество точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        // читаем отрезки
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        // читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // сортируем отрезки по start
        quickSort(segments, 0, n - 1);

        // для каждой точки считаем количество подходящих отрезков
        for (int i = 0; i < m; i++) {
            int x = points[i];

            // ищем первый отрезок, у которого start > x
            int rightBound = upperBound(segments, x);

            int count = 0;

            // проверяем только те отрезки, у которых start <= x
            for (int j = 0; j < rightBound; j++) {
                if (segments[j].stop >= x) {
                    count++;
                }
            }

            result[i] = count;
        }

        return result;
    }

    // быстрая сортировка
    private void quickSort(Segment[] a, int left, int right) {
        if (left >= right) return;

        int mid = partition(a, left, right);
        quickSort(a, left, mid - 1);
        quickSort(a, mid + 1, right);
    }

    // разбиение массива
    private int partition(Segment[] a, int left, int right) {
        Segment pivot = a[(left + right) / 2];

        while (left <= right) {
            while (a[left].compareTo(pivot) < 0) left++;
            while (a[right].compareTo(pivot) > 0) right--;

            if (left <= right) {
                Segment tmp = a[left];
                a[left] = a[right];
                a[right] = tmp;
                left++;
                right--;
            }
        }
        return left - 1;
    }

    // бинарный поиск: первый индекс, где start > x
    private int upperBound(Segment[] a, int x) {
        int left = 0;
        int right = a.length;

        while (left < right) {
            int mid = (left + right) / 2;

            if (a[mid].start <= x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result=instance.getAccessory(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
