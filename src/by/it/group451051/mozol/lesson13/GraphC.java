package by.it.group451051.mozol.lesson13;

import java.util.*;

public class GraphC {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;
        String input = scanner.nextLine();

        // 1. Сбор всех уникальных вершин
        String[] tokens = input.split("[\\s,]+");
        List<String> vertexNames = new ArrayList<>();
        for (String t : tokens) {
            String[] parts = t.split("->");
            for (String part : parts) {
                if (!part.isEmpty() && !vertexNames.contains(part)) vertexNames.add(part);
            }
        }
        Collections.sort(vertexNames);

        Map<String, Integer> nameToIndex = new HashMap<>();
        for (int i = 0; i < vertexNames.size(); i++) nameToIndex.put(vertexNames.get(i), i);

        int n = vertexNames.size();
        List<List<Integer>> adj = new ArrayList<>();
        List<List<Integer>> revAdj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
            revAdj.add(new ArrayList<>());
        }

        // 2. Построение графа и обратного графа
        for (String t : tokens) {
            String[] parts = t.split("->");
            if (parts.length == 2) {
                int u = nameToIndex.get(parts[0]);
                int v = nameToIndex.get(parts[1]);
                adj.get(u).add(v);
                revAdj.get(v).add(u);
            }
        }

        // 3. Алгоритм Косарайю: первый проход (заполнение стека)
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) if (!visited[i]) dfs1(i, adj, visited, stack);

        // 4. Второй проход по транспонированному графу
        Arrays.fill(visited, false);
        List<List<String>> components = new ArrayList<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<String> component = new ArrayList<>();
                dfs2(v, revAdj, visited, component, vertexNames);
                Collections.sort(component);
                components.add(component);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < components.size(); i++) {
            List<String> comp = components.get(i);
            Collections.sort(comp);
            for (String s : comp) sb.append(s);
            if (i < components.size() - 1) sb.append("\n");
        }
        System.out.print(sb.toString()); // Вывод строго один раз
    }

    private static void dfs1(int v, List<List<Integer>> adj, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (int neighbor : adj.get(v)) if (!visited[neighbor]) dfs1(neighbor, adj, visited, stack);
        stack.push(v);
    }

    private static void dfs2(int v, List<List<Integer>> revAdj, boolean[] visited, List<String> comp, List<String> names) {
        visited[v] = true;
        comp.add(names.get(v));
        for (int neighbor : revAdj.get(v)) if (!visited[neighbor]) dfs2(neighbor, revAdj, visited, comp, names);
    }
}