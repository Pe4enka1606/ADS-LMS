package by.it.group451051.pekarskij.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_LongDivComSubSeq {

    int getDivSeqSize(InputStream stream) throws FileNotFoundException {
    Scanner scanner = new Scanner(stream);

    // читаем длину последовательности
    int n = scanner.nextInt();
    int[] m = new int[n];

    // читаем массив
    for (int i = 0; i < n; i++) {
        m[i] = scanner.nextInt();
    }

    // dp[i] — длина максимальной кратной подпоследовательности,
    // заканчивающейся в позиции i
    int[] dp = new int[n];

    int result = 1;

    // базовый случай: каждая позиция — подпоследовательность длины 1
    for (int i = 0; i < n; i++) {
        dp[i] = 1;

        // ищем предыдущие элементы, на которые m[i] делится
        for (int j = 0; j < i; j++) {
            if (m[i] % m[j] == 0) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }

        // обновляем глобальный максимум
        if (dp[i] > result) {
            result = dp[i];
        }
    }

    return result;
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson06/dataB.txt");
        B_LongDivComSubSeq instance = new B_LongDivComSubSeq();
        int result = instance.getDivSeqSize(stream);
        System.out.print(result);
    }

}