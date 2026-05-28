package by.it.group451051.shiman.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Lesson 3. B_Huffman.
// Восстановите строку по её коду и беспрефиксному коду символов.

// В первой строке входного файла заданы два целых числа
// kk и ll через пробел — количество различных букв, встречающихся в строке,
// и размер получившейся закодированной строки, соответственно.
//
// В следующих kk строках записаны коды букв в формате "letter: code".
// Ни один код не является префиксом другого.
// Буквы могут быть перечислены в любом порядке.
// В качестве букв могут встречаться лишь строчные буквы латинского алфавита;
// каждая из этих букв встречается в строке хотя бы один раз.
// Наконец, в последней строке записана закодированная строка.
// Исходная строка и коды всех букв непусты.
// Заданный код таков, что закодированная строка имеет минимальный возможный размер.
//
//        Sample Input 1:
//        1 1
//        a: 0
//        0

//        Sample Output 1:
//        a


//        Sample Input 2:
//        4 14
//        a: 0
//        b: 10
//        c: 110
//        d: 111
//        01001100100111

//        Sample Output 2:
//        abacabad

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result=new StringBuilder();
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(file);
        Integer count = scanner.nextInt(); // Количество различных букв
        Integer length = scanner.nextInt(); // Размер закодированной строки
        scanner.nextLine(); // Переходим на следующую строку после чисел

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        // Создаем карту для хранения кодов символов
        Map<String, Character> codeMap = new HashMap<>();

        // Считываем k строк с кодами символов
        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine(); // Читаем строку формата "буква: код"
            String[] parts = line.split(": "); // Разделяем строку на букву и код
            char symbol = parts[0].charAt(0); // Извлекаем символ
            String code = parts[1]; // Извлекаем код символа
            codeMap.put(code, symbol); // Сохраняем соответствие кода и символа
        }

        // Считываем закодированную строку
        String encodedString = scanner.nextLine();

        // Декодируем строку
        StringBuilder currentCode = new StringBuilder(); // Текущий накапливаемый код
        for (int i = 0; i < encodedString.length(); i++) {
            currentCode.append(encodedString.charAt(i)); // Добавляем очередной символ к текущему коду
            String code = currentCode.toString();

            // Если текущий код соответствует какому-либо символу в карте
            if (codeMap.containsKey(code)) {
                // Добавляем найденный символ к результату
                result.append(codeMap.get(code));
                // Сбрасываем текущий код для поиска следующего символа
                currentCode.setLength(0);
            }
            // Если не нашли символ, продолжаем накапливать код (коды беспрефиксные, так что это безопасно)
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        return result.toString(); // Возвращаем декодированную строку
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }


}