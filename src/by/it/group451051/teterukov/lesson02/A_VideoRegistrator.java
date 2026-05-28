package by.it.group451051.teterukov.lesson02;

import java.util.ArrayList;
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
        List<Double> result= new ArrayList<>();
        //Arrays.sort() в Java — это мощный статический метод из класса java.util.Arrays ==>
        //==> который сортирует массивы примитивных типов (int, double, char и др.)==>
        //==> и объектов (Integer, String и др.) по возрастанию (по естественному порядку).
        //сортировка массива events
        java.util.Arrays.sort(events);
        //i - это индекс события events[i]

        //комментарии от проверочного решения сохранены для подсказки, но вы можете их удалить.
        //подготовка к жадному поглощению массива событий
        //hint: сортировка Arrays.sort обеспечит скорость алгоритма
        //C*(n log n) + C1*n = O(n log n)

        //пока есть незарегистрированные события
        //получим одно событие по левому краю
        //и запомним время старта видеокамеры
        //вычислим момент окончания работы видеокамеры
        //и теперь пропустим все покрываемые события
        //за время до конца работы, увеличивая индекс
        for(int i=0, n= events.length; i<n ;){
            //берём самое левое событие - это надёжный шаг
            //смотреть видео https://stepik.org/lesson/13238/step/9?unit=3424
            double start = events[i];
            result.add(start);
            double finish = start + workDuration;
            while (i < n && events[i] <= finish)
                i++;
        }

        return result;
    }
}
