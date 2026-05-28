package by.it.group451051.belousova.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_QSortOptimized {

    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = Math.min(start, stop);
            this.stop = Math.max(start, stop);
        }

        @Override
        public int compareTo(Segment o) {
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            }
            return Integer.compare(this.stop, o.stop);
        }
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // число отрезков
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];

        // число точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        // читаем отрезки
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        // читаем точки (исправлено: было n, нужно m)
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортируем отрезки с помощью улучшенной быстрой сортировки
        quickSort3Way(segments, 0, n - 1);

        // Для каждой точки ищем отрезки, которые ее покрывают
        for (int i = 0; i < m; i++) {
            int point = points[i];

            // Бинарный поиск для нахождения первого отрезка, который МОЖЕТ покрывать точку
            // (первый отрезок с началом <= точке)
            int left = 0;
            int right = n - 1;
            int firstPossible = -1;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (segments[mid].start <= point) {
                    firstPossible = mid;
                    left = mid + 1; // ищем самый правый отрезок с началом <= точке
                } else {
                    right = mid - 1;
                }
            }

            if (firstPossible == -1) {
                // Нет отрезков, начинающихся до точки
                result[i] = 0;
            } else {
                // Теперь ищем влево от firstPossible все отрезки, которые покрывают точку
                // Так как отрезки отсортированы по началу, мы можем идти влево,
                // пока начало <= точке, и проверять конец >= точке
                int count = 0;
                for (int j = firstPossible; j >= 0 && segments[j].start <= point; j--) {
                    if (segments[j].stop >= point) {
                        count++;
                        // После нахождения первого подходящего отрезка,
                        // ищем остальные в обе стороны
                        // Ищем влево
                        for (int k = j - 1; k >= 0 && segments[k].start <= point; k--) {
                            if (segments[k].stop >= point) {
                                count++;
                            }
                        }
                        // Ищем вправо (только если есть элементы справа)
                        for (int k = j + 1; k < n && segments[k].start <= point; k++) {
                            if (segments[k].stop >= point) {
                                count++;
                            }
                        }
                        break; // нашли первый отрезок и посчитали все
                    }
                }
                result[i] = count;
            }
        }

        scanner.close();
        return result;
    }

    // Улучшенная быстрая сортировка с 3-разбиением и без хвостовой рекурсии
    private void quickSort3Way(Segment[] arr, int low, int high) {
        while (low < high) {
            // Выбираем опорный элемент (медиана трех)
            int pivotIndex = medianOfThree(arr, low, high);
            Segment pivot = arr[pivotIndex];

            // Перемещаем опорный элемент в начало
            swap(arr, pivotIndex, low);

            int lt = low;   // конец участка элементов меньше опорного
            int gt = high;  // начало участка элементов больше опорного
            int i = low + 1;

            // 3-разбиение
            while (i <= gt) {
                int cmp = arr[i].compareTo(pivot);
                if (cmp < 0) {
                    swap(arr, lt++, i++);
                } else if (cmp > 0) {
                    swap(arr, i, gt--);
                } else {
                    i++;
                }
            }

            // Рекурсивно сортируем меньшую часть, а большую часть обрабатываем в цикле
            // (элиминация хвостовой рекурсии)
            if (lt - low < high - gt) {
                quickSort3Way(arr, low, lt - 1);
                low = gt + 1;
            } else {
                quickSort3Way(arr, gt + 1, high);
                high = lt - 1;
            }
        }
    }

    // Медиана трех для выбора опорного элемента
    private int medianOfThree(Segment[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        if (arr[low].compareTo(arr[mid]) > 0) {
            swap(arr, low, mid);
        }
        if (arr[low].compareTo(arr[high]) > 0) {
            swap(arr, low, high);
        }
        if (arr[mid].compareTo(arr[high]) > 0) {
            swap(arr, mid, high);
        }

        return mid; // медиана теперь в позиции mid
    }

    // Вспомогательный метод для обмена элементов
    private void swap(Segment[] arr, int i, int j) {
        Segment temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}