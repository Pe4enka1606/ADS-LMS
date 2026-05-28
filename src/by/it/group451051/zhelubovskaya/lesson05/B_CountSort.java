package by.it.group451051.zhelubovskaya.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10.
Выведите упорядоченную по неубыванию последовательность этих чисел.

При сортировке реализуйте метод со сложностью O(n)

Пример: https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/
Вольный перевод: http://programador.ru/sorting-positive-int-linear-time/
*/

public class B_CountSort {


    int[] countSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        int[] points=new int[n];

        //читаем точки
        for (int i = 0; i < n; i++) {
            points[i]=scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением сортировки подсчетом
        // Определяем максимальное значение в массиве (по условию не превышает 10)
        int maxValue = 10;
                
        // Создаем массив для подсчета частот
        int[] frequencyArray = new int[maxValue + 1]; // +1 чтобы индексы от 0 до 10

        // 1. Подсчитываем количество вхождений каждого числа
        for (int i = 0; i < n; i++) {
            int currentValue = points[i];
            // Проверяем, что значение в допустимом диапазоне
            if (currentValue >= 0 && currentValue <= maxValue) {
                frequencyArray[currentValue]++;
            }
        }

        // 2. Восстанавливаем отсортированный массив
        int currentIndex = 0;
        for (int value = 0; value <= maxValue; value++) {
            // Добавляем каждое значение столько раз, сколько оно встречалось
            while (frequencyArray[value] > 0) {
                points[currentIndex] = value;
                currentIndex++;
                frequencyArray[value]--;
            }
        }

        scanner.close();

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return points;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result=instance.countSort(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
