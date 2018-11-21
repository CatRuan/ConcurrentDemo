package character6;

import java.util.concurrent.*;

/**
 *Callable 可以返回值也可以抛出异常
 * Future 表示一个任务的生命周期
 */
public class FutureTest {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        // 获取future的第一种方式
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "task hello";
            }
        };
        Future<String> future  = executorService.submit(callable);
        try {
            String result = future.get();
            System.out.println("1得到结果了：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 获取future的第二种方式
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "task world";
            }
        });
        //executorService.submit(futureTask);
        futureTask.run();
        try {
            String result = futureTask.get();
            System.out.println("2得到结果了：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
