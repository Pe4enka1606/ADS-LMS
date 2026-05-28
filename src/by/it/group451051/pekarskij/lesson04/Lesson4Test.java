package by.it.group451051.pekarskij.lesson04;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class Lesson4Test {

    @Test
    public void A() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson04/dataA.txt");
        A_BinaryFind instance = new A_BinaryFind();
        int[] result = instance.findIndex(stream);

        StringBuilder sb = new StringBuilder();
        for (int index : result) sb.append(index).append(" ");
        boolean ok = sb.toString().trim().equals("3 1 -1 1 -1");
        assertTrue("A failed", ok);

        // добавленные тесты

        // 1) поиск в массиве из одного элемента
        java.io.File f1 = java.io.File.createTempFile("bin1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "1\n5\n1 5".getBytes());
        int[] r1 = instance.findIndex(new FileInputStream(f1));
        boolean ok2 = r1[0] == 1;
        assertTrue("A single element failed", ok2);

        // 2) поиск отсутствующих элементов
        java.io.File f2 = java.io.File.createTempFile("bin2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "3\n1 10 20\n3 5 7 100".getBytes());
        int[] r2 = instance.findIndex(new FileInputStream(f2));
        boolean ok3 = Arrays.equals(r2, new int[]{-1, -1, -1});
        assertTrue("A missing elements failed", ok3);

        // 3) поиск всех элементов в большом массиве
        StringBuilder sb3 = new StringBuilder("5\n2 4 6 8 10\n5 2 4 6 8 10");
        java.io.File f3 = java.io.File.createTempFile("bin3", ".txt");
        java.nio.file.Files.write(f3.toPath(), sb3.toString().getBytes());
        int[] r3 = instance.findIndex(new FileInputStream(f3));
        boolean ok4 = Arrays.equals(r3, new int[]{1, 2, 3, 4, 5});
        assertTrue("A full match failed", ok4);
    }


    @Test
    public void B() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        int[] result = instance.getMergeSort(stream);

        boolean ok = result.length > 3;
        int[] test = Arrays.copyOf(result, result.length);
        Arrays.sort(test);
        for (int i = 0; i < result.length; i++) ok = ok && (result[i] == test[i]);
        assertTrue("B failed", ok);

        // добавленные тесты

        // 1) сортировка массива из одного элемента
        java.io.File f1 = java.io.File.createTempFile("merge1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "1\n7".getBytes());
        int[] r1 = instance.getMergeSort(new FileInputStream(f1));
        boolean ok2 = r1[0] == 7;
        assertTrue("B single element failed", ok2);

        // 2) сортировка массива с повторяющимися элементами
        java.io.File f2 = java.io.File.createTempFile("merge2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "5\n9 1 9 1 9".getBytes());
        int[] r2 = instance.getMergeSort(new FileInputStream(f2));
        boolean ok3 = Arrays.equals(r2, new int[]{1, 1, 9, 9, 9});
        assertTrue("B duplicates failed", ok3);

        // 3) сортировка большого массива
        StringBuilder sb3 = new StringBuilder("10\n");
        sb3.append("10 9 8 7 6 5 4 3 2 1");
        java.io.File f3 = java.io.File.createTempFile("merge3", ".txt");
        java.nio.file.Files.write(f3.toPath(), sb3.toString().getBytes());
        int[] r3 = instance.getMergeSort(new FileInputStream(f3));
        boolean ok4 = Arrays.equals(r3, new int[]{1,2,3,4,5,6,7,8,9,10});
        assertTrue("B reverse order failed", ok4);
    }


    @Test
    public void C() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        boolean ok = (2 == result);
        assertTrue("C failed", ok);

        // добавленные тесты

        // 1) массив без инверсий
        java.io.File f1 = java.io.File.createTempFile("inv1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "5\n1 2 3 4 5".getBytes());
        int r1 = instance.calc(new FileInputStream(f1));
        boolean ok2 = (r1 == 0);
        assertTrue("C no inversions failed", ok2);

        // 2) массив полностью в обратном порядке
        java.io.File f2 = java.io.File.createTempFile("inv2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "5\n5 4 3 2 1".getBytes());
        int r2 = instance.calc(new FileInputStream(f2));
        boolean ok3 = (r2 == 10); // 5*4/2
        assertTrue("C full reverse failed", ok3);

        // 3) случайный массив
        java.io.File f3 = java.io.File.createTempFile("inv3", ".txt");
        java.nio.file.Files.write(f3.toPath(), "6\n3 1 2 5 4 6".getBytes());
        int r3 = instance.calc(new FileInputStream(f3));
        boolean ok4 = (r3 == 3); // пары: (3,1), (3,2), (5,4)
        assertTrue("C random failed", ok4);
    }
}
