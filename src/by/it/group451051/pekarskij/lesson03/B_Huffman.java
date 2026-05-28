package by.it.group451051.pekarskij.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
    StringBuilder result = new StringBuilder();
    Scanner scanner = new Scanner(file);

    // читаем количество символов и длину закодированной строки
    int count = scanner.nextInt();
    int length = scanner.nextInt();

    // читаем таблицу кодов
    Map<String, Character> table = new HashMap<>();
    for (int i = 0; i < count; i++) {
        String letter = scanner.next();
        String code = scanner.next();
        char c = letter.charAt(0);
        String realCode = code.substring(0); // "0" или "10" и т.д.
        table.put(realCode, c);
    }

    // читаем закодированную строку
    String encoded = scanner.next();

    // декодируем посимвольно
    StringBuilder current = new StringBuilder();
    for (char bit : encoded.toCharArray()) {
        current.append(bit);
        if (table.containsKey(current.toString())) {
            result.append(table.get(current.toString()));
            current.setLength(0);
        }
    }

    return result.toString();
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group451051/pekarskij/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }

}
