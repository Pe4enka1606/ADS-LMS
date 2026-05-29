package by.it.group451051.mozol.lesson13;

import java.util.Scanner;

public class GraphA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) {
            return;
        }
        String input = scanner.nextLine();

        // 1. Находим максимальный индекс вершины, чтобы определить размер массивов
        int maxVertex = 0;
        // Разбираем строку по числам, игнорируя символы "->", "," и пробелы
        String[] tokens = input.split("[\\s,>\\-]+");

        int tokenCount = 0;
        for (String token : tokens) {
            if (!token.isEmpty()) {
                tokenCount++;
                int v = Integer.parseInt(token);
                if (v > maxVertex) {
                    maxVertex = v;
                }
            }
        }

        int numVertices = maxVertex + 1;

        // 2. Инициализируем структуры данных графа
        // Массив для подсчета входящих ребер (полустепень захода)
        int[] inDegree = new int[numVertices];

        // Списки смежности: так как коллекции запрещены, используем двумерный массив.
        // Сначала посчитаем количество исходящих ребер для каждой вершины (степень исхода)
        int[] outDegree = new int[numVertices];

        // Массив ребер для повторного прохода парсинга
        int[][] edges = new int[tokenCount / 2][2];
        int edgeIdx = 0;

        int from = -1;
        for (String token : tokens) {
            if (!token.isEmpty()) {
                if (from == -1) {
                    from = Integer.parseInt(token);
                } else {
                    int to = Integer.parseInt(token);
                    edges[edgeIdx][0] = from;
                    edges[edgeIdx][1] = to;
                    edgeIdx++;

                    outDegree[from]++;
                    inDegree[to]++;

                    from = -1; // сбрасываем для следующей пары
                }
            }
        }

        // Создаем jagged-array (зубчатый массив) под точное количество соседей каждой вершины
        int[][] adj = new int[numVertices][];
        for (int i = 0; i < numVertices; i++) {
            adj[i] = new int[outDegree[i]];
        }

        // Заполняем списки смежности
        int[] curIdx = new int[numVertices];
        for (int i = 0; i < edgeIdx; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj[u][curIdx[u]++] = v;
        }

        // 3. Алгоритм Кана с поддержкой лексикографического порядка
        // Будем использовать булев массив вершин, у которых входящая степень стала 0,
        // и которые еще не были извлечены (симуляция PriorityQueue на фиксированном массиве)
        boolean[] inQueue = new boolean[numVertices];
        int activeCount = 0;

        for (int i = 0; i < numVertices; i++) {
            if (inDegree[i] == 0) {
                inQueue[i] = true;
                activeCount++;
            }
        }

        // Массив для хранения результата
        int[] result = new int[numVertices];
        int resultIdx = 0;

        while (activeCount > 0) {
            // Ищем наименьший доступный индекс вершины с inDegree == 0 (лексикографический приоритет)
            int u = -1;
            for (int i = 0; i < numVertices; i++) {
                if (inQueue[i]) {
                    u = i;
                    break;
                }
            }

            // Извлекаем вершину из нашей "очереди"
            inQueue[u] = false;
            activeCount--;

            // Добавляем в результат
            result[resultIdx++] = u;

            // Уменьшаем степень захода у всех соседей выбранной вершины
            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    inQueue[v] = true;
                    activeCount++;
                }
            }
        }

        // 4. Вывод результата в консоль в требуемом формате
        for (int i = 0; i < resultIdx; i++) {
            System.out.print(result[i]);
            if (i < resultIdx - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
