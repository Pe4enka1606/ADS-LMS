package by.it.group451051.zhelubovskaya.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Реализуйте сортировку слиянием для одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо отсортировать полученный массив.

Sample Input:
5
2 3 9 2 9
Sample Output:
2 2 3 9 9
*/
public class B_MergeSort {

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a=new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        // Вызываем рекурсивную сортировку слиянием
        mergeSortAlgorithm(a, 0, n - 1);
        return a;
    }
// Рекурсивный метод сортировки слиянием
private void mergeSortAlgorithm(int[] array, int startIndex, int endIndex) {
    // Базовый случай: если подмассив содержит 1 элемент или меньше
    if (startIndex >= endIndex) {
        return;
    }
    
    // Находим середину массива
    int middleIndex = startIndex + (endIndex - startIndex) / 2;
    
    // Рекурсивно сортируем левую и правую половины
    mergeSortAlgorithm(array, startIndex, middleIndex);
    mergeSortAlgorithm(array, middleIndex + 1, endIndex);
    
    // Сливаем отсортированные половины
    mergeHalves(array, startIndex, middleIndex, endIndex);
}

// Метод для слияния двух отсортированных половин массива
private void mergeHalves(int[] array, int leftStart, int middle, int rightEnd) {
    // Создаем временный массив для хранения слитых элементов
    int[] temporaryArray = new int[rightEnd - leftStart + 1];
    
    int leftIndex = leftStart; // Указатель на начало левой половины
    int rightIndex = middle + 1; // Указатель на начало правой половины
    int tempIndex = 0; // Указатель для временного массива
    
    // Сливаем элементы из обеих половин в временный массив
    while (leftIndex <= middle && rightIndex <= rightEnd) {
        if (array[leftIndex] <= array[rightIndex]) {
            temporaryArray[tempIndex] = array[leftIndex];
            leftIndex++;
        } else {
            temporaryArray[tempIndex] = array[rightIndex];
            rightIndex++;
        }
        tempIndex++;
    }
    
    // Копируем оставшиеся элементы из левой половины (если есть)
    while (leftIndex <= middle) {
        temporaryArray[tempIndex] = array[leftIndex];
        leftIndex++;
        tempIndex++;
    }
    
    // Копируем оставшиеся элементы из правой половины (если есть)
    while (rightIndex <= rightEnd) {
        temporaryArray[tempIndex] = array[rightIndex];
        rightIndex++;
        tempIndex++;
    }
    
    // Копируем отсортированные элементы из временного массива обратно в исходный
    for (int i = 0; i < temporaryArray.length; i++) {
        array[leftStart + i] = temporaryArray[i];
    }
}  
//!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!  
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
