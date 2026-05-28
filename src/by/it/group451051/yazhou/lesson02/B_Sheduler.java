package by.it.group451051.yazhou.lesson02;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
/*
даны интервальные события events
реализуйте метод calcStartTimes, так, чтобы число принятых к выполнению
непересекающихся событий было максимально.
Алгоритм жадный. Для реализации обдумайте надежный шаг.
*/

public class B_Sheduler {
    //событие у аудитории(два поля: начало и конец)
    static class Event {
        int start;
        int stop;

        Event(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public String toString() {
            return "("+ start +":" + stop + ")";
        }
    }

    public static void main(String[] args) {
        B_Sheduler instance = new B_Sheduler();
        Event[] events = {  new Event(0, 3),  new Event(0, 1), new Event(1, 2), new Event(3, 5),
                new Event(1, 3),  new Event(1, 3), new Event(1, 3), new Event(3, 6),
                new Event(2, 7),  new Event(2, 3), new Event(2, 7), new Event(7, 9),
                new Event(3, 5),  new Event(2, 4), new Event(2, 3), new Event(3, 7),
                new Event(4, 5),  new Event(6, 7), new Event(6, 9), new Event(7, 9),
                new Event(8, 9),  new Event(4, 6), new Event(8, 10), new Event(7, 10)
        };

        List<Event> starts = instance.calcStartTimes(events,0,10);  //рассчитаем оптимальное заполнение аудитории
        System.out.println(starts);                                 //покажем рассчитанный график занятий
    }

    List<Event> calcStartTimes(Event[] events, int from, int to) {
        //events - события которые нужно распределить в аудитории
        //в период [from, int] (включительно).
        //оптимизация проводится по наибольшему числу непересекающихся событий.
        //начало и конец событий могут совпадать.
        List<Event> result;
        result = new ArrayList<>();
        // Фильтруем события, которые попадают в заданный диапазон [from, to]
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.start >= from && event.stop <= to) {
                filteredEvents.add(event);
            }
        }

        // Если нет подходящих событий, возвращаем пустой список
        if (filteredEvents.isEmpty()) {
            return result;
        }

        // Сортируем события по времени окончания (по возрастанию)
        filteredEvents.sort(Comparator.comparingInt(e -> e.stop));

        // Жадный алгоритм: выбираем события, которые заканчиваются раньше всего
        int lastEndTime = filteredEvents.get(0).stop;
        result.add(filteredEvents.get(0));

        for (int i = 1; i < filteredEvents.size(); i++) {
            Event currentEvent = filteredEvents.get(i);
            // Если текущее событие начинается после окончания последнего выбранного
            if (currentEvent.start >= lastEndTime) {
                result.add(currentEvent);
                lastEndTime = currentEvent.stop;
            }
        }

        return result;                        //вернем итог
    }
}