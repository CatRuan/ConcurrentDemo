package cancle;

import sun.security.provider.NativePRNG;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 在阻塞的方法中使用中断策略不当导致的死锁
 * 这种用标志位作为中断方式的方法，可用使用线程的中断机制替代，如BlockingTest2
 */
public class BlockingTest1 {

    public static void main(String[] args) {
        BlockingTask task = new BlockingTask();
        task.start();
        try {
            System.out.println("准备take--------");
            task.getANumber();
            System.out.println("take完毕--------");
            Thread.sleep(2000);
            System.out.println("2秒后，取消操作");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            task.cancel();
            System.out.println("已经调用取消方法");
        }
    }

    static class BlockingTask extends Thread {
        private final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        private volatile boolean cancelled = false;

        public void cancel() {
            cancelled = true;
        }

        public int getANumber() throws InterruptedException {
            return queue.take();
        }

        @Override
        public void run() {
           while (!cancelled){
               try {
                   System.out.println("准备put>>>>>>>>>");
                   queue.put(1);
                   System.out.println("put完毕>>>>>>>>>");
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           System.out.println("任务已经结束了");
        }
    }
}
