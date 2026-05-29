package by.it.group451051.mozol.lesson14;

import java.util.Scanner;

public class PointsA {

    // Класс для представления точки в 3D пространстве
    private static class Point {
        int x, y, z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    // Структура данных DSU (Система непересекающихся множеств)
    private static class DSU {
        int[] parent;
        int[] size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;  // Изначально каждый элемент сам себе родитель
                size[i] = 1;    // Начальный размер каждого кластера — 1
            }
        }

        // Поиск представителя множества сжатием путей (Path Compression)
        int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]);
        }

        // Объединение множеств с эвристикой по размеру поддерева (Size heuristic)
        void union(int i, int j) {
            int rootI = find(i);
            int rootY = find(j);

            if (rootI != rootY) {
                if (size[rootI] < size[rootY]) {
                    parent[rootI] = rootY;
                    size[rootY] += size[rootI];
                } else {
                    parent[rootY] = rootI;
                    size[rootI] += size[rootY];
                }
            }
        }
    }

    // Метод вычисления Евклидова расстояния между двумя 3D-точками
    private static double distance(Point p1, Point p2) {
        return Math.sqrt(
                Math.pow(p1.x - p2.x, 2) +
                        Math.pow(p1.y - p2.y, 2) +
                        Math.pow(p1.z - p2.z, 2)
        );
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int d = scanner.nextInt();
        int n = scanner.nextInt();

        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        }

        DSU dsu = new DSU(n);

        // Перебираем все пары точек и объединяем их, если расстояние строго меньше D
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (distance(points[i], points[j]) < d) {
                    dsu.union(i, j);
                }
            }
        }

        // Подсчитываем размеры получившихся кластеров
        int[] clusterSizes = new int[n];
        int uniqueClustersCount = 0;

        for (int i = 0; i < n; i++) {
            // Находим корень для каждой точки
            int root = dsu.find(i);
            // Если точка сама является корнем кластера, фиксируем её размер
            if (root == i) {
                clusterSizes[uniqueClustersCount++] = dsu.size[root];
            }
        }

        // Сортируем размеры полученных кластеров по возрастанию (сортировка вставками)
        for (int i = 1; i < uniqueClustersCount; i++) {
            int key = clusterSizes[i];
            int j = i - 1;
            while (j >= 0 && clusterSizes[j] > key) {
                clusterSizes[j + 1] = clusterSizes[j];
                j = j - 1;
            }
            clusterSizes[j + 1] = key;
        }

        // Выводим результат в консоль
        for (int i = 0; i < uniqueClustersCount; i++) {
            System.out.print(clusterSizes[i]);
            if (i < uniqueClustersCount - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
