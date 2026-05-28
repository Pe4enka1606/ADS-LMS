package by.it.group451051.yazhou.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
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

    static class HuffmanNode {
        char symbol;
        HuffmanNode left;  // 0
        HuffmanNode right; // 1

        HuffmanNode(char symbol) {
            this.symbol = symbol;
        }

        HuffmanNode() {
            this.symbol = '\0';
        }
    }

    String decode(File file) throws FileNotFoundException {
        StringBuilder result=new StringBuilder();
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(file);
        Integer count = scanner.nextInt();
        Integer length = scanner.nextInt();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение
        scanner.nextLine(); // переход на новую строку

        // Создаем корень дерева Хаффмана
        HuffmanNode root = new HuffmanNode();

        // Построение дерева Хаффмана на основе кодов символов
        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(": ");
            char symbol = parts[0].charAt(0);
            String code = parts[1];

            // Добавляем код в дерево
            HuffmanNode currentNode = root;
            for (int j = 0; j < code.length(); j++) {
                char bit = code.charAt(j);
                if (bit == '0') {
                    if (currentNode.left == null) {
                        currentNode.left = new HuffmanNode();
                    }
                    currentNode = currentNode.left;
                } else { // bit == '1'
                    if (currentNode.right == null) {
                        currentNode.right = new HuffmanNode();
                    }
                    currentNode = currentNode.right;
                }
            }
            // В листовом узле храним символ
            currentNode.symbol = symbol;
        }

        // Читаем закодированную строку
        String encodedString = scanner.nextLine();

        // Декодируем строку с использованием дерева
        HuffmanNode currentNode = root;
        for (int i = 0; i < encodedString.length(); i++) {
            char bit = encodedString.charAt(i);
            if (bit == '0') {
                currentNode = currentNode.left;
            } else { // bit == '1'
                currentNode = currentNode.right;
            }

            // Если достигли листового узла (в нем есть символ)
            if (currentNode.symbol != '\0') {
                result.append(currentNode.symbol);
                currentNode = root; // возвращаемся к корню для следующего символа
            }
        }

        scanner.close();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group451051/yazhou/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }


}
