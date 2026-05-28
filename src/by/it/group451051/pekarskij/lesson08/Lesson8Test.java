package by.it.group451051.pekarskij.lesson08;

import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class Lesson8Test {

    @Test
    public void A() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson08/dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        assertEquals("A failed", res, 14);

        // добавленные тесты
        File f1 = File.createTempFile("knapA1", ".txt");
        Files.write(f1.toPath(), "10 3\n1 4 8".getBytes());
        assertEquals("A1 failed", instance.getMaxWeight(new FileInputStream(f1)), 10);

        File f2 = File.createTempFile("knapA2", ".txt");
        Files.write(f2.toPath(), "15 3\n2 8 16".getBytes());
        assertEquals("A2 failed", instance.getMaxWeight(new FileInputStream(f2)), 14);

        File f3 = File.createTempFile("knapA3", ".txt");
        Files.write(f3.toPath(), "7 2\n3 5".getBytes());
        assertEquals("A3 failed", instance.getMaxWeight(new FileInputStream(f3)), 6);
    }

    @Test
    public void B() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res = instance.getMaxWeight(stream);
        assertEquals("B failed", res, 9);

        // добавленные тесты
        File f1 = File.createTempFile("knapB1", ".txt");
        Files.write(f1.toPath(), "10 3\n1 4 8".getBytes());
        assertEquals("B1 failed", instance.getMaxWeight(new FileInputStream(f1)), 9);

        File f2 = File.createTempFile("knapB2", ".txt");
        Files.write(f2.toPath(), "15 3\n2 8 16".getBytes());
        assertEquals("B2 failed", instance.getMaxWeight(new FileInputStream(f2)), 10);

        File f3 = File.createTempFile("knapB3", ".txt");
        Files.write(f3.toPath(), "7 2\n3 5".getBytes());
        assertEquals("B3 failed", instance.getMaxWeight(new FileInputStream(f3)), 5);
    }

    @Test
    public void C() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson08/dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res = instance.getMaxSum(stream);
        assertEquals("C failed", res, 3);

        // добавленные тесты
        File f1 = File.createTempFile("stairs1", ".txt");
        Files.write(f1.toPath(), "2\n2 -1".getBytes());
        assertEquals("C1 failed", instance.getMaxSum(new FileInputStream(f1)), 1);

        File f2 = File.createTempFile("stairs2", ".txt");
        Files.write(f2.toPath(), "3\n-1 2 1".getBytes());
        assertEquals("C2 failed", instance.getMaxSum(new FileInputStream(f2)), 3);

        File f3 = File.createTempFile("stairs3", ".txt");
        Files.write(f3.toPath(), "4\n1 -5 10 -1".getBytes());
        assertEquals("C3 failed", instance.getMaxSum(new FileInputStream(f3)), 10);
    }
}
