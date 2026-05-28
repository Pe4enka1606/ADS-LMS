package by.it.group451051.shiman.lesson03;

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

        int siftDown(int i) { //просеивание вниз (восстановление свойства кучи сверху вниз)
            int leftChild = 2 * i + 1; // Индекс левого потомка
            int rightChild = 2 * i + 2; // Индекс правого потомка
            int largest = i; // Предполагаем, что текущий элемент - наибольший

            // Проверяем, существует ли левый потомок и больше ли он текущего элемента
            if (leftChild < heap.size() && heap.get(leftChild) > heap.get(largest)) {
                largest = leftChild;
            }

            // Проверяем, существует ли правый потомок и больше ли он текущего наибольшего
            if (rightChild < heap.size() && heap.get(rightChild) > heap.get(largest)) {
                largest = rightChild;
            }

            // Если наибольший элемент не текущий, меняем их местами
            if (largest != i) {
                Long temp = heap.get(i);
                heap.set(i, heap.get(largest));
                heap.set(largest, temp);
                // Рекурсивно продолжаем просеивание для нового положения элемента
                return siftDown(largest);
            }
            return i; // Возвращаем индекс элемента после просеивания
        }

        int siftUp(int i) { //просеивание вверх (восстановление свойства кучи снизу вверх)
            // Пока не дошли до корня и текущий элемент больше родителя
            while (i > 0 && heap.get(i) > heap.get((i - 1) / 2)) {
                int parent = (i - 1) / 2; // Индекс родителя
                // Меняем местами текущий элемент с родителем
                Long temp = heap.get(i);
                heap.set(i, heap.get(parent));
                heap.set(parent, temp);
                i = parent; // Переходим к индексу родителя для следующей итерации
            }
            return i; // Возвращаем индекс элемента после просеивания
        }

        void insert(Long value) { //вставка нового элемента
            heap.add(value); // Добавляем элемент в конец массива
            siftUp(heap.size() - 1); // Просеиваем вверх, чтобы восстановить свойство кучи
        }

        Long extractMax() { //извлечение и удаление максимума
            if (heap.isEmpty()) {
                return null; // Если куча пуста, возвращаем null
            }

            Long result = heap.get(0); // Максимальный элемент находится в корне (индекс 0)

            // Перемещаем последний элемент в корень
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1); // Удаляем последний элемент

            // Если куча не пуста, просеиваем новый корень вниз
            if (!heap.isEmpty()) {
                siftDown(0);
            }

            return result; // Возвращаем максимальный элемент
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
        scanner.nextLine(); // Считываем оставшуюся часть строки после числа
        for (int i = 0; i < count; ) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                Long res=heap.extractMax();
                if (res!=null && res>maxValue) maxValue=res;
                System.out.println(res);
                i++;
            }
            if (s.contains(" ")) {
                String[] p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert")) {
                    heap.insert(Long.parseLong(p[1]));
                }
                i++;
                //System.out.println(heap); //debug
            }
        }
        return maxValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX="+instance.findMaxValue(stream));
    }

    // РЕМАРКА. Это задание исключительно учебное.
    // Свои собственные кучи нужны довольно редко.
    // "В реальном бою" все существенно иначе. Изучите и используйте коллекции
    // TreeSet, TreeMap, PriorityQueue и т.д. с нужным CompareTo() для объекта внутри.
}