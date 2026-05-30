package by.it.group451051.mozol.lesson13;

import java.util.*;

public class GraphA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;
        String input = scanner.nextLine();

        // 1. Собираем все уникальные имена вершин
        String[] tokens = input.split("[\\s,>\\-]+");
        List<String> vertexNames = new ArrayList<>();
        for (String t : tokens) {
            if (!t.isEmpty() && !vertexNames.contains(t)) {
                vertexNames.add(t);
            }
        }
        // Сортируем имена, чтобы лексикографический порядок (A, B, C...) соблюдался сам собой
        Collections.sort(vertexNames);

        // Маппинг: имя -> индекс
        Map<String, Integer> nameToIndex = new HashMap<>();
        for (int i = 0; i < vertexNames.size(); i++) {
            nameToIndex.put(vertexNames.get(i), i);
        }

        int n = vertexNames.size();
        int[] inDegree = new int[n];
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        // 2. Парсим ребра
        String[] pairs = input.split("[,]+");
        for (String pair : pairs) {
            String[] nodes = pair.split("->");
            if (nodes.length == 2) {
                String uName = nodes[0].trim();
                String vName = nodes[1].trim();
                int u = nameToIndex.get(uName);
                int v = nameToIndex.get(vName);
                adj.get(u).add(v);
                inDegree[v]++;
            }
        }

        // 3. Алгоритм Кана
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }

        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            result.add(vertexNames.get(u));

            for (int v : adj.get(u)) {
                inDegree[v]--;
                if (inDegree[v] == 0) queue.add(v);
            }
        }

        // 4. Вывод
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + (i == result.size() - 1 ? "" : " "));
        }
    }
}