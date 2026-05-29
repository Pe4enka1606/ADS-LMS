package by.it.group451051.mozol.lesson13;

import java.util.Scanner;

public class GraphB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) {
            return;
        }
        String input = scanner.nextLine();

        // 1. Находим максимальный индекс вершины, чтобы определить размер массивов
        int maxVertex = 0;
        // Разбираем строку, используя разделители: пробелы, запятые, стрелочки
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

        // 2. Инициализируем структуры графа
        int[] inDegree = new int[numVertices];
        int[] outDegree = new int[numVertices];

        // Булев массив для отслеживания вершин, которые вообще упоминались в графе
        boolean[] visited = new boolean[numVertices];
        int uniqueVerticesCount = 0;

        // Массив ребер для парсинга
        int[][] edges = new int[tokenCount / 2][2];
        int edgeIdx = 0;

        int from = -1;
        for (String token : tokens) {
            if (!token.isEmpty()) {
                int v = Integer.parseInt(token);
                if (!visited[v]) {
                    visited[v] = true;
                    uniqueVerticesCount++;
                }

                if (from == -1) {
                    from = v;
                } else {
                    edges[edgeIdx][0] = from;
                    edges[edgeIdx][1] = v;
                    edgeIdx++;

                    outDegree[from]++;
                    inDegree[v]++;

                    from = -1; // Сброс для следующей пары
                }
            }
        }

        // Строим списки смежности (зубчатый массив)
        int[][] adj = new int[numVertices][];
        for (int i = 0; i < numVertices; i++) {
            adj[i] = new int[outDegree[i]];
        }

        int[] curIdx = new int[numVertices];
        for (int i = 0; i < edgeIdx; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj[u][curIdx[u]++] = v;
        }

        // 3. Алгоритм Кана для определения ацикличности
        // Помещаем в "очередь" все вершины, у которых in-degree == 0
        boolean[] inQueue = new boolean[numVertices];
        int activeCount = 0;

        for (int i = 0; i < numVertices; i++) {
            // Вершина должна присутствовать в графе и не иметь входящих ребер
            if (visited[i] && inDegree[i] == 0) {
                inQueue[i] = true;
                activeCount++;
            }
        }

        int processedVertices = 0;

        while (activeCount > 0) {
            // Извлекаем любую доступную вершину с inDegree == 0
            int u = -1;
            for (int i = 0; i < numVertices; i++) {
                if (inQueue[i]) {
                    u = i;
                    break;
                }
            }

            inQueue[u] = false;
            activeCount--;
            processedVertices++;

            // Уменьшаем степень захода у смежных вершин
            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    inQueue[v] = true;
                    activeCount++;
                }
            }
        }

        // 4. Проверка: если количество обработанных вершин совпадает с общим числом
        // уникальных вершин графа — циклов нет. Иначе — цикл присутствует.
        if (processedVertices == uniqueVerticesCount) {
            System.out.println("no");
        } else {
            System.out.println("yes");
        }
    }
}
