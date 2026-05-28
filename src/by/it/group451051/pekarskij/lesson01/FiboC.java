package by.it.group451051.pekarskij.lesson01;

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
    if (n == 0) return 0;

    int[] pisano = new int[m * 6 + 3];
    pisano[0] = 0;
    pisano[1] = 1;

    int period = 0;
    for (int i = 2; i < pisano.length; i++) {
        pisano[i] = (pisano[i - 1] + pisano[i - 2]) % m;
        if (pisano[i] == 1 && pisano[i - 1] == 0) {
            period = i - 1;
            break;
        }
    }

    long index = n % period;
    return pisano[(int) index];
}
}


