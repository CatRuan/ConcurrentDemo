package character6.Executor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newSingleThreadExecutor
 * 是一个单线程的的Executor，如果某个线程异常结束，会创建另一个线程代替
 * 使用场景：任务需要串行执行的场景
 * 返回值ExecutorService
 */
public class NewSingleThreadPool {
    private static ArrayList<Callable<String>> callables = new ArrayList<>();

    static {
        for (int i = 0; i <= 3; i++) {
            callables.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
//                    int millis = new Random().nextInt(8);
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "-正在执行任务：" + "【" + System.currentTimeMillis() + "】");
                    return Thread.currentThread().getName();
                }
            });
        }

    }

    public static void main(String[] args) {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(new Runnable() {
                @Override
                public void run (){
                    System.out.println(Thread.currentThread().getName() + "-正在执行任务：" + "【产生异常的任务】");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String[] array = new String[10];
                    String a = array[10] ;
                }
            });
            for (int i = 0; i <= 3; i++) {
                executor.submit(callables.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
