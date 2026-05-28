package by.it.group451051.belousova.lesson01;

public class FiboC {
    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        // Находим период Пизано для модуля m
        long pisanoPeriod = getPisanoPeriod(m);

        // Находим эквивалентное n в пределах периода
        long equivalentN = n % pisanoPeriod;

        // Вычисляем число Фибоначчи по модулю m для equivalentN
        return fibMod(equivalentN, m);
    }

    // Метод для нахождения периода Пизано
    private long getPisanoPeriod(int m) {
        if (m == 1) return 1;

        long a = 0, b = 1, c;
        // Период Пизано не превышает 6*m
        for (int i = 0; i < 6 * m; i++) {
            c = (a + b) % m;
            a = b;
            b = c;
            // Период начинается с 0,1
            if (a == 0 && b == 1) {
                return i + 1;
            }
        }
        return 6L * m; // На всякий случай возвращаем верхнюю границу
    }

    // Метод для вычисления n-го числа Фибоначчи по модулю m
    private long fibMod(long n, int m) {
        if (n == 0) return 0;
        if (n == 1) return 1 % m;

        long a = 0, b = 1, c = 0;
        for (long i = 2; i <= n; i++) {
            c = (a + b) % m;
            a = b;
            b = c;
        }
        return c;
    }
}