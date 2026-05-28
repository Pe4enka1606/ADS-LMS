package by.it.group451051.belousova.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        Scanner scanner = new Scanner(file);

        // Считываем количество различных букв и длину закодированной строки
        int count = scanner.nextInt();
        int length = scanner.nextInt();
        scanner.nextLine(); // Переходим на следующую строку

        // Создаем карту для хранения кодов символов
        Map<String, Character> codeToChar = new HashMap<>();

        // Считываем коды символов
        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine().trim();
            // Разделяем строку на символ и его код
            String[] parts = line.split(": ");
            char character = parts[0].charAt(0);
            String code = parts[1];
            codeToChar.put(code, character);
        }

        // Считываем закодированную строку
        String encodedString = scanner.nextLine();

        // Декодируем строку
        StringBuilder currentCode = new StringBuilder();
        for (int i = 0; i < encodedString.length(); i++) {
            currentCode.append(encodedString.charAt(i));
            // Проверяем, есть ли такой код в карте
            if (codeToChar.containsKey(currentCode.toString())) {
                // Добавляем соответствующий символ к результату
                result.append(codeToChar.get(currentCode.toString()));
                // Сбрасываем текущий код для следующего символа
                currentCode.setLength(0);
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