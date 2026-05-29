package by.it.group451051.mozol.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {

    // Класс структуры данных DSU с двумя обязательными эвристиками
    private static class DSU {
        int[] parent;
        int[] size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;  // Эвристика 1: изначально каждый элемент сам себе родитель
                size[i] = 1;    // Изначальный размер поддерева равен 1
            }
        }

        // Эвристика 2: Сжатие пути поддерева (Path Compression)
        int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]);
        }

        // Эвристика 1: Объединение по размеру поддерева (Size heuristic)
        void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (size[rootI] < size[rootJ]) {
                    parent[rootI] = rootJ;
                    size[rootJ] += size[rootI];
                } else {
                    parent[rootJ] = rootI;
                    size[rootI] += size[rootJ];
                }
            }
        }
    }

    // Вспомогательный класс для имитации трех стержней
    private static class Towers {
        int[] a, b, c;
        int sizeA, sizeB, sizeC;

        Towers(int n) {
            a = new int[n];
            b = new int[n];
            c = new int[n];
            sizeA = n;
            sizeB = 0;
            sizeC = 0;
            // Заполняем стержень A кольцами от большего к меньшему
            for (int i = 0; i < n; i++) {
                a[i] = n - i;
            }
        }

        // Перемещение верхнего диска с одного стержня на другой
        void move(char from, char to) {
            int disk = 0;
            // Снятие диска
            if (from == 'A') disk = a[--sizeA];
            else if (from == 'B') disk = b[--sizeB];
            else if (from == 'C') disk = c[--sizeC];

            // Посадка диска
            if (to == 'A') a[sizeA++] = disk;
            else if (to == 'B') b[sizeB++] = disk;
            else if (to == 'C') c[sizeC++] = disk;
        }

        // Вычисление максимальной высоты среди трех стержней на текущем шаге
        int getMaxHeight() {
            int max = sizeA;
            if (sizeB > max) max = sizeB;
            if (sizeC > max) max = sizeC;
            return max;
        }
    }

    // Глобальные переменные для отслеживания генерации состояний в линейный массив
    private static int[] maxHeights;
    private static int stepCounter = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        // Количество ходов равно 2^n - 1
        int totalSteps = (1 << n) - 1;
        maxHeights = new int[totalSteps];
        stepCounter = 0;

        Towers towers = new Towers(n);

        // Запуск генерации последовательности Ханойских ходов
        // Стартовое состояние не учитывается по условию задачи
        // Задача перенести с A на B, используя вспомогательный C
        runHanoi(n, 'A', 'B', 'C', towers);

        // Инициализируем DSU количеством шагов
        DSU dsu = new DSU(totalSteps);

        // Группируем шаги: объединяем текущий шаг со всеми предыдущими,
        // у которых точно такое же значение максимальной высоты башни.
        for (int i = 0; i < totalSteps; i++) {
            for (int j = 0; j < i; j++) {
                if (maxHeights[i] == maxHeights[j]) {
                    dsu.union(i, j);
                }
            }
        }

        // Вычисляем размеры полученных поддеревьев (кластеров)
        int[] clusterSizes = new int[totalSteps];
        int uniqueClusters = 0;

        for (int i = 0; i < totalSteps; i++) {
            int root = dsu.find(i);
            if (root == i) {
                clusterSizes[uniqueClusters++] = dsu.size[root];
            }
        }

        // Сортируем размеры поддеревьев по возрастанию (сортировка выбором)
        for (int i = 0; i < uniqueClusters - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < uniqueClusters; j++) {
                if (clusterSizes[j] < clusterSizes[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = clusterSizes[minIdx];
            clusterSizes[minIdx] = clusterSizes[i];
            clusterSizes[i] = temp;
        }

        // Вывод результатов в консоль через пробел
        for (int i = 0; i < uniqueClusters; i++) {
            System.out.print(clusterSizes[i]);
            if (i < uniqueClusters - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    // Рекурсивный алгоритм решения Ханойской башни с фиксацией состояний
    private static void runHanoi(int count, char from, char to, char aux, Towers towers) {
        if (count == 1) {
            towers.move(from, to);
            maxHeights[stepCounter++] = towers.getMaxHeight();
            return;
        }

        runHanoi(count - 1, from, aux, to, towers);

        towers.move(from, to);
        maxHeights[stepCounter++] = towers.getMaxHeight();

        runHanoi(count - 1, aux, to, from, towers);
    }
}