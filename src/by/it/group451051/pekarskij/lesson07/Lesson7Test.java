package by.it.group451051.pekarskij.lesson07;

import org.junit.Test;
import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class Lesson7Test {

    @Test
    public void A() throws Exception {
        A_EditDist instance = new A_EditDist();
        assertEquals("A1 failed", instance.getDistanceEdinting("ab","ab"),0);
        assertEquals("A2 failed", instance.getDistanceEdinting("short","ports"),3);
        assertEquals("A3 failed", instance.getDistanceEdinting("distance","editing"),5);

        // добавленные тесты 

        // 1) один символ
        assertEquals("A4 failed", instance.getDistanceEdinting("a","b"),1);

        // 2) пустые превращения
        assertEquals("A5 failed", instance.getDistanceEdinting("abc",""),3);
        assertEquals("A6 failed", instance.getDistanceEdinting("","abc"),3);

        // 3) частичное совпадение
        assertEquals("A7 failed", instance.getDistanceEdinting("abc","axc"),1);
    }


    @Test
    public void B() throws Exception {
        B_EditDist instance = new B_EditDist();
        assertEquals("B1 failed", instance.getDistanceEdinting("ab","ab"),0);
        assertEquals("B2 failed", instance.getDistanceEdinting("short","ports"),3);
        assertEquals("B3 failed", instance.getDistanceEdinting("distance","editing"),5);

        // добавленные тесты

        // 1) перестановка
        assertEquals("B4 failed", instance.getDistanceEdinting("abc","cba"),2);

        // 2) повторяющиеся символы
        assertEquals("B5 failed", instance.getDistanceEdinting("aaaa","aaa"),1);

        // 3) полностью разные строки
        assertEquals("B6 failed", instance.getDistanceEdinting("abc","xyz"),3);
    }


    @Test
    public void C() throws Exception {
        C_EditDist instance = new C_EditDist();
        assertEquals("C1 failed", instance.getDistanceEdinting("ab","ab"),"#,#,");

        // ожидается 4 копирования
        assertEquals("C2 failed", instance.getDistanceEdinting("short","ports").split("#").length,4);

        // ожидается 5 копирований
        assertEquals("C3 failed", instance.getDistanceEdinting("distance","editing").split("#").length,5);

        // добавленные тесты

        // 1) простая замена
        String r1 = instance.getDistanceEdinting("a","b");
        assertEquals("C4 failed", r1.contains("~b,"), true);

        // 2) вставка
        String r2 = instance.getDistanceEdinting("a","ab");
        assertEquals("C5 failed", r2.contains("+b,"), true);

        // 3) удаление
        String r3 = instance.getDistanceEdinting("ab","a");
        assertEquals("C6 failed", r3.contains("-b,"), true);
    }
}
