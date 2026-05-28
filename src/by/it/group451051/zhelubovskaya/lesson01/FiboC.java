package by.it.group451051.zhelubovskaya.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

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
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        if (n <= 1) return n % m;
    
        long prev = 0;
        long curr = 1;
        
        // Ищем период Пизано
        for (long i = 0; i < m * m; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
            
            // Если нашли начало периода
            if (prev == 0 && curr == 1) {
                long period = i + 1;
                n = n % period;
                break;
            }
        }
        
        // Вычисляем F(n) % m для уменьшенного n
        if (n <= 1) return n % m;
        
        prev = 0;
        curr = 1;
        for (long i = 2; i <= n; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
        }

        return curr%m;
    }


}

