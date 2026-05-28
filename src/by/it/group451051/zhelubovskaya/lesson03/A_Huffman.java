package by.it.group451051.zhelubovskaya.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class A_Huffman {

    //Изучите классы Node InternalNode LeafNode
    abstract class Node implements Comparable<Node> {
        //абстрактный класс элемент дерева
        //(сделан abstract, чтобы нельзя было использовать его напрямую)
        //а только через его версии InternalNode и LeafNode
        private final int frequence; //частота символов

        //генерация кодов (вызывается на корневом узле
        //один раз в конце, т.е. после построения дерева)
        abstract void fillCodes(String code);

        //конструктор по умолчанию
        private Node(int frequence) {
            this.frequence = frequence;
        }

        //метод нужен для корректной работы узла в приоритетной очереди
        //или для сортировок
        @Override
        public int compareTo(Node o) {
            return Integer.compare(frequence, o.frequence);
        }
    }

    private class InternalNode extends Node {
        //внутренный узел дерева
        Node left;  //левый ребенок бинарного дерева
        Node right; //правый ребенок бинарного дерева

        //для этого дерева не существует внутренних узлов без обоих детей
        //поэтому вот такого конструктора будет достаточно
        InternalNode(Node left, Node right) {
            super(left.frequence + right.frequence);
            this.left = left;
            this.right = right;
        }

        @Override
        void fillCodes(String code) {
            left.fillCodes(code + "0");
            right.fillCodes(code + "1");
        }

    }


    private class LeafNode extends Node {
        //лист
        char symbol; //символы хранятся только в листах

        LeafNode(int frequence, char symbol) {
            super(frequence);
            this.symbol = symbol;
        }

        @Override
        void fillCodes(String code) {
            //добрались до листа, значит рекурсия закончена, код уже готов
            //и можно запомнить его в индексе для поиска кода по символу.
            codes.put(this.symbol, code);
        }
    }

    //индекс данных из листьев
    static private Map<Character, String> codes = new TreeMap<>();


    //НАЧАЛО ЗАДАЧИ
    String encode(File file) throws FileNotFoundException {
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(file);
        String s = scanner.next();

        Map<Character, Integer> count = new HashMap<>();
        //1. переберем все символы по очереди и рассчитаем их частоту в Map count
            //для каждого символа добавим 1 если его в карте еще нет или инкремент если есть.
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if (count.containsKey(currentChar)) {
                count.put(currentChar, count.get(currentChar) + 1);
            } else {
                count.put(currentChar, 1);
            }
        }
        //2. перенесем все символы в приоритетную очередь в виде листьев
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : count.entrySet()) {
            LeafNode leaf = new LeafNode(entry.getValue(), entry.getKey());
            priorityQueue.offer(leaf);
        }

        while (priorityQueue.size() > 1) {
            // Извлекаем два узла с наименьшей частотой
            Node leftChild = priorityQueue.poll();
            Node rightChild = priorityQueue.poll();
            
            // Создаем внутренний узел (родителя)
            InternalNode parentNode = new InternalNode(leftChild, rightChild);
            
            // Возвращаем родителя в очередь
            priorityQueue.offer(parentNode);
        }

        StringBuilder sb = new StringBuilder();

 // 4. Извлекаем корень дерева и генерируем коды
        Node rootNode = priorityQueue.poll();
        if (rootNode != null) {
            rootNode.fillCodes("");
        }
        
        // 5. Кодируем исходную строку
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            sb.append(codes.get(currentChar));
        }
        return sb.toString();

    }
 
    // КОНЕЦ ЗАДАЧИ 


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group451051/zhelubovskaya/lesson03/dataHuffman.txt");
        A_Huffman instance = new A_Huffman();
        long startTime = System.currentTimeMillis();
        String result = instance.encode(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("%d %d\n", codes.size(), result.length());
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
        System.out.println(result);
    }

}
