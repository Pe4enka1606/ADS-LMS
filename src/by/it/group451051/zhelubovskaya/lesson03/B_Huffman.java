package by.it.group451051.zhelubovskaya.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result=new StringBuilder();
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(file);
        Integer count = scanner.nextInt();
        Integer length = scanner.nextInt();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение
 // Пропускаем перевод строки после чисел
        scanner.nextLine();
        
        // Создаем словарь для хранения соответствия кодов символам
        Map<String, Character> codeMap = new HashMap<>();
        
        // Читаем коды символов
        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine();
            // Разбиваем строку на символ и его код
            String[] parts = line.split(": ");
            char symbol = parts[0].charAt(0);
            String code = parts[1];
            
            // Сохраняем в словаре
            codeMap.put(code, symbol);
        }
        
        // Читаем закодированную строку
        String encodedText = scanner.nextLine();
        
        // Декодируем строку
        StringBuilder currentCodeBuilder = new StringBuilder();
        
        for (int i = 0; i < encodedText.length(); i++) {
            // Добавляем очередной бит к текущему коду
            currentCodeBuilder.append(encodedText.charAt(i));
            
            String currentCode = currentCodeBuilder.toString();
            
            // Проверяем, есть ли такой код в словаре
            if (codeMap.containsKey(currentCode)) {
                // Добавляем соответствующий символ к результату
                result.append(codeMap.get(currentCode));
                // Сбрасываем текущий код для поиска следующего символа
                currentCodeBuilder.setLength(0);
            }
        }



        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        return result.toString(); //01001100100111
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }


}
