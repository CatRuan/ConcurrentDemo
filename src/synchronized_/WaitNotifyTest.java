package synchronized_;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 1、wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写。
 * <p>
 * 2、wait()使当前线程阻塞，前提是 必须先获得锁，一般配合synchronized 关键字使用，即，一般在synchronized 同步代码块里使用 wait()、notify/notifyAll() 方法。
 * <p>
 * 3、 由于 wait()、notify/notifyAll() 在synchronized 代码块执行，说明当前线程一定是获取了锁的。
 * 当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
 * 只有当 notify/notifyAll() 被执行时候，才会唤醒一个或多个正处于等待状态的线程，然后继续往下执行，直到执行完synchronized 代码块的代码或是中途遇到wait() ，再次释放锁。
 * 也就是说，notify/notifyAll() 的执行只是唤醒沉睡的线程，而不会立即释放锁，锁的释放要看代码块的具体执行情况。
 * 所以在编程中，尽量在使用了notify/notifyAll() 后立即退出临界区，以唤醒其他线程
 * <p>
 * 4、wait() 需要被try catch包围，中断也可以使wait等待的线程唤醒。
 * <p>
 * 5、notify 和wait 的顺序不能错，如果A线程先执行notify方法，B线程在执行wait方法，那么B线程是无法被唤醒的。
 * <p>
 * 6、notify 和 notifyAll的区别
 * notify方法只唤醒一个等待（对象的）线程并使该线程开始执行。所以如果有多个线程等待一个对象，这个方法只会唤醒其中一个线程，选择哪个线程取决于操作系统对多线程管理的实现。
 * notifyAll 会唤醒所有等待(对象的)线程，尽管哪一个线程将会第一个处理取决于操作系统的实现。如果当前情况下有多个线程需要被唤醒，
 * 推荐使用notifyAll 方法。比如在生产者-消费者里面的使用，每次都需要唤醒所有的消费者或是生产者，以判断程序是否可以继续往下执行。
 * <p>
 * 7、在多线程中要测试某个条件的变化，使用if 还是while？
 * 要注意，notify唤醒沉睡的线程后，线程会接着上次的执行继续往下执行。
 * 所以在进行条件判断时候，可以先把 wait 语句忽略不计来进行考虑，
 * 显然，要确保程序一定要执行，并且要保证程序直到满足一定的条件再执行，要使用while来执行，以确保条件满足和一定执行。如下代码：
 */
public class WaitNotifyTest {

    // 以消费者生产者模式演示notify的使用
    static class Factory {
        private static final int MAX_NUM = 10;//最大生产数量
        private List storage = new LinkedList();

        public Factory() {
        }

        public void produce(int num) {
            synchronized (storage) {
                while (storage.size() + num > MAX_NUM) {
                    System.out.println(Thread.currentThread().getName() + " 库存数量：" + storage.size() + "  等待生产数量：" + num + "，暂不生产");
                    try {
                        storage.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("等待生产中断");
                    }
                }
                for (int i = 0; i < num; i++) {
                    storage.add(new Object());
                }
                System.out.println(Thread.currentThread().getName() + "库存数量：" + storage.size() + "  生产数量：" + num);
                storage.notifyAll();
            }

        }


        public void custom(int num) {
            synchronized (storage) {


                while (num > storage.size()) {
                    System.out.println(Thread.currentThread().getName() + "库存数量：" + storage.size() + "  待消费数量：" + num + "，等待消费");
                    try {
                        storage.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("等待消费中断");
                    }
                }
                for (int i = 0; i < num; i++) {
                    storage.remove(storage.size() - 1);
                }
                System.out.println(Thread.currentThread().getName() + "库存数量：" + storage.size() + "  消费数量：" + num);
                storage.notifyAll();
            }

        }
    }

    static class Producer extends Thread {
        Factory factory;
        int num;

        Producer(Factory factory) {
            this.factory = factory;
        }

        Producer produce(int num) {
            this.num = num;
            return this;
        }

        @Override
        public void run() {
            factory.produce(num);
        }
    }

    static class Costomer extends Thread {
        Factory factory;
        int num;

        Costomer(Factory factory) {
            this.factory = factory;
        }

        Costomer custom(int num) {
            this.num = num;
            return this;
        }

        @Override
        public void run() {
            factory.custom(num);
        }
    }

    public static void main(String[] args) {
        Factory factory = new Factory();
        new Producer(factory).produce(3).start();
        new Producer(factory).produce(7).start();
        new Producer(factory).produce(5).start();
        new Producer(factory).produce(4).start();
        new Producer(factory).produce(4).start();

        new Costomer(factory).custom(2).start();
        new Costomer(factory).custom(2).start();
        new Costomer(factory).custom(3).start();
        new Costomer(factory).custom(6).start();
        new Costomer(factory).custom(5).start();

    }

}























