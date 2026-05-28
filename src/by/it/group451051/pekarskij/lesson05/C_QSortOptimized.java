package by.it.group451051.pekarskij.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_QSortOptimized {

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
            // сортировка по началу
            return Integer.compare(this.start, o.start);
        }
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
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

        // сортировка отрезков (3‑разбиение + tail recursion elimination)
        quickSort3(segments, 0, n - 1);

        // обработка каждой точки
        for (int i = 0; i < m; i++) {
            int x = points[i];

            // бинарный поиск первого сегмента с start > x
            int pos = upperBound(segments, x);

            int count = 0;

            // проверяем только сегменты с start <= x
            for (int j = pos - 1; j >= 0; j--) {
                if (segments[j].stop >= x) count++;
                else break; // дальше start <= x, но stop < x — можно остановиться
            }

            result[i] = count;
        }

        return result;
    }

    // бинарный поиск: первый индекс, где start > x
    private int upperBound(Segment[] a, int x) {
        int left = 0;
        int right = a.length;

        while (left < right) {
            int mid = (left + right) / 2;

            if (a[mid].start <= x) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    // быстрая сортировка с 3‑разбиением и элиминацией хвостовой рекурсии
    private void quickSort3(Segment[] a, int left, int right) {
        while (left < right) {

            int lt = left;
            int gt = right;
            Segment pivot = a[left];
            int i = left;

            // 3‑разбиение
            while (i <= gt) {
                int cmp = a[i].compareTo(pivot);

                if (cmp < 0) {
                    swap(a, lt, i);
                    lt++;
                    i++;
                } else if (cmp > 0) {
                    swap(a, i, gt);
                    gt--;
                } else {
                    i++;
                }
            }

            // рекурсивно сортируем меньшую часть
            if (lt - left < right - gt) {
                quickSort3(a, left, lt - 1);
                left = gt + 1; // хвостовая рекурсия
            } else {
                quickSort3(a, gt + 1, right);
                right = lt - 1; // хвостовая рекурсия
            }
        }
    }

    private void swap(Segment[] a, int i, int j) {
        Segment t = a[i];
        a[i] = a[j];
        a[j] = t;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
