package by.it.group451051.belousova.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class A_QSort {

    // отрезок
    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            // Корректируем, если концы пришли в обратном порядке
            if (start > stop) {
                this.start = stop;
                this.stop = start;
            } else {
                this.start = start;
                this.stop = stop;
            }
        }

        @Override
        public int compareTo(Segment o) {
            // Сначала сравниваем по началу, потом по концу
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            }
            return Integer.compare(this.stop, o.stop);
        }
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // число отрезков
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];

        // число точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        // читаем отрезки
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        // читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортируем отрезки по началу
        Arrays.sort(segments);

        // Для каждой точки считаем, скольким отрезкам она принадлежит
        for (int i = 0; i < m; i++) {
            int point = points[i];
            int count = 0;

            // Проходим по отсортированным отрезкам
            for (int j = 0; j < n; j++) {
                Segment segment = segments[j];

                // Если начало отрезка больше точки, дальше не проверяем
                if (segment.start > point) {
                    break;
                }

                // Проверяем, принадлежит ли точка отрезку
                if (point >= segment.start && point <= segment.stop) {
                    count++;
                }
            }

            result[i] = count;
        }

        scanner.close();
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result = instance.getAccessory(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}