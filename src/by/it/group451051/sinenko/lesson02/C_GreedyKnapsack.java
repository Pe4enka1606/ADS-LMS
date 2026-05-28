package by.it.group451051.sinenko.lesson02;
/*
Даны
1) объем рюкзака 4
2) число возможных предметов 60
3) сам набор предметов
    100 50
    120 30
    100 50
Все это указано в файле (by/it/a_khmelev/lesson02/greedyKnapsack.txt)

Необходимо собрать наиболее дорогой вариант рюкзака для этого объема
Предметы можно резать на кусочки (т.е. алгоритм будет жадным)
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<Item> {
        int cost;
        int weight;

        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    '}';
        }

        @Override
        public int compareTo(Item o) {
            //тут может быть ваш компаратор

            //находим самый выгодный предмет на кг веса
            double thisValue = (double) this.cost / this.weight; // вычисление стоимости на 1 кг для текущего объекта
            double otherValue = (double) o.cost / o.weight;      // вычисление стоимости на 1 кг для другого объекта (о обьекта)

            return Double.compare(otherValue, thisValue);    //сравнение и возврат результата
            // что больше по значению на 1кг, то и будет стоять раньше и с тем и пойдет работа
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      //сколько предметов в файле
        int W = input.nextInt();      //какой вес у рюкзака
        Item[] items = new Item[n];   //получим список предметов
        for (int i = 0; i < n; i++) { //создавая каждый конструктором
            items[i] = new Item(input.nextInt(), input.nextInt());
        }
        //покажем предметы
        for (Item item:items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n",n,W);

        //тут необходимо реализовать решение задачи
        //итогом является максимально воможная стоимость вещей в рюкзаке
        //вещи можно резать на кусочки (непрерывный рюкзак)
        double result = 0;
        //тут реализуйте алгоритм сбора рюкзака
        //будет особенно хорошо, если с собственной сортировкой
        //кроме того, можете описать свой компаратор в классе Item
        //ваше решение.

        Arrays.sort(items);  // сортировка items 

        int remaining = W;  // переменная для оставшегося веса в рюкзаке
    
        for (Item item : items) {      // цикл идет по предметам от самого выгодного к менее выгодному
            if (remaining <= 0) break; // если не осталось места то стоп
        
            if (item.weight <= remaining) {      // если вес предмета не больше оставшегося места
                result += item.cost;             // то добавляем его стоимость
                remaining -= item.weight;        // и уменьшаем оставшееся место

            } else {            // если целый предмет не вмещается, то дробим его
                double fraction = (double) remaining / item.weight; // вычисление какая часть предмета помещается
                result += item.cost * fraction;    //прибавляем к итогу пропорциональную стоимость
                remaining = 0;                    // в конце рюкзак полон 
        }
    }

        System.out.printf("Удалось собрать рюкзак на сумму %f\n",result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root=System.getProperty("user.dir")+"/src/";
        File f=new File(root+"by/it/group451051/sinenko/lesson02/greedyKnapsack.txt");
        double costFinal=new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)",costFinal,finishTime - startTime);
    }
}