package by.it.group451051.markhel.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        Scanner scanner = new Scanner(file);

        int count = scanner.nextInt();
        int length = scanner.nextInt();
        scanner.nextLine(); // перейти на следующую строку

        // Создаем массив для хранения кодов символов
        String[] codes = new String[256]; // для латинских букв достаточно

        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(": ");
            char symbol = parts[0].charAt(0);
            String code = parts[1];
            codes[symbol] = code;
        }

        String encoded = scanner.next();

        // Декодирование
        StringBuilder currentCode = new StringBuilder();
        for (int i = 0; i < encoded.length(); i++) {
            currentCode.append(encoded.charAt(i));

            // Проверяем, соответствует ли текущий код какому-либо символу
            for (char c = 'a'; c <= 'z'; c++) {
                if (codes[c] != null && codes[c].equals(currentCode.toString())) {
                    result.append(c);
                    currentCode.setLength(0); // сбрасываем текущий код
                    break;
                }
            }
        }

        scanner.close();
        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }
}
