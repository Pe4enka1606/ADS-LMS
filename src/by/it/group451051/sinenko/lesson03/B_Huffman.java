package by.it.group451051.sinenko.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

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
        Integer count = scanner.nextInt();
        Integer length = scanner.nextInt();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение

        // пропускаем перевод строки после чисел
        scanner.nextLine();

        // создаем HashMap для хранения кодов символов
        Map<String, Character> codeToChar = new HashMap<>();

        // читаем количество k строк с кодами символов
        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine();         // собственно читаем строку
            String[] parts = line.split(": "); // разбиваем строку и получаем массив из двух элементов
            char symbol = parts[0].charAt(0);  // извлекаем символ из первой части строки    
            String code = parts[1];                   // извлекаем код символа из второй части   

            // сохраняем в HashMap
            codeToChar.put(code, symbol);
        }

        // читаем закодированную строку
        String encodedString = scanner.nextLine();

        // декодирование строки
        StringBuilder currentCode = new StringBuilder();

        for (int i = 0; i < encodedString.length(); i++) {
            currentCode.append(encodedString.charAt(i));   // добавляем очередной бит из закодированной строки к текущему коду
            String code = currentCode.toString();         // преобразуем накопленные символы в строку-код
            if (codeToChar.containsKey(code)) {           // проверка есть ли такой код в HashMap
                result.append(codeToChar.get(code));      // если есть, то добавляем соответствующий символ к результату
                currentCode.setLength(0);       //сброс текущего код для поиска следующего символа
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        return result.toString(); //01001100100111
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group451051/sinenko/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }


}
