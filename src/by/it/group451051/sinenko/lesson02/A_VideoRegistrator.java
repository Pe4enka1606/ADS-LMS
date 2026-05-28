package by.it.group451051.sinenko.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
даны события events
реализуйте метод calcStartTimes, так, чтобы число включений регистратора на
заданный период времени (1) было минимальным, а все события events
были зарегистрированы.
Алгоритм жадный. Для реализации обдумайте надежный шаг.
*/

public class A_VideoRegistrator {

    public static void main(String[] args)  {
        A_VideoRegistrator instance=new A_VideoRegistrator();
        double[] events=new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts=instance.calcStartTimes(events,1); //рассчитаем моменты старта, с длинной сеанса 1
        System.out.println(starts);                            //покажем моменты старта
    }
    //модификаторы доступа опущены для возможности тестирования
    List<Double> calcStartTimes(double[] events, double workDuration)  {
        //events - события которые нужно зарегистрировать
        //timeWorkDuration время работы видеокамеры после старта

        // Решение
        Arrays.sort(events);  // 1.сортировка списка
        List<Double> result;
        result = new ArrayList<>();
        int i=0;                              //i - это индекс события events[i]

        while (i < events.length) {        //2. Цикл работает до конца событий
            double start = events[i];      //3. События обозначаем как начало отсчета работы камеры
            result.add(start);             //4. Добавляем переменную в результат
            double end = start + workDuration;   // 5. Переменная когда камера закончит работать
            i++;                                 // 6. Переход к следующему events
            
            while (i < events.length && events[i] <= end) { // 6. Пропуск покрытых камерой events
                i++; // собственно пропсук события
            }
        }
        return result;                        //вернем итог
    }
}         
// Выводит такой результат [1.0, 2.2, 3.7, 5.5, 8.1]