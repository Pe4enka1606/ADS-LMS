package by.it.group451051.yazhou.lesson05;

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

    //отрезок
    private class Segment  implements Comparable{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = Math.min(start, stop);
            this.stop = Math.max(start, stop);
        }

        @Override
        public int compareTo(Object o) {
            //подумайте, что должен возвращать компаратор отрезков
            Segment other = (Segment) o;
            // сначала сравниваем по началу отрезка
            if (this.start != other.start) {
                return Integer.compare(this.start, other.start);
            }
            // если начала равны, сравниваем по концу
            return Integer.compare(this.stop, other.stop);
        }

        // Проверка, содержит ли отрезок точку
        boolean contains(int point) {
            return start <= point && point <= stop;
        }

    }

    // метод для обмена элементов массива
    private void swap(Segment[] arr, int i, int j) {
        Segment temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 3-разбиение (разбиение Дейкстры)
    private int[] partition3(Segment[] arr, int left, int right) {
        // выбираем опорный элемент (середина)
        int mid = left + (right - left) / 2;
        Segment pivot = arr[mid];
        // перемещаем опорный элемент в начало
        swap(arr, mid, left);

        int lt = left;      // граница элементов < pivot
        int gt = right;     // граница элементов > pivot
        int i = left + 1;   // текущий элемент

        while (i <= gt) {
            int cmp = arr[i].compareTo(pivot);
            if (cmp < 0) {
                // элемент меньше опорного
                swap(arr, lt++, i++);
            } else if (cmp > 0) {
                // элемент больше опорного
                swap(arr, i, gt--);
            } else {
                // элемент равен опорному
                i++;
            }
        }

        // возвращаем границы равных элементов
        return new int[]{lt, gt};
    }

    // быстрая сортировка с 3-разбиением и элиминацией хвостовой рекурсии
    private void quickSort3Way(Segment[] arr, int left, int right) {
        // элиминация хвостовой рекурсии через цикл
        while (left < right) {
            int[] pivots = partition3(arr, left, right);

            // рекурсивно сортируем меньшую часть, чтобы минимизировать глубину стека
            if (pivots[0] - left < right - pivots[1]) {
                quickSort3Way(arr, left, pivots[0] - 1);
                left = pivots[1] + 1;  // продолжаем с большей части
            } else {
                quickSort3Way(arr, pivots[1] + 1, right);
                right = pivots[0] - 1; // продолжаем с меньшей части
            }
        }
    }

    // бинарный поиск последнего отрезка с началом <= point
    private int binarySearchFirstSegment(Segment[] segments, int point) {
        int left = 0;
        int right = segments.length - 1;
        int result = -1; // если не найдено

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (segments[mid].start <= point) {
                // этот отрезок может содержать точку
                result = mid;
                left = mid + 1; // ищем дальше справа
            } else {
                // начало отрезка больше точки
                right = mid - 1;
            }
        }

        return result;
    }


    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments=new Segment[n];
        //число точек
        int m = scanner.nextInt();
        int[] points=new int[m];
        int[] result=new int[m];

        //читаем сами отрезки
        for (int i = 0; i < n; i++) {
            //читаем начало и конец каждого отрезка
            segments[i]=new Segment(scanner.nextInt(),scanner.nextInt());
        }
        //читаем точки
        for (int i = 0; i < m; i++) {
            points[i]=scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением быстрой сортировки
        //в классе отрезка Segment реализуйте нужный для этой задачи компаратор
        // сортируем отрезки с помощью оптимизированной быстрой сортировки
        if (n > 0) {
            quickSort3Way(segments, 0, n - 1);
        }

        // для каждой точки находим количество отрезков, которые ее содержат
        for (int i = 0; i < m; i++) {
            int point = points[i];
            int count = 0;

            if (n > 0) {
                // находим индекс последнего отрезка с началом <= точке
                int firstIndex = binarySearchFirstSegment(segments, point);

                // проверяем отрезки от firstIndex вниз до 0
                // (отрезки отсортированы по возрастанию начала)
                for (int j = firstIndex; j >= 0; j--) {
                    if (segments[j].contains(point)) {
                        count++;
                    } else if (segments[j].stop < point) {
                        // если конец отрезка меньше точки,
                        // то и все предыдущие отрезки тоже будут иметь конец < точки
                        break;
                    }
                }
            }

            result[i] = count;
        }

        scanner.close();

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/yazhou/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
