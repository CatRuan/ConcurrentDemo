package character6.Executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 测试ExecutorsService的生命周期方法
 * ExecutorsService 定义了生命周期方法
 */

public class ExecutorsServiceTest {

    private final static ExecutorService executorService = Executors.newCachedThreadPool();
    private static ArrayList<Callable<String>> callables = new ArrayList<>();
    private static ArrayList<Runnable> runnables = new ArrayList<>();

    static {
        for (int i = 0; i <= 10; i++) {
            callables.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    int millis = new Random().nextInt(8);
                    Thread.sleep(millis * 1000);
                    System.out.println("正在执行---" + Thread.currentThread().getName());
                    return Thread.currentThread().getName();
                }
            });
        }

    }

    public static void main(String[] args) {
//        testExecute();
//        testSubmit();
//        testShutdown();
        testShutdownNow();
//        testInvokeAll();
//        testInvokeAny();
    }

    /**
     * 测试invokeAll方法
     * 返回所有callable的结果，invokeAll结果是阻塞返回的
     */
    public static void testInvokeAll() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            List<Future<String>> futures = executorService.invokeAll(callables);
            System.out.println("------------");
            for (Future<String> future : futures) {
                System.out.println(future.get() + "执行完了");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 测试invokeAny方法
     * 返回结果是哪个任务是不确定
     */
    public static void testInvokeAny() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            String result = executorService.invokeAny(callables);
            System.out.println(result + "执行完了");
        } catch (Exception e) {
            e.printStackTrace();
        }


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


    /**
     * 测试submit
     */
    public static void testSubmit() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            // runnable 拿不到结果，但是get 方法阻塞执行
            Future<?> future1 = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("submit一个runnable  " + "---" + Thread.currentThread().getName());
                }
            });
            System.out.println("submit一个runnable返回值->" + future1.get() + "---" + Thread.currentThread().getName());

            // callable 拿的到结果，且get方法阻塞执行
            Future<?> future2 = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(1000);
                    System.out.println("submit一个callable  " + "---" + Thread.currentThread().getName());
                    return Thread.currentThread().getName() + "的结果";
                }
            });
            System.out.println("submit一个callable返回值->" + future2.get() + "---" + Thread.currentThread().getName());

            // FutureTask 拿不到结果，但是get 方法阻塞执行,所以FutureTask使用execute调用
            Future<?> future3 = executorService.submit(new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(1000);
                    System.out.println("submit一个FutureTask  " + "---" + Thread.currentThread().getName());
                    return Thread.currentThread().getName() + "的结果";
                }
            }));
            System.out.println("submit一个FutureTask返回值->" + future3.get() + "---" + Thread.currentThread().getName());

        } catch (Exception e) {

        }
    }

    /**
     * 测试execute
     */
    public static void testExecute() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            // runnable
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("execute一个runnable  " + "---" + Thread.currentThread().getName());
                }
            });

            // callable 拿的到结果，且get方法阻塞执行
//            executorService.execute(new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    Thread.sleep(1000);
//                    System.out.println("submit一个callable  " + "---" + Thread.currentThread().getName());
//                    return Thread.currentThread().getName() + "的结果";
//                }
//            });

            // FutureTask
            FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(1000);
                    System.out.println("execute一个FutureTask  " + "---" + Thread.currentThread().getName());
                    return Thread.currentThread().getName() + "的结果";
                }
            });
            executorService.execute(future);
            System.out.println("execute一个FutureTask返回值->" + future.get() + "---" + Thread.currentThread().getName());

        } catch (Exception e) {

        }
    }
}
