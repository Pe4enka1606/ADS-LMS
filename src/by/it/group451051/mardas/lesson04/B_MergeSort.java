package by.it.group451051.mardas.lesson04;

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

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием
        class Sort {
            static void delenie(int[] a,int left,int right) {
                if (left<right) {
                    int mid=(left+right)/2;
                    delenie(a,left,mid);
                    delenie(a,mid+1,right);
                    sort(a,left,mid,right);
                }
            }
            static void sort(int[] a,int left,int mid,int right) {
                int size1=mid-left+1;
                int size2=right-mid;
                int[] mas_l=new int[size1];
                int[] mas_r=new int[size2];
                System.arraycopy(a,left,mas_l,0,size1);
                System.arraycopy(a,mid+1,mas_r,0,size2);
                int i=0,j=0;
                int k=left;
                while (i<size1&&j<size2) {
                    if (mas_l[i]<=mas_r[j]) {
                        a[k]=mas_l[i];
                        i++;
                    } else {
                        a[k]=mas_r[j];
                        j++;
                    }
                    k++;
                }
                while (i<size1) {
                    a[k]=mas_l[i];
                    i++;
                    k++;
                }
                while (j<size2) {
                    a[k]=mas_r[j];
                    j++;
                    k++;
                }
            }
        }
//for (int i=0;i<a.length;i++) System.out.println("==="+a[i]);
        Sort.delenie(a,0,n-1);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }
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
