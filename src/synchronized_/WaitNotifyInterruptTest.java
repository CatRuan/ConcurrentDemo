package synchronized_;

import java.util.LinkedList;
import java.util.List;

/**
 * 如果有两个线程，线程A和线程B，他们持有同一个锁对象
 * 当线程A持有的锁对象wait的时间已经到了，且线程B已经调用了notify方法，那么直到线程B执行结束，即线程A再次获得锁，它才会继续执行
 * 当线程A持有的锁对象处于wait状态，且线程B发起了中断线程A的请求，那么直到线程B执行结束，即线程A再次获得锁，才会进入catch。
 */
public class WaitNotifyInterruptTest {


    /**
     * 验证catch到中断异常时的现象
     */
    static class Wait2InterruptThread extends Thread {
        private Object lock;

        private Wait2InterruptThread(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println("Wait2InterruptThread：开始等待");
                    lock.wait();
                    System.out.println("Wait2InterruptThread：结束等待，go on……");
                } catch (InterruptedException e) {
                    System.out.println("Wait2InterruptThread：接收到中断异常");
                }
            }
        }
    }

    /**
     * 验证wait的时间到时的现象
     */
    static class Wait2TimeoutThread extends Thread {
        private Object lock;

        private Wait2TimeoutThread(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println("Wait2TimeoutThread：开始等待");
                    lock.wait(1000);
                    System.out.println("Wait2TimeoutThread：结束等待，go on……");
                } catch (InterruptedException e) {
                    System.out.println("Wait2TimeoutThread：接收到中断异常");
                }
            }
        }
    }

    static class InterruptWait2InterruptThread extends Thread {
        private Object lock;
        private Wait2InterruptThread waitThread;

        private InterruptWait2InterruptThread(Object lock, Wait2InterruptThread waitThread) {
            this.lock = lock;
            this.waitThread = waitThread;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println("InterruptWait2InterruptThread：发起中断请求");
                    waitThread.interrupt();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class InterruptWait2TimeoutThread extends Thread {
        private Object lock;

        private InterruptWait2TimeoutThread(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    Thread.sleep(3000);
                    System.out.println("InterruptWait2TimeoutThread：唤醒正在等待的线程");
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        testInterrupt();
//        testTimeOut();


    }

    private static void testInterrupt() {
        Object object = new Object();
        Wait2InterruptThread wait2InterruptThread = new Wait2InterruptThread(object);
        wait2InterruptThread.start();
        new InterruptWait2InterruptThread(object, wait2InterruptThread).start();
    }

    private static void testTimeOut() {
        Object object = new Object();
        new Wait2TimeoutThread(object).start();
        new InterruptWait2TimeoutThread(object).start();
    }

}























