package by.it.group451051.mozol.lesson13;

import java.util.Scanner;

public class GraphC {

    // Максимальное количество вершин (для латинского алфавита A-Z)
    private static final int MAX_V = 26;

    private static boolean[] visited = new boolean[MAX_V];
    private static boolean[] present = new boolean[MAX_V];

    // Списки смежности для исходного графа и транспонированного (обратного)
    private static int[][] adj = new int[MAX_V][MAX_V];
    private static int[] adjSize = new int[MAX_V];

    private static int[][] adjRev = new int[MAX_V][MAX_V];
    private static int[] adjRevSize = new int[MAX_V];

    // Массив для хранения порядка завершения функций DFS (симуляция стека)
    private static int[] postOrder = new int[MAX_V];
    private static int postOrderIdx = 0;

    // Массив для сбора вершин текущей компоненты сильной связности
    private static int[] component = new int[MAX_V];
    private static int componentSize = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) {
            return;
        }
        String input = scanner.nextLine();

        // Разбираем строку по разделителям (пробелы, запятые)
        String[] tokens = input.split("[\\s,]+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            // Выделяем вершины из конструкции вида "C->B"
            String[] parts = token.split("->");
            if (parts.length == 2) {
                int u = parts[0].trim().charAt(0) - 'A';
                int v = parts[1].trim().charAt(0) - 'A';

                present[u] = true;
                present[v] = true;

                // Добавляем ребро в прямой граф
                adj[u][adjSize[u]++] = v;
                // Добавляем ребро в транспонированный граф
                adjRev[v][adjRevSize[v]++] = u;
            }
        }

        // Шаг 1: Запуск DFS на прямом графе для построения порядка завершения вершин.
        // Чтобы гарантировать детерминированность и лексикографический приоритет обхода,
        // сначала отсортируем списки смежности по убыванию (для правильного извлечения).
        for (int i = 0; i < MAX_V; i++) {
            if (present[i]) {
                sortDescending(adj[i], adjSize[i]);
            }
        }

        // Запускаем DFS в лексикографическом порядке (от A к Z)
        for (int i = 0; i < MAX_V; i++) {
            if (present[i] && !visited[i]) {
                dfs1(i);
            }
        }

        // Обнуляем массив посещенных вершин для второго прохода DFS
        for (int i = 0; i < MAX_V; i++) {
            visited[i] = false;
        }

        // Шаг 2: Обход транспонированного графа в порядке, обратном postOrder.
        // Перед этим сортируем списки смежности транспонированного графа.
        for (int i = 0; i < MAX_V; i++) {
            if (present[i]) {
                sortDescending(adjRev[i], adjRevSize[i]);
            }
        }

        // Идем с конца массива postOrder (от истоков к стокам в метаграфе компонент)
        for (int i = postOrderIdx - 1; i >= 0; i--) {
            int v = postOrder[i];
            if (!visited[v]) {
                componentSize = 0;

                // Собираем всю компоненту сильной связности
                dfs2(v);

                // Сортируем вершины внутри компоненты по возрастанию (лексикографически)
                sortAscending(component, componentSize);

                // Выводим компоненту (без пробелов и табуляции)
                for (int j = 0; j < componentSize; j++) {
                    System.out.print((char) (component[j] + 'A'));
                }
                System.out.println();
            }
        }
    }

    // Первый DFS (поиск времени выхода)
    private static void dfs1(int v) {
        visited[v] = true;
        for (int i = 0; i < adjSize[v]; i++) {
            int neighbor = adj[v][i];
            if (!visited[neighbor]) {
                dfs1(neighbor);
            }
        }
        postOrder[postOrderIdx++] = v;
    }

    // Второй DFS (выделение КЧС по транспонированному графу)
    private static void dfs2(int v) {
        visited[v] = true;
        component[componentSize++] = v;
        for (int i = 0; i < adjRevSize[v]; i++) {
            int neighbor = adjRev[v][i];
            if (!visited[neighbor]) {
                dfs2(neighbor);
            }
        }
    }

    // Сортировка массива по возрастанию (сортировка выбором)
    private static void sortAscending(int[] arr, int size) {
        for (int i = 0; i < size - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < size; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

    // Сортировка массива по убыванию
    private static void sortDescending(int[] arr, int size) {
        for (int i = 0; i < size - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < size; j++) {
                if (arr[j] > arr[maxIdx]) {
                    maxIdx = j;
                }
            }
            int temp = arr[maxIdx];
            arr[maxIdx] = arr[i];
            arr[i] = temp;
        }
    }
}