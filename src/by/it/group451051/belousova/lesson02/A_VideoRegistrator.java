package by.it.group451051.belousova.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A_VideoRegistrator {

    public static void main(String[] args) {
        A_VideoRegistrator instance = new A_VideoRegistrator();
        double[] events = new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts = instance.calcStartTimes(events, 1);
        System.out.println(starts);
    }

    List<Double> calcStartTimes(double[] events, double workDuration) {
        List<Double> result = new ArrayList<>();

        // 1. Сортируем события по возрастанию времени
        Arrays.sort(events);

        int i = 0; // индекс текущего события
        int n = events.length;

        // 2. Пока есть незарегистрированные события
        while (i < n) {
            // 3. Берем самое раннее незарегистрированное событие
            double startTime = events[i];
            result.add(startTime);

            // 4. Вычисляем время окончания работы регистратора
            double endTime = startTime + workDuration;

            // 5. Пропускаем все события, которые попадают в интервал [startTime, endTime]
            while (i < n && events[i] <= endTime) {
                i++;
            }
        }

        return result;
    }
}
