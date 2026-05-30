package by.it.group451051.mozol.lesson14;

import java.util.*;

public class PointsA {
    // Внутренний класс для DSU
    static class DSU {
        int[] parent;
        int[] size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]); // Сжатие пути
        }

        void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                // Объединение по размеру
                if (size[rootI] < size[rootJ]) {
                    int temp = rootI; rootI = rootJ; rootJ = temp;
                }
                parent[rootJ] = rootI;
                size[rootI] += size[rootJ];
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNext()) return;

        double D = sc.nextDouble();
        int N = sc.nextInt();

        double[][] points = new double[N][3];
        for (int i = 0; i < N; i++) {
            points[i][0] = sc.nextDouble();
            points[i][1] = sc.nextDouble();
            points[i][2] = sc.nextDouble();
        }

        DSU dsu = new DSU(N);

        // Объединение точек по условию расстояния
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                double dist = Math.hypot(Math.hypot(points[i][0] - points[j][0],
                                points[i][1] - points[j][1]),
                        points[i][2] - points[j][2]);
                if (dist < D) {
                    dsu.union(i, j);
                }
            }
        }

        // Собираем размеры всех корневых кластеров
        List<Integer> clusterSizes = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (dsu.parent[i] == i) {
                clusterSizes.add(dsu.size[i]);
            }
        }

        // Сортировка по убыванию
        clusterSizes.sort(Collections.reverseOrder());

        // Вывод результатов
        for (int i = 0; i < clusterSizes.size(); i++) {
            System.out.print(clusterSizes.get(i) + (i == clusterSizes.size() - 1 ? "" : " "));
        }
    }
}
