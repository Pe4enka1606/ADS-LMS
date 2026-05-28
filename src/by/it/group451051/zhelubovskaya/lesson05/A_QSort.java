package by.it.group451051.zhelubovskaya.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/*
Видеорегистраторы и площадь.
На площади установлена одна или несколько камер.
Известны данные о том, когда каждая из них включалась и выключалась (отрезки работы)
Известен список событий на площади (время начала каждого события).
Вам необходимо определить для каждого события сколько камер его записали.

В первой строке задано два целых числа:
    число включений камер (отрезки) 1<=n<=50000
    число событий (точки) 1<=m<=50000.

Следующие n строк содержат по два целых числа ai и bi (ai<=bi) -
координаты концов отрезков (время работы одной какой-то камеры).
Последняя строка содержит m целых чисел - координаты точек.
Все координаты не превышают 10E8 по модулю (!).

Точка считается принадлежащей отрезку, если она находится внутри него или на границе.

Для каждой точки в порядке их появления во вводе выведите,
скольким отрезкам она принадлежит.
    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/

public class A_QSort {

    //отрезок
    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
            // Обеспечиваем, что start всегда меньше или равен stop
            if (start <= stop) {
                this.start = start;
                this.stop = stop;
            } else {
                this.start = stop;
                this.stop = start;
            }
        }

        @Override
        public int compareTo(Segment o) {
            //подумайте, что должен возвращать компаратор отрезков
            // Сравниваем по началу отрезка
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            }
            // Если начала равны, сравниваем по концу
            return Integer.compare(this.stop, o.stop);
            
        }
    }


    int[] getAccessory(InputStream stream) throws FileNotFoundException {
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
       // Создаем отдельные массивы для начал и концов отрезков
        int[] startPoints = new int[n];
        int[] endPoints = new int[n];
        
        // Заполняем массивы
        for (int i = 0; i < n; i++) {
            startPoints[i] = segments[i].start;
            endPoints[i] = segments[i].stop;
        }
        
        // Сортируем массивы
        Arrays.sort(startPoints);
        Arrays.sort(endPoints);
        
        // Для каждой точки считаем количество активных камер
        for (int i = 0; i < m; i++) {
            int currentTime = points[i];
            
            // Количество отрезков, которые начались до или в момент currentTime
            int segmentsStarted = countSegmentsStartedBefore(startPoints, currentTime);
            
            // Количество отрезков, которые закончились до currentTime
            int segmentsEnded = countSegmentsEndedBefore(endPoints, currentTime);
            
            // Активные камеры = начавшиеся - закончившиеся
            result[i] = segmentsStarted - segmentsEnded;
        }
        
        scanner.close();
        return result;
    }
    // Метод для подсчета количества отрезков, которые начались до или в заданное время
    private int countSegmentsStartedBefore(int[] starts, int time) {
        int left = 0;
        int right = starts.length;
        
        while (left < right) {
            int middle = left + (right - left) / 2;
            
            if (starts[middle] <= time) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        
        return left;
    }
    
    // Метод для подсчета количества отрезков, которые закончились до заданного времени
    private int countSegmentsEndedBefore(int[] ends, int time) {
        int left = 0;
        int right = ends.length;
        
        while (left < right) {
            int middle = left + (right - left) / 2;
            
            if (ends[middle] < time) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        
        return left;
    }
//!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/zhelubovskaya/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result=instance.getAccessory(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
