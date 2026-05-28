package by.it.group451051.sinenko.lesson05;

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
        public int compareTo(Segment o) {
            // Сравниваем сначала по началу, потом по концу
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            }
            return Integer.compare(this.stop, o.stop);
        }
    }

    // Оптимизированная быстрая сортировка с трехчастным разбиением
    private void quickSort3Way(Segment[] arr, int low, int high) {
        while (low < high) {
            if (high - low < 16) {
                insertionSort(arr, low, high);
                return;
            }
            
            // Выбор медианы трех
            int mid = low + (high - low) / 2;
            if (arr[mid].compareTo(arr[low]) < 0) swap(arr, low, mid);
            if (arr[high].compareTo(arr[low]) < 0) swap(arr, low, high);
            if (arr[high].compareTo(arr[mid]) < 0) swap(arr, mid, high);
            
            swap(arr, mid, high - 1);
            Segment pivot = arr[high - 1];
            
            // Трехчастное разбиение
            int i = low;
            int j = high - 1;
            int p = low;
            int q = high - 1;
            
            while (true) {
                while (arr[++i].compareTo(pivot) < 0);
                while (arr[--j].compareTo(pivot) > 0 && j > low);
                
                if (i >= j) break;
                
                swap(arr, i, j);
                if (arr[i].compareTo(pivot) == 0) swap(arr, i, p++);
                if (arr[j].compareTo(pivot) == 0) swap(arr, j, q--);
            }
            
            swap(arr, i, high - 1);
            
            int left = i - 1;
            int right = i + 1;
            
            for (int k = low; k < p; k++) {
                swap(arr, k, left--);
            }
            for (int k = high - 1; k > q; k--) {
                swap(arr, k, right++);
            }
            
            // Элиминация хвостовой рекурсии - обрабатываем меньшую часть рекурсивно,
            // большую часть обрабатываем в цикле
            if (i - low < high - i) {
                quickSort3Way(arr, low, i - 1);
                low = i + 1;
            } else {
                quickSort3Way(arr, i + 1, high);
                high = i - 1;
            }
        }
    }
    
    // Вспомогательная сортировка вставками для небольших подмассивов
    private void insertionSort(Segment[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Segment key = arr[i];
            int j = i - 1;
            while (j >= low && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    // Вспомогательный метод для обмена элементов
    private void swap(Segment[] arr, int i, int j) {
        Segment temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    // Бинарный поиск первого отрезка, который начинается не позже точки
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

        // 1. Сортируем отрезки с помощью быстрой сортировки
        if (n > 0) {
            quickSort3Way(segments, 0, n - 1);
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
