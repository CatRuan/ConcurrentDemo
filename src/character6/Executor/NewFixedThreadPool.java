package character6.Executor;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newSingleThreadExecutor
 * 固定长度的线程池，每提交一个任务就创建一个新的线程，直到达到最大线程数
 * 返回值ExecutorService
 */
public class NewFixedThreadPool {
    private static ArrayList<Callable<String>> callables = new ArrayList<>();

    static {
        for (int i = 0; i <= 10; i++) {
            callables.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
//                    int millis = new Random().nextInt(8);
                    Thread.sleep( 2000);
                    System.out.println(Thread.currentThread().getName() + "-正在执行任务：" + "【" + System.currentTimeMillis() + "】");
                    return Thread.currentThread().getName();
                }
            });
        }

    }

    public static void main(String[] args) {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(3);
            for (int i = 0; i <= 10; i++) {
                executor.submit(callables.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
