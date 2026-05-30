package by.it.group451051.mozol.lesson14;
import java.util.*;

public class SitesB {

    // Вспомогательный класс для DSU
    static class DSU {
        private final Map<String, String> parent = new HashMap<>();
        private final Map<String, Integer> size = new HashMap<>();

        public void makeSet(String site) {
            if (!parent.containsKey(site)) {
                parent.put(site, site);
                size.put(site, 1);
            }
        }

        public String find(String site) {
            if (parent.get(site).equals(site)) {
                return site;
            }
            // Эвристика 2: Сжатие пути
            String root = find(parent.get(site));
            parent.put(site, root);
            return root;
        }

        public void union(String site1, String site2) {
            String root1 = find(site1);
            String root2 = find(site2);

            if (!root1.equals(root2)) {
                // Эвристика 1: Объединение по размеру
                if (size.get(root1) < size.get(root2)) {
                    String temp = root1;
                    root1 = root2;
                    root2 = temp;
                }
                parent.put(root2, root1);
                size.put(root1, size.get(root1) + size.get(root2));
            }
        }

        public Collection<Integer> getClusterSizes() {
            Map<String, Integer> clusterSizes = new HashMap<>();
            for (String site : parent.keySet()) {
                String root = find(site);
                clusterSizes.put(root, size.get(root));
            }
            return clusterSizes.values();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DSU dsu = new DSU();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("end".equals(line)) break;

            String[] sites = line.split("\\+");
            if (sites.length == 2) {
                dsu.makeSet(sites[0]);
                dsu.makeSet(sites[1]);
                dsu.union(sites[0], sites[1]);
            }
        }

        List<Integer> sizes = new ArrayList<>(dsu.getClusterSizes());
        sizes.sort(Collections.reverseOrder());

        for (int i = 0; i < sizes.size(); i++) {
            System.out.print(sizes.get(i) + (i == sizes.size() - 1 ? "" : " "));
        }
    }
}