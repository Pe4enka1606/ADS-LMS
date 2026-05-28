package by.it.group451051.pekarskij.lesson03;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class Lesson3Test {

    @Test
    public void A() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group451051/pekarskij/lesson03/dataHuffman.txt");
        A_Huffman instance = new A_Huffman();
        String result = instance.encode(f);
        boolean ok=result.equals("01001100100111");
        assertTrue("A failed", ok);

        // добавленные тесты

        File f1 = File.createTempFile("huff1", ".txt");
        java.nio.file.Files.write(f1.toPath(), "a".getBytes());
        String r1 = instance.encode(f1);
        boolean ok2 = r1.equals("0");
        assertTrue("A single letter failed", ok2);

        File f2 = File.createTempFile("huff2", ".txt");
        java.nio.file.Files.write(f2.toPath(), "zzzzzz".getBytes());
        String r2 = instance.encode(f2);
        boolean ok3 = r2.equals("000000");
        assertTrue("A repeated letter failed", ok3);

        File f3 = File.createTempFile("huff3", ".txt");
        java.nio.file.Files.write(f3.toPath(), "abcabcabcabc".getBytes());
        String r3 = instance.encode(f3);
        boolean ok4 = r3.length() > 0;
        assertTrue("A medium string failed", ok4);
    }

    @Test
    public void B() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group451051/pekarskij/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        boolean ok=result.equals("abacabad");
        assertTrue("B failed", ok);

        // добавленные тесты

        File f1 = File.createTempFile("dec1", ".txt");
        java.nio.file.Files.write(f1.toPath(),
                ("1 6\na: 0\n000000").getBytes());
        String r1 = instance.decode(f1);
        boolean ok2 = r1.equals("aaaaaa");
        assertTrue("B single code failed", ok2);

        File f2 = File.createTempFile("dec2", ".txt");
        java.nio.file.Files.write(f2.toPath(),
                ("2 6\na: 0\nb: 1\n010101").getBytes());
        String r2 = instance.decode(f2);
        boolean ok3 = r2.equals("ababab");
        assertTrue("B two letters failed", ok3);

        File f3 = File.createTempFile("dec3", ".txt");
        java.nio.file.Files.write(f3.toPath(),
                ("3 9\na: 0\nb: 10\nc: 11\n011011011").getBytes());
        String r3 = instance.decode(f3);
        boolean ok4 = r3.equals("acacac");
        assertTrue("B three letters failed", ok4);
    }

    @Test
    public void C() throws Exception {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        Long res=instance.findMaxValue(stream);
        boolean ok=(res==500);
        assertTrue("C failed", ok);

        // добавленные тесты

        File f1 = File.createTempFile("heap1", ".txt");
        java.nio.file.Files.write(f1.toPath(),
                ("3\nInsert 1\nInsert 2\nExtractMax").getBytes());
        InputStream s1 = new FileInputStream(f1);
        Long r1 = instance.findMaxValue(s1);
        boolean ok2 = (r1 == 2);
        assertTrue("C simple heap failed", ok2);

        File f2 = File.createTempFile("heap2", ".txt");
        java.nio.file.Files.write(f2.toPath(),
                ("5\nInsert 10\nInsert 100\nInsert 50\nExtractMax\nExtractMax").getBytes());
        InputStream s2 = new FileInputStream(f2);
        Long r2 = instance.findMaxValue(s2);
        boolean ok3 = (r2 == 100);
        assertTrue("C multiple extracts failed", ok3);

        File f3 = File.createTempFile("heap3", ".txt");
        StringBuilder sb = new StringBuilder("1001\n");
        for (int i = 1; i <= 1000; i++) sb.append("Insert ").append(i).append("\n");
        sb.append("ExtractMax\n");
        java.nio.file.Files.write(f3.toPath(), sb.toString().getBytes());
        InputStream s3 = new FileInputStream(f3);
        Long r3 = instance.findMaxValue(s3);
        boolean ok4 = (r3 == 1000);
        assertTrue("C large heap failed", ok4);
    }
}
