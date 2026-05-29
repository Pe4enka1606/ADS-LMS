package by.it.group451051.mozol.lesson14;

import java.util.Scanner;

public class SitesB {

    // Ограничение на максимальное количество уникальных сайтов
    private static final int MAX_SITES = 1000;

    // Структура данных DSU с двумя обязательными эвристиками
    private static class DSU {
        int[] parent;
        int[] size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;  // Изначально каждый элемент — сам себе родитель
                size[i] = 1;    // Начальный размер каждого кластера — 1
            }
        }

        // Эвристика 2: Сокращение пути (Path Compression)
        int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]); // Сплющивание дерева при поиске
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

    // Параллельные массивы для ручного мапирования String -> Integer (замена HashMap)
    private static String[] siteNames = new String[MAX_SITES];
    private static int siteCount = 0;

    // Метод получения уникального ID для сайта. Если сайта еще нет, он регистрируется.
    private static int getSiteId(String name) {
        for (int i = 0; i < siteCount; i++) {
            if (siteNames[i].equals(name)) {
                return i;
            }
        }
        siteNames[siteCount] = name;
        return siteCount++;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Временные массивы для хранения ребер до инициализации DSU
        int[][] edges = new int[MAX_SITES][2];
        int edgeCount = 0;

        // 1. Чтение входных данных до строки "end"
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("end")) {
                break;
            }
            if (line.isEmpty()) {
                continue;
            }

            int plusIdx = line.indexOf('+');
            if (plusIdx != -1) {
                String siteA = line.substring(0, plusIdx).trim();
                String siteB = line.substring(plusIdx + 1).trim();

                int idA = getSiteId(siteA);
                int idB = getSiteId(siteB);

                edges[edgeCount][0] = idA;
                edges[edgeCount][1] = idB;
                edgeCount++;
            }
        }

        // 2. Инициализация DSU точным количеством зарегистрированных сайтов
        DSU dsu = new DSU(siteCount);

        // Объединяем связанные сайты в кластеры
        for (int i = 0; i < edgeCount; i++) {
            dsu.union(edges[i][0], edges[i][1]);
        }

        // 3. Подсчет размеров полученных кластеров
        int[] clusterSizes = new int[siteCount];
        int uniqueClustersCount = 0;

        for (int i = 0; i < siteCount; i++) {
            int root = dsu.find(i);
            // Если элемент сам является корнем дерева кластера, фиксируем итоговый размер группы
            if (root == i) {
                clusterSizes[uniqueClustersCount++] = dsu.size[root];
            }
        }

        // 4. Сортировка размеров кластеров по возрастанию (сортировка выбором)
        for (int i = 0; i < uniqueClustersCount - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < uniqueClustersCount; j++) {
                if (clusterSizes[j] < clusterSizes[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = clusterSizes[minIdx];
            clusterSizes[minIdx] = clusterSizes[i];
            clusterSizes[i] = temp;
        }

        // 5. Вывод результатов на консоль в требуемом формате
        for (int i = 0; i < uniqueClustersCount; i++) {
            System.out.print(clusterSizes[i]);
            if (i < uniqueClustersCount - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}