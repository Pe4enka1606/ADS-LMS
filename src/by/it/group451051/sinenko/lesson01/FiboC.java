package by.it.group451051.sinenko.lesson01;

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

        int period = findPisanoPeriod(m); // находим сколько чисел в цикле для модуля m
    
        long reducedN = n % period;  // уменьшаем n по модулю периода
        
        return fibonacciModul(reducedN, m); // вычисляем Fibo (reducedN) % m
    }
    
    private int findPisanoPeriod(int m) { // находит длину периода
        if (m == 1) return 1;     // базовый случай
        
        int prev = 0;    // первые числа фибоначчи по модулю m 
        int curr = 1;
        
        for (int i = 0; i < m * 6; i++) {   
            int next = (prev + curr) % m;   // вычисление следующего остатка
            prev = curr;   // сдвиг значений для следующей итерации
            curr = next;
            
            if (prev == 0 && curr == 1) {   // проверка на повторение периода
                return i + 1;
            }
        }
        return 0;
    }
    
    private long fibonacciModul(long n, int m) {    // вычисляет остаток числа 
        if (n == 0) return 0;   // базовые случаи
        if (n == 1) return 1;
        
        long prev = 0;      // первые числа фибоначчи по модулю m 
        long curr = 1;
        
        for (long i = 2; i <= n; i++) {
            long next = (prev + curr) % m;    // вычисление следующего числа
            prev = curr;           // сдвиг значений для следующей итерации
            curr = next;
        }
        
        return curr;
    }
}