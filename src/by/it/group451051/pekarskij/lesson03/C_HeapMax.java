package by.it.group451051.pekarskij.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C_HeapMax {

    private class MaxHeap {
    // массив для хранения кучи
    private List<Long> heap = new ArrayList<>();

    int siftDown(int i) { // просеивание вниз
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            if (left < heap.size() && heap.get(left) > heap.get(largest)) {
                largest = left;
            }
            if (right < heap.size() && heap.get(right) > heap.get(largest)) {
                largest = right;
            }

            if (largest == i) break;

            long tmp = heap.get(i);
            heap.set(i, heap.get(largest));
            heap.set(largest, tmp);

            i = largest;
        }
        return i;
    }

    int siftUp(int i) { // просеивание вверх
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap.get(parent) >= heap.get(i)) break;

            long tmp = heap.get(parent);
            heap.set(parent, heap.get(i));
            heap.set(i, tmp);

            i = parent;
        }
        return i;
    }

    void insert(Long value) { // вставка
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    Long extractMax() { // извлечение максимума
        if (heap.isEmpty()) return null;

        long result = heap.get(0);

        long last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }

        return result;
    }
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
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX="+instance.findMaxValue(stream));
    }

}
