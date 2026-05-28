package by.it.group451051.belousova.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class B_Sheduler {
    static class Event {
        int start;
        int stop;

        Event(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public String toString() {
            return "(" + start + ":" + stop + ")";
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

        List<Event> starts = instance.calcStartTimes(events, 0, 10);
        System.out.println(starts);
    }

    List<Event> calcStartTimes(Event[] events, int from, int to) {
        List<Event> result = new ArrayList<>();

        // 1. Фильтруем события, которые полностью находятся в заданном интервале [from, to]
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.start >= from && event.stop <= to) {
                filteredEvents.add(event);
            }
        }

        // 2. Сортируем события по времени окончания (по возрастанию)
        filteredEvents.sort(new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                // Сначала по времени окончания
                if (e1.stop != e2.stop) {
                    return Integer.compare(e1.stop, e2.stop);
                }
                // При равном времени окончания сортируем по времени начала
                return Integer.compare(e1.start, e2.start);
            }
        });

        // 3. Жадный алгоритм выбора событий
        if (!filteredEvents.isEmpty()) {
            // Берем первое событие (с самым ранним окончанием)
            Event lastSelected = filteredEvents.get(0);
            result.add(lastSelected);

            // Проходим по остальным событиям
            for (int i = 1; i < filteredEvents.size(); i++) {
                Event current = filteredEvents.get(i);

                // Если текущее событие начинается после окончания последнего выбранного
                if (current.start >= lastSelected.stop) {
                    result.add(current);
                    lastSelected = current;
                }
            }
        }

        return result;
    }
}