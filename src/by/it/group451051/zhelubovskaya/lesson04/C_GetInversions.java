package by.it.group451051.zhelubovskaya.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Рассчитать число инверсий одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].

    (Такая пара элементов называется инверсией массива.
    Количество инверсий в массиве является в некотором смысле
    его мерой неупорядоченности: например, в упорядоченном по неубыванию
    массиве инверсий нет вообще, а в массиве, упорядоченном по убыванию,
    инверсию образуют каждые (т.е. любые) два элемента.
    )

Sample Input:
5
2 3 9 2 9
Sample Output:
2

Головоломка (т.е. не обязательно).
Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
Докажите рост производительности замерами времени.
Большой тестовый массив можно прочитать свой или сгенерировать его программно.
*/


public class C_GetInversions {

    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int result = 0;
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!
        // Создаем копию массива для использования в сортировке слиянием
        int[] arrayCopy = a.clone();
        // Вычисляем количество инверсий с помощью сортировки слиянием
        result = (int) countInversionsWithMergeSort(arrayCopy, 0, n - 1);

        
        return result;
    }
 // Метод для подсчета инверсий с использованием сортировки слиянием
 private long countInversionsWithMergeSort(int[] array, int leftIndex, int rightIndex) {
    // Базовый случай: если подмассив содержит 0 или 1 элемент
    if (leftIndex >= rightIndex) {
        return 0;
    }
    
    // Находим середину массива
    int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
    
    // Рекурсивно считаем инверсии в левой и правой половинах
    long inversionCount = 0;
    inversionCount += countInversionsWithMergeSort(array, leftIndex, middleIndex);
    inversionCount += countInversionsWithMergeSort(array, middleIndex + 1, rightIndex);
    
    // Считаем инверсии при слиянии половин
    inversionCount += mergeAndCountInversions(array, leftIndex, middleIndex, rightIndex);
    
    return inversionCount;
}

// Метод для слияния двух половин массива с подсчетом инверсий
private long mergeAndCountInversions(int[] array, int leftStart, int middle, int rightEnd) {
    // Создаем временный массив для слияния
    int[] temporaryArray = new int[rightEnd - leftStart + 1];
    
    int leftPointer = leftStart;
    int rightPointer = middle + 1;
    int tempPointer = 0;
    long inversionCount = 0;
    
    // Сливаем две отсортированные половины
    while (leftPointer <= middle && rightPointer <= rightEnd) {
        if (array[leftPointer] <= array[rightPointer]) {
            // Нет инверсии
            temporaryArray[tempPointer] = array[leftPointer];
            leftPointer++;
        } else {
            // Найдена инверсия: все элементы в левой половине от leftPointer до middle
            // образуют инверсии с array[rightPointer]
            temporaryArray[tempPointer] = array[rightPointer];
            rightPointer++;
            inversionCount += (middle - leftPointer + 1);
        }
        tempPointer++;
    }
    
    // Копируем оставшиеся элементы из левой половины
    while (leftPointer <= middle) {
        temporaryArray[tempPointer] = array[leftPointer];
        leftPointer++;
        tempPointer++;
    }
    
    // Копируем оставшиеся элементы из правой половины
    while (rightPointer <= rightEnd) {
        temporaryArray[tempPointer] = array[rightPointer];
        rightPointer++;
        tempPointer++;
    }
    
    // Копируем отсортированные элементы обратно в исходный массив
    System.arraycopy(temporaryArray, 0, array, leftStart, temporaryArray.length);
    
    return inversionCount;
}
//!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}
