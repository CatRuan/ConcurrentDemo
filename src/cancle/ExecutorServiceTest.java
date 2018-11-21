package cancle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorServiceTest {


    private final static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
//        testShutdown();
        testShutdownNow();
    }

    /**
     * shutdown() 方法调用以后，不接受新任务，不结束正在执行的任务，会执行等待执行的任务，不等待任务完成。
     * 只是不再接受任务了，已经接受的爱咋咋的
     */
    public static void testShutdown() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("调用shutdown前提交的线程---running " + Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.shutdown();
        System.out.println("调用isShutdown111：" + executorService.isShutdown() + "  " + Thread.currentThread().getName());
        System.out.println("调用isTerminated111：" + executorService.isTerminated() + "  " + Thread.currentThread().getName());
        // 调用shutdown方法以后，再submit任务，会抛异常RejectedExecutionException
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("调用shutdown后---" + Thread.currentThread().getName());
//                int millis = new Random().nextInt(3);
//                try {
//                    Thread.sleep(millis * 1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        try {
            boolean result = executorService.awaitTermination(1000, TimeUnit.SECONDS);
            System.out.println("调用shutdown后,awaitTermination被调用--任务终于结束了" + Thread.currentThread().getName());
            System.out.println("调用isShutdown22，isShutdown：" + executorService.isShutdown() + "  " + Thread.currentThread().getName());
            System.out.println("调用isTerminated222：" + executorService.isTerminated() + "  " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * shutdown() 方法调用以后，不接受新任务，尝试结束正在执行的的任务，不执行等待执行的任务，不等待任务完成。
     * 不接受新任务了，已经接受的也想方设法结束掉
     */
    public static void testShutdownNow() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // 因为程序被shutdown，无法继续执行 ，抛出异常InterruptedException
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("调用shutdownNow前提交的线程---running " + Thread.currentThread().getName());
            }
        });
        executorService.shutdownNow();
        System.out.println("调用isShutdown111：" + executorService.isShutdown() + "  " + Thread.currentThread().getName());
        System.out.println("调用isTerminated111：" + executorService.isTerminated() + "  " + Thread.currentThread().getName());
        // 调用shutdownNow方法以后，再submit任务，会抛异常RejectedExecutionException
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                int millis = new Random().nextInt(3);
//                try {
//                    Thread.sleep(millis * 1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("调用shutdownNow后---" + Thread.currentThread().getName());
//            }
//        });
        try {
            boolean result = executorService.awaitTermination(1000, TimeUnit.SECONDS);
            System.out.println("调用shutdownNow后,awaitTermination被调用--任务终于结束了" + Thread.currentThread().getName());
            System.out.println("调用isShutdown222：" + executorService.isShutdown() + "  " + Thread.currentThread().getName());
            System.out.println("调用isTerminated222：" + executorService.isTerminated() + "  " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
