package by.it.group451051.pekarskij.lesson05;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class Lesson5Test {

    @Test
    public void A() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result = instance.getAccessory(stream);
        boolean ok = Arrays.equals(result, new int[]{1, 0, 0});
        assertTrue("A failed", ok);

        // добавленные тесты

        // 1) один отрезок, одна точка внутри
        java.io.File f1 = java.io.File.createTempFile("qa1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "1 1\n0 10\n5".getBytes());
        int[] r1 = instance.getAccessory(new FileInputStream(f1));
        assertTrue("A single segment failed", Arrays.equals(r1, new int[]{1}));

        // 2) несколько отрезков, точка на границе
        java.io.File f2 = java.io.File.createTempFile("qa2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "2 1\n0 5\n5 10\n5".getBytes());
        int[] r2 = instance.getAccessory(new FileInputStream(f2));
        assertTrue("A boundary failed", Arrays.equals(r2, new int[]{2}));

        // 3) точка вне всех отрезков
        java.io.File f3 = java.io.File.createTempFile("qa3", ".txt");
        java.nio.file.Files.write(f3.toPath(), "2 1\n0 5\n10 20\n7".getBytes());
        int[] r3 = instance.getAccessory(new FileInputStream(f3));
        assertTrue("A no segments failed", Arrays.equals(r3, new int[]{0}));
    }


    @Test
    public void B() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        boolean ok = Arrays.equals(result, new int[]{2, 2, 3, 9, 9});
        assertTrue("B failed", ok);

        // добавленные тесты

        // 1) один элемент
        java.io.File f1 = java.io.File.createTempFile("cb1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "1\n7".getBytes());
        int[] r1 = instance.countSort(new FileInputStream(f1));
        assertTrue("B single element failed", Arrays.equals(r1, new int[]{7}));

        // 2) все одинаковые
        java.io.File f2 = java.io.File.createTempFile("cb2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "5\n3 3 3 3 3".getBytes());
        int[] r2 = instance.countSort(new FileInputStream(f2));
        assertTrue("B all equal failed", Arrays.equals(r2, new int[]{3, 3, 3, 3, 3}));

        // 3) полный диапазон 0..10
        java.io.File f3 = java.io.File.createTempFile("cb3", ".txt");
        java.nio.file.Files.write(f3.toPath(), "11\n10 9 8 7 6 5 4 3 2 1 0".getBytes());
        int[] r3 = instance.countSort(new FileInputStream(f3));
        assertTrue("B full range failed", Arrays.equals(r3, new int[]{0,1,2,3,4,5,6,7,8,9,10}));
    }


    @Test
    public void C() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        boolean ok = Arrays.equals(result, new int[]{1, 0, 0});
        assertTrue("C failed", ok);

        // добавленные тесты

        // 1) один отрезок, точка внутри
        java.io.File f1 = java.io.File.createTempFile("qc1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "1 1\n0 10\n5".getBytes());
        int[] r1 = instance.getAccessory2(new FileInputStream(f1));
        assertTrue("C single segment failed", Arrays.equals(r1, new int[]{1}));

        // 2) точка на границе двух отрезков
        java.io.File f2 = java.io.File.createTempFile("qc2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "2 1\n0 5\n5 10\n5".getBytes());
        int[] r2 = instance.getAccessory2(new FileInputStream(f2));
        assertTrue("C boundary failed", Arrays.equals(r2, new int[]{2}));

        // 3) точка вне всех отрезков
        java.io.File f3 = java.io.File.createTempFile("qc3", ".txt");
        java.nio.file.Files.write(f3.toPath(), "2 1\n0 5\n10 20\n7".getBytes());
        int[] r3 = instance.getAccessory2(new FileInputStream(f3));
        assertTrue("C no segments failed", Arrays.equals(r3, new int[]{0}));
    }
}
