package by.it.group451051.zhelubovskaya.lesson05;

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
    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment other) {
            //подумайте, что должен возвращать компаратор отрезков
            // Сравниваем сначала по началу отрезка
            if (this.start != other.start) {
                return Integer.compare(this.start, other.start);
            }
            // Если начала равны, сравниваем по концу
            return Integer.compare(this.stop, other.stop);
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
        for (int i = 0; i < m; i++) {
            points[i]=scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением быстрой сортировки
        //в классе отрезка Segment реализуйте нужный для этой задачи компаратор
           
        // 1. Сортируем отрезки с помощью быстрой сортировки с 3-разбиением
        if (n > 0) {
            quickSort3(segments, 0, n - 1);
        }
        
        // 2. Для каждой точки ищем подходящие отрезки
        for (int i = 0; i < m; i++) {
            int point = points[i];
            int count = 0;

            // Используем бинарный поиск для нахождения первого возможного отрезка
            int firstIndex = binarySearchFirst(segments, point);

            // Если нашли хотя бы один отрезок с началом не позже точки
            if (firstIndex != -1) {
                // Проверяем все отрезки от найденного в обратном порядке
                for (int j = firstIndex; j >= 0; j--) {
                    if (segments[j].start <= point && point <= segments[j].stop) {
                        count++;
                    }
                }

                // Проверяем отрезки после найденного (их может быть немного)
                for (int j = firstIndex + 1; j < n; j++) {
                    if (segments[j].start > point) {
                        break; // Дальше отрезки начинаются позже точки
                    }
                    if (segments[j].start <= point && point <= segments[j].stop) {
                        count++;
                    }
                }
            }

            result[i] = count;
        }

        scanner.close();

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    // Метод быстрой сортировки с 3-разбиением
    private void quickSort3(Segment[] segments, int left, int right) {
        // Элиминация хвостовой рекурсии
        while (left < right) {
            Segment pivot = segments[left + (right - left) / 2];
            
            // Трехстороннее разбиение
            int i = left;      // конец секции элементов меньше опорного
            int j = left;      // текущий элемент
            int k = right;     // начало секции элементов больше опорного
            
            while (j <= k) {
                int cmp = segments[j].compareTo(pivot);
                if (cmp < 0) {
                    // Элемент меньше опорного
                    swap(segments, i, j);
                    i++;
                    j++;
                } else if (cmp > 0) {
                    // Элемент больше опорного
                    swap(segments, j, k);
                    k--;
                } else {
                    // Элемент равен опорному
                    j++;
                }
            }

            // Рекурсивная сортировка меньшей части для элиминации хвостовой рекурсии
            if (i - left < right - k) {
                quickSort3(segments, left, i - 1);
                left = k + 1;  // продолжаем с правой части
            } else {
                quickSort3(segments, k + 1, right);
                right = i - 1;  // продолжаем с левой части
            }
        }
    }

    // Метод для обмена элементов в массиве
    private void swap(Segment[] arr, int i, int j) {
        Segment temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Бинарный поиск для нахождения последнего отрезка с началом <= point
    private int binarySearchFirst(Segment[] segments, int point) {
        int left = 0;
        int right = segments.length - 1;
        int result = -1;  // если не найдем, вернем -1
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (segments[mid].start <= point) {
                // Нашли отрезок, начинающийся не позже точки
                result = mid;      // запоминаем индекс
                left = mid + 1;    // продолжаем искать справа (большие индексы)
            } else {
                // Отрезок начинается позже точки
                right = mid - 1;   // ищем слева
            }
        }
        
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/zhelubovskaya/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}