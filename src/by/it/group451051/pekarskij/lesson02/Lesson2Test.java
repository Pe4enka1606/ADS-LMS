package by.it.group451051.pekarskij.lesson02;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Lesson2Test {

    @Test
    public void A_VideoRegistrator() {
        A_VideoRegistrator instance=new A_VideoRegistrator();
        double[] events=new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts=instance.calcStartTimes(events,1);
        boolean ok=starts.toString().equals("[1.0, 2.2, 3.7, 5.5, 8.1]");
        assertTrue("slowA failed", ok);

        // добавленные тесты

        double[] e1 = {5, 5, 5, 5};
        List<Double> s1 = instance.calcStartTimes(e1, 1);
        boolean ok2 = s1.toString().equals("[5.0]");
        assertTrue("A_VideoRegistrator same events failed", ok2);

        double[] e2 = {1, 3, 5, 7};
        List<Double> s2 = instance.calcStartTimes(e2, 1);
        boolean ok3 = s2.toString().equals("[1.0, 3.0, 5.0, 7.0]");
        assertTrue("A_VideoRegistrator no overlap failed", ok3);

        double[] e3 = new double[10000];
        for (int i = 0; i < 10000; i++) e3[i] = i * 0.1;
        List<Double> s3 = instance.calcStartTimes(e3, 1);
        boolean ok4 = s3.size() == 910;
        assertTrue("A_VideoRegistrator large input failed", ok4);
    }

    @Test
    public void B_Sheduler() {
        B_Sheduler instance = new B_Sheduler();
        B_Sheduler.Event[] events = {
                new B_Sheduler.Event(0, 3), new B_Sheduler.Event(0, 1), new B_Sheduler.Event(1, 2), new B_Sheduler.Event(3, 5),
                new B_Sheduler.Event(1, 3), new B_Sheduler.Event(1, 3), new B_Sheduler.Event(1, 3), new B_Sheduler.Event(3, 6),
                new B_Sheduler.Event(2, 7), new B_Sheduler.Event(2, 3), new B_Sheduler.Event(2, 7), new B_Sheduler.Event(7, 9),
                new B_Sheduler.Event(3, 5), new B_Sheduler.Event(2, 4), new B_Sheduler.Event(2, 3), new B_Sheduler.Event(3, 7),
                new B_Sheduler.Event(4, 5), new B_Sheduler.Event(6, 7), new B_Sheduler.Event(6, 9), new B_Sheduler.Event(7, 9),
                new B_Sheduler.Event(8, 9), new B_Sheduler.Event(4, 6), new B_Sheduler.Event(8, 10), new B_Sheduler.Event(7, 10)
        };

        List<B_Sheduler.Event> starts = instance.calcStartTimes(events, 0, 10);
        boolean ok=starts.toString().equals("[(0:1), (1:2), (2:3), (3:5), (6:7), (7:9)]");
        assertTrue("B_Sheduler failed", ok);

        // добавленные тесты

        B_Sheduler.Event[] e1 = {};
        List<B_Sheduler.Event> s1 = instance.calcStartTimes(e1, 0, 10);
        boolean ok2 = s1.isEmpty();
        assertTrue("B_Sheduler empty failed", ok2);

        B_Sheduler.Event[] e2 = {
                new B_Sheduler.Event(0, 10),
                new B_Sheduler.Event(1, 9),
                new B_Sheduler.Event(2, 8)
        };
        List<B_Sheduler.Event> s2 = instance.calcStartTimes(e2, 0, 10);
        boolean ok3 = s2.size() == 1;
        assertTrue("B_Sheduler all overlap failed", ok3);

        B_Sheduler.Event[] e3 = new B_Sheduler.Event[10000];
        for (int i = 0; i < 10000; i++) e3[i] = new B_Sheduler.Event(i, i + 1);
        List<B_Sheduler.Event> s3 = instance.calcStartTimes(e3, 0, 20000);
        boolean ok4 = s3.size() == 10000;
        assertTrue("B_Sheduler large input failed", ok4);
    }

    @Test
    public void C_GreedyKnapsack() throws Exception {
        String root=System.getProperty("user.dir")+"/src/";
        File f=new File(root+"by/it/group451051/pekarskij/lesson02/greedyKnapsack.txt");
        double costFinal=new C_GreedyKnapsack().calc(f);
        boolean ok=costFinal==200;
        assertTrue("C_GreedyKnapsack failed", ok);

        // добавленные тесты

        File f1 = File.createTempFile("knapsack_empty", ".txt");
        java.nio.file.Files.write(f1.toPath(), "0 10".getBytes());
        double r1 = new C_GreedyKnapsack().calc(f1);
        boolean ok2 = r1 == 0;
        assertTrue("C_GreedyKnapsack empty failed", ok2);

        File f2 = File.createTempFile("knapsack_one", ".txt");
        java.nio.file.Files.write(f2.toPath(), "1 50\n100 50".getBytes());
        double r2 = new C_GreedyKnapsack().calc(f2);
        boolean ok3 = r2 == 100;
        assertTrue("C_GreedyKnapsack one item failed", ok3);

        File f3 = File.createTempFile("knapsack_big", ".txt");
        StringBuilder sb = new StringBuilder("10000 10000\n");
        for (int i = 0; i < 10000; i++) sb.append("10 10\n");
        java.nio.file.Files.write(f3.toPath(), sb.toString().getBytes());
        double r3 = new C_GreedyKnapsack().calc(f3);
        boolean ok4 = r3 == 10000;
        assertTrue("C_GreedyKnapsack large input failed", ok4);
    }
}
