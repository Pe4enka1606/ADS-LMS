package by.it.group451051.pekarskij.lesson06;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class Lesson6Test {

    @Test
    public void A() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        boolean ok = (result == 3);
        assertTrue("A failed", ok);

        // добавленные тесты

        // 1) строго возрастающая последовательность
        java.io.File f1 = java.io.File.createTempFile("lis1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "5\n1 2 3 4 5".getBytes());
        int r1 = instance.getSeqSize(new FileInputStream(f1));
        assertTrue("A increasing failed", r1 == 5);

        // 2) строго убывающая последовательность
        java.io.File f2 = java.io.File.createTempFile("lis2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "5\n5 4 3 2 1".getBytes());
        int r2 = instance.getSeqSize(new FileInputStream(f2));
        assertTrue("A decreasing failed", r2 == 1);

        // 3) все элементы одинаковые
        java.io.File f3 = java.io.File.createTempFile("lis3", ".txt");
        java.nio.file.Files.write(f3.toPath(), "5\n7 7 7 7 7".getBytes());
        int r3 = instance.getSeqSize(new FileInputStream(f3));
        assertTrue("A equal failed", r3 == 1);
    }


    @Test
    public void B() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson06/dataB.txt");
        B_LongDivComSubSeq instance = new B_LongDivComSubSeq();
        int result = instance.getDivSeqSize(stream);
        boolean ok = (result == 3);
        assertTrue("B failed", ok);

        // добавленные тесты

        // 1) последовательность, где каждый делится на предыдущий
        java.io.File f1 = java.io.File.createTempFile("div1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "5\n1 2 4 8 16".getBytes());
        int r1 = instance.getDivSeqSize(new FileInputStream(f1));
        assertTrue("B full chain failed", r1 == 5);

        // 2) нет делимости вообще
        java.io.File f2 = java.io.File.createTempFile("div2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "5\n3 5 7 11 13".getBytes());
        int r2 = instance.getDivSeqSize(new FileInputStream(f2));
        assertTrue("B no divisibility failed", r2 == 1);

        // 3) смешанная последовательность
        java.io.File f3 = java.io.File.createTempFile("div3", ".txt");
        java.nio.file.Files.write(f3.toPath(), "6\n3 6 2 4 8 16".getBytes());
        int r3 = instance.getDivSeqSize(new FileInputStream(f3));
        assertTrue("B mixed failed", r3 == 4); // 2 → 4 → 8 → 16
    }


    @Test(timeout = 1000)
    public void C() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        boolean ok = (result == 4);
        assertTrue("C failed", ok);

        // добавленные тесты

        // 1) строго невозрастающая последовательность
        java.io.File f1 = java.io.File.createTempFile("notup1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "5\n9 7 5 3 1".getBytes());
        int r1 = instance.getNotUpSeqSize(new FileInputStream(f1));
        assertTrue("C decreasing failed", r1 == 5);

        // 2) строго возрастающая последовательность
        java.io.File f2 = java.io.File.createTempFile("notup2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "5\n1 2 3 4 5".getBytes());
        int r2 = instance.getNotUpSeqSize(new FileInputStream(f2));
        assertTrue("C increasing failed", r2 == 1);

        // 3) последовательность с повторениями
        java.io.File f3 = java.io.File.createTempFile("notup3", ".txt");
        java.nio.file.Files.write(f3.toPath(), "6\n5 5 4 4 4 3".getBytes());
        int r3 = instance.getNotUpSeqSize(new FileInputStream(f3));
        assertTrue("C repeats failed", r3 == 6);
    }
}
