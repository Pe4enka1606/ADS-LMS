package by.it.group451051.kozlova.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Видеорегистраторы и площадь 2.
Условие то же что и в задаче А.

        По сравнению с задачей A доработайте алгоритм так, чтобы
        1) он оптимально использовал время и память:
            - за стек отвечает элиминация хвостовой рекурсии,
            - за сам массив отрезков - сортировка на месте
            - рекурсивные вызовы должны проводиться на основе 3-разбиения

        2) при поиске подходящих отрезков для точки реализуйте метод бинарного поиска
        для первого отрезка решения, а затем найдите оставшуюся часть решения
        (т.е. отрезков, подходящих для точки, может быть много)

    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/


public class C_QSortOptimized {

    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            if (this.start != o.start) return Integer.compare(this.start, o.start);
            return Integer.compare(this.stop, o.stop);
        }
    }


    int[] getAccessory2(InputStream stream) {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        //число точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        //читаем сами отрезки
        for (int i = 0; i < n; i++) {
            //читаем начало и конец каждого отрезка
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        //читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        quickSort(segments, 0, n - 1);

        for (int i = 0; i < m; i++) {
            int p = points[i];
            int idx = getSegmentIndex(segments, p);
            if (idx == -1) {
                result[i] = 0;
            } else if (segments[idx].stop >= p) {
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    private void quickSort(Segment[] a, int low, int high) {
        while (low < high) {
            if (high - low < 10) {
                insertionSort(a, low, high);
                break;
            } else {
                int[] pivots = partition(a, low, high);
                int lt = pivots[0], gt = pivots[1];

                if (lt - low < high - gt) {
                    quickSort(a, low, lt - 1);
                    low = gt + 1;
                } else {
                    quickSort(a, gt + 1, high);
                    high = lt - 1;
                }
            }
        }
    }

    private int[] partition(Segment[] a, int low, int high) {
        Segment pivot = a[low];
        int lt = low, i = low + 1, gt = high;
        while (i <= gt) {
            int cmp = a[i].compareTo(pivot);
            if (cmp < 0) swap(a, lt++, i++);
            else if (cmp > 0) swap(a, i, gt--);
            else i++;
        }
        return new int[]{lt, gt};
    }

    private void insertionSort(Segment[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Segment key = a[i];
            int j = i - 1;
            while (j >= low && a[j].compareTo(key) > 0) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    private void swap(Segment[] a, int i, int j) {
        Segment tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private int getSegmentIndex(Segment[] segments, int p) {
        int low = 0;
        int high = segments.length - 1;
        int res = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (segments[mid].start <= p) {
                res = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        for (int count : result) {
            System.out.print(count + " ");
        }
    }

}
