package by.it.group451051.pekarskij.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A_VideoRegistrator {

    public static void main(String[] args)  {
        A_VideoRegistrator instance=new A_VideoRegistrator();
        double[] events=new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts=instance.calcStartTimes(events,1); //рассчитаем моменты старта, с длинной сеанса 1
        System.out.println(starts);                            //покажем моменты старта
    }
    List<Double> calcStartTimes(double[] events, double workDuration)  {
    // список для моментов включения регистратора
    List<Double> result = new ArrayList<>();

    // сортируем события слева направо
    Arrays.sort(events);

    int i = 0;

    // жадно выбираем минимальное количество включений
    while (i < events.length) {
        // фиксируем старт по самому раннему непокрытому событию
        double start = events[i];
        result.add(start);

        // вычисляем момент окончания работы регистратора
        double end = start + workDuration;

        // пропускаем все события, которые попадают в интервал [start, end]
        while (i < events.length && events[i] <= end) {
            i++;
        }
    }

    return result;
}

}
