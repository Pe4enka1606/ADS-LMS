package by.it.group451051.shiman.lesson05;

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

    //отрезок - добавлен static для корректной работы Comparable
    private static class Segment implements Comparable<Segment> { // Исправлено: добавлен generic тип
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) { // Исправлено: правильная сигнатура метода
            // Сравниваем сначала по началу, затем по концу
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            }
            return Integer.compare(this.stop, o.stop);
        }
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
        for (int i = 0; i < m; i++) { // Исправлено: должно быть m, а не n
            points[i]=scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением быстрой сортировки
        //в классе отрезка Segment реализуйте нужный для этой задачи компаратор

        // Сортируем отрезки с помощью оптимизированной быстрой сортировки с 3-разбиением
        quickSort3Way(segments, 0, n-1);

        // Для каждой точки ищем подходящие отрезки
        for (int i = 0; i < m; i++) {
            int point = points[i];
            // Используем бинарный поиск для нахождения первого отрезка, который может содержать точку
            int firstIndex = binarySearchFirstSegment(segments, point);

            // Если нашли подходящий отрезок
            if (firstIndex != -1) {
                // Считаем все отрезки, содержащие точку, начиная с найденного индекса
                for (int j = firstIndex; j < n && segments[j].start <= point; j++) {
                    if (segments[j].start <= point && point <= segments[j].stop) {
                        result[i]++;
                    }
                }
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    // Оптимизированная быстрая сортировка с 3-разбиением и элиминацией хвостовой рекурсии
    private void quickSort3Way(Segment[] arr, int low, int high) {
        // Элиминация хвостовой рекурсии: используем цикл вместо рекурсии для большей части
        while (low < high) {
            // 3-разбиение: разделяем массив на три части - <, =, > опорного элемента
            int[] pivotIndices = partition3Way(arr, low, high);

            // Рекурсивно сортируем меньшую часть, а большую обрабатываем в цикле
            if (pivotIndices[0] - low < high - pivotIndices[1]) {
                quickSort3Way(arr, low, pivotIndices[0] - 1);
                low = pivotIndices[1] + 1; // Переходим к большей части
            } else {
                quickSort3Way(arr, pivotIndices[1] + 1, high);
                high = pivotIndices[0] - 1; // Переходим к меньшей части
            }
        }
    }

    // Метод для 3-разбиения массива отрезков
    private int[] partition3Way(Segment[] arr, int low, int high) {
        // Выбираем опорный элемент (середина массива)
        Segment pivot = arr[low + (high - low) / 2];
        int i = low;      // Указатель на конец элементов < pivot
        int j = high;     // Указатель на начало элементов > pivot
        int k = low;      // Текущий элемент для сравнения

        while (k <= j) {
            int cmp = arr[k].compareTo(pivot); // Исправлено: используем compareTo вместо compareSegments
            if (cmp < 0) {
                // Элемент меньше опорного - перемещаем в левую часть
                swap(arr, i, k);
                i++;
                k++;
            } else if (cmp > 0) {
                // Элемент больше опорного - перемещаем в правую часть
                swap(arr, k, j);
                j--;
            } else {
                // Элемент равен опорному - оставляем в средней части
                k++;
            }
        }

        // Возвращаем индексы границ элементов, равных опорному
        return new int[]{i, j};
    }

    // Вспомогательный метод для обмена элементов массива
    private void swap(Segment[] arr, int i, int j) {
        Segment temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Бинарный поиск первого отрезка, который может содержать точку
    private int binarySearchFirstSegment(Segment[] segments, int point) {
        int left = 0;
        int right = segments.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (segments[mid].start <= point) {
                // Этот отрезок начинается не позже точки, может содержать ее
                if (segments[mid].stop >= point) {
                    result = mid; // Запоминаем найденный отрезок
                }
                // Продолжаем искать в правой части для более поздних отрезков
                left = mid + 1;
            } else {
                // Отрезок начинается после точки - ищем в левой части
                right = mid - 1;
            }
        }

        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}