package by.it.group451051.zhelubovskaya.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C_HeapMax {

    private class MaxHeap {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение.
        //Будет мало? Ну тогда можете его собрать как Generic и/или использовать в варианте B
        private List<Long> heap = new ArrayList<>();

        // Вспомогательные методы для навигации по куче
        private int getParentIndex(int index) {
            return (index - 1) / 2;
        }

        private int getLeftChildIndex(int index) {
            return 2 * index + 1;
        }

        private int getRightChildIndex(int index) {
            return 2 * index + 2;
        }  

        private void swapElements(int index1, int index2) {
            long temp = heap.get(index1);
            heap.set(index1, heap.get(index2));
            heap.set(index2, temp);
        }   

        int siftDown(int i) { //просеивание вверх
        int currentIndex = i;
        int leftChild = getLeftChildIndex(currentIndex);
        int rightChild = getRightChildIndex(currentIndex);
        int largestIndex = currentIndex;
            
        // Проверяем левого потомка
        if (leftChild < heap.size() && heap.get(leftChild) > heap.get(largestIndex)) {
            largestIndex = leftChild;
        }
            
        // Проверяем правого потомка
        if (rightChild < heap.size() && heap.get(rightChild) > heap.get(largestIndex)) {
            largestIndex = rightChild;
        }
            
        // Если найден потомок больше текущего элемента
        if (largestIndex != currentIndex) {
            swapElements(currentIndex, largestIndex);
            // Рекурсивно продолжаем просеивание
            siftDown(largestIndex);
        }
            return largestIndex;
        }

        int siftUp(int i) { //просеивание вниз
            int currentIndex = i;
            
            while (currentIndex > 0) {
                int parentIndex = getParentIndex(currentIndex);
                
                // Если текущий элемент больше родителя
                if (heap.get(currentIndex) > heap.get(parentIndex)) {
                    swapElements(currentIndex, parentIndex);
                    currentIndex = parentIndex;
                } else {
                    break;
                }
            }            

            return currentIndex;
        }

        void insert(Long value) { //вставка
            heap.add(value);
            siftUp(heap.size() - 1);            
        }

        Long extractMax() { //извлечение и удаление максимума
            if (heap.isEmpty()) {
                return null;
        }
            Long maxValue = heap.get(0);
                
            if (heap.size() == 1) {
                heap.remove(0);
            } else {
                // Перемещаем последний элемент в корень
                heap.set(0, heap.get(heap.size() - 1));
                heap.remove(heap.size() - 1);
                
                // Восстанавливаем свойство кучи
                if (!heap.isEmpty()) {
                    siftDown(0);
                }
        }
        
        return maxValue;
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
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX="+instance.findMaxValue(stream));
    }

    // РЕМАРКА. Это задание исключительно учебное.
    // Свои собственные кучи нужны довольно редко.
    // "В реальном бою" все существенно иначе. Изучите и используйте коллекции
    // TreeSet, TreeMap, PriorityQueue и т.д. с нужным CompareTo() для объекта внутри.
}
