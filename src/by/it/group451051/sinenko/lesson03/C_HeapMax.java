package by.it.group451051.sinenko.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Lesson 3. C_Heap.
// Задача: построить max-кучу = пирамиду = бинарное сбалансированное дерево на массиве.
// ВАЖНО! НЕЛЬЗЯ ИСПОЛЬЗОВАТЬ НИКАКИЕ КОЛЛЕКЦИИ, КРОМЕ ARRAYLIST (его можно, но только для массива)

//      Проверка проводится по данным файла
//      Первая строка входа содержит число операций 1 ≤ n ≤ 100000.
//      Каждая из последующих nn строк задают операцию одного из следующих двух типов:

//      Insert x, где 0 ≤ x ≤ 1000000000 — целое число;
//      ExtractMax.

//      Первая операция добавляет число x в очередь с приоритетами,
//      вторая — извлекает максимальное число и выводит его.

//      Sample Input:
//      6
//      Insert 200
//      Insert 10
//      ExtractMax
//      Insert 5
//      Insert 500
//      ExtractMax
//
//      Sample Output:
//      200
//      500


public class C_HeapMax {

    private class MaxHeap {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение.
        //Будет мало? Ну тогда можете его собрать как Generic и/или использовать в варианте B
        private List<Long> heap = new ArrayList<>();

         // вспомогательные методы для навигации
        private int parent(int i) {
            return (i - 1) / 2;
        }
    
        private int leftChild(int i) {
            return 2 * i + 1;
        }
    
        private int rightChild(int i) {
            return 2 * i + 2;
        }

        int siftDown(int i) { //просеивание вниз*

            int left = leftChild(i);
            int right = rightChild(i);
            int maxIndex = i;  // предполагаем что текущий элемент это максимум

            // проверяем левого ребенка
            if (left < heap.size() && heap.get(left) > heap.get(maxIndex)) {
                maxIndex = left;
            }
 
            // проверяем правого ребенка
            if (right < heap.size() && heap.get(right) > heap.get(maxIndex)) {
                maxIndex = right;
            }
            // если нашли ребенка больше родителя
            if (maxIndex != i) {
                Long temp = heap.get(i);     // Меняем местами
                heap.set(i, heap.get(maxIndex));
                heap.set(maxIndex, temp);

                siftDown(maxIndex); // рекурсивно продолжаем для новой позиции
            }
            return maxIndex;
        }

        int siftUp(int i) { //просеивание вверх*
            while (i > 0 && heap.get(i) > heap.get(parent(i))) {// пока не дошли до корня и элемент больше родителя
                Long temp = heap.get(i);            // меняем местами с родителем
                heap.set(i, heap.get(parent(i)));
                heap.set(parent(i), temp);
            
                i = parent(i);      // переходим к индексу родителя
            }
            return i;
        }

        void insert(Long value) { //вставка
            heap.add(value);           // добавляем элемент в конец
            siftUp(heap.size() - 1);   // просеиваем вверх чтобы восстановить свойство кучи
        }

        Long extractMax() { //извлечение и удаление максимума
            if (heap.isEmpty()) {
                return null;
            }

            Long result = heap.get(0);  // cохраняем корень
            heap.set(0, heap.get(heap.size() - 1));  // последний элемент перемещаем в корень
            heap.remove(heap.size() - 1);                   // удаляем последний элемент

            // если куча не пуста то восстанавливаем свойство кучи
            if (!heap.isEmpty()) {
                siftDown(0);
            }

            return result;
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    }

    //эта процедура читает данные из файла, ее можно не менять.
    Long findMaxValue(InputStream stream) {
        Long maxValue=0L;
        MaxHeap heap = new MaxHeap();
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(stream);
        Integer count = scanner.nextInt();
        for (int i = 0; i < count; ) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                Long res=heap.extractMax();
                if (res!=null && res>maxValue) maxValue=res;
                System.out.println();
                i++;
            }
            if (s.contains(" ")) {
                String[] p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert"))
                    heap.insert(Long.parseLong(p[1]));
                i++;
            //System.out.println(heap); //debug
            }
        }
        return maxValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/sinenko/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX="+instance.findMaxValue(stream));
    }

    // РЕМАРКА. Это задание исключительно учебное.
    // Свои собственные кучи нужны довольно редко.
    // "В реальном бою" все существенно иначе. Изучите и используйте коллекции
    // TreeSet, TreeMap, PriorityQueue и т.д. с нужным CompareTo() для объекта внутри.
}
