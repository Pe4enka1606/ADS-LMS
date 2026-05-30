package by.it.group451051.mozol.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {

    private static int[] parent;
    private static int[] size;
    private static int[] heights;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        // Количество шагов: 2^n - 1
        int totalSteps = (1 << n) - 1;

        parent = new int[totalSteps];
        size = new int[totalSteps];
        heights = new int[totalSteps];

        for (int i = 0; i < totalSteps; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // Заполняем высоты моделированием
        int[] currentPoles = new int[3];
        currentPoles[0] = n; // Стержень A
        solve(n, 0, 1, 2, new int[]{0}, currentPoles);

        // Оптимизированная группировка (линейная сложность)
        // firstOccurrence хранит индекс первого шага для данной высоты
        int[] firstOccurrence = new int[n + 1];
        for (int i = 0; i <= n; i++) firstOccurrence[i] = -1;

        for (int i = 0; i < totalSteps; i++) {
            int h = heights[i];
            if (firstOccurrence[h] == -1) {
                firstOccurrence[h] = i;
            } else {
                union(firstOccurrence[h], i);
            }
        }

        // Сбор размеров кластеров (только для корней DSU)
        int[] results = new int[totalSteps];
        int count = 0;
        for (int i = 0; i < totalSteps; i++) {
            if (parent[i] == i) {
                results[count++] = size[i];
            }
        }

        // Сортировка результатов методом пузырька (без коллекций)
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (results[j] > results[j + 1]) {
                    int temp = results[j];
                    results[j] = results[j + 1];
                    results[j + 1] = temp;
                }
            }
        }

        // Вывод
        for (int i = 0; i < count; i++) {
            System.out.print(results[i] + (i == count - 1 ? "" : " "));
        }
    }

    private static void solve(int n, int from, int to, int aux, int[] step, int[] poles) {
        if (n > 0) {
            solve(n - 1, from, aux, to, step, poles);

            poles[from]--;
            poles[to]++;

            // Находим максимум из трех стержней
            int maxH = poles[0];
            if (poles[1] > maxH) maxH = poles[1];
            if (poles[2] > maxH) maxH = poles[2];

            heights[step[0]++] = maxH;

            solve(n - 1, aux, to, from, step, poles);
        }
    }

    private static int find(int i) {
        if (parent[i] == i) return i;
        return parent[i] = find(parent[i]); // Сжатие пути
    }

    private static void union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI != rootJ) {
            // Объединение по размеру
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