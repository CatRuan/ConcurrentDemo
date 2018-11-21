package character6.Executor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

/**
 * newCachedThreadPool
 * 线程池规模不受控制，如果当前需求少于当前规模将回收空闲线程，大于时则新创需求
 * 使用场景：执行很多短期异步的小程序或者负载较轻的服务器
 * 返回值ExecutorService
 */
public class NewCachedPool {
    private static ArrayList<Callable<String>> callables = new ArrayList<>();

    static {
        for (int i = 0; i <= 10; i++) {
            callables.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    int millis = new Random().nextInt(10);
                    Thread.sleep(millis * 1000);
                    System.out.println("正在执行---" + Thread.currentThread().getName());
                    return Thread.currentThread().getName();
                }
            });
        }

    }

    public static void main(String[] args) {
        try {
            ExecutorService executor = Executors.newCachedThreadPool();
            for (int i = 0; i <= 10; i++) {
                executor.submit(callables.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
