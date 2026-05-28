package by.it.group451051.sinenko.lesson04;

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

        // вызов сортировки слиянием
        mergeSort(a, 0, a.length - 1);

        scanner.close();
        return a;
    }
        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием

        // сортировка слиянием
        private void mergeSort(int[] arr, int left, int right) {
            if (left < right) {
                int mid = left + (right - left) / 2; // находим середину

                mergeSort(arr, left, mid);       // сортировка левой половины
                mergeSort(arr, mid + 1, right);  // сортировка правой
                merge(arr, left, mid, right);    // слияние отсортированных половинок
            } 
        }

        // cлияние двух отсортированных частей массива
        private void merge(int[] arr, int left, int mid, int right) {
            int[] temp = new int[right - left + 1]; // временный массив для слияния

            int l = left;      // индекс для левой части
            int r = mid + 1;   // для правой
            int k = 0;         // для временного массива

            while (l <= mid && r <= right) {
                if (arr[l] <= arr[r]) {
                    temp[k] = arr[l];
                    l++;
                } else {
                    temp[k] = arr[r];
                    r++;
                }
                k++;
            }

            // дописываем оставшиеся элементы из левой части
            while (l <= mid) {
                temp[k] = arr[l];
                l++;
                k++;
            }

            // из правой части
            while (r <= right) {
                temp[k] = arr[r];
                r++;
                k++;
            }

            // копируем отсортированные элементы в исходный массив
            for (int m = 0; m < temp.length; m++) {
                arr[left + m] = temp[m];
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



