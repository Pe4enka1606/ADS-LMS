package by.it.group451051.pekarskij.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class A_Huffman {

    abstract class Node implements Comparable<Node> {
       
        private final int frequence; //частота символов

        abstract void fillCodes(String code);

        //конструктор по умолчанию
        private Node(int frequence) {
            this.frequence = frequence;
        }

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
            codes.put(this.symbol, code);
        }
    }

    static private Map<Character, String> codes = new TreeMap<>();

    String encode(File file) throws FileNotFoundException {
    // читаем строку
    Scanner scanner = new Scanner(file);
    String s = scanner.next();

    // очищаем карту кодов на случай повторного вызова
    codes.clear();

    // считаем частоты символов
    Map<Character, Integer> count = new HashMap<>();
    for (char c : s.toCharArray()) {
        count.put(c, count.getOrDefault(c, 0) + 1);
    }

    // создаем очередь листьев
    PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
    for (Map.Entry<Character, Integer> entry : count.entrySet()) {
        priorityQueue.add(new LeafNode(entry.getValue(), entry.getKey()));
    }

    // если строка состоит из одного символа
    if (priorityQueue.size() == 1) {
        Node only = priorityQueue.poll();
        codes.put(((LeafNode) only).symbol, "0");
    } else {
        // строим дерево Хаффмана
        while (priorityQueue.size() > 1) {
            Node a = priorityQueue.poll();
            Node b = priorityQueue.poll();
            priorityQueue.add(new InternalNode(a, b));
        }

        // генерируем коды
        Node root = priorityQueue.poll();
        root.fillCodes("");
    }

    // кодируем строку
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
        sb.append(codes.get(c));
    }

    return sb.toString();
}
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group451051/pekarskij/lesson03/dataHuffman.txt");
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
