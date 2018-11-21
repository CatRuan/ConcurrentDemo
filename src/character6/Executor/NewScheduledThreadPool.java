package character6.Executor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;

/**
 * newScheduledThreadPool
 * 创建固定长度的线程池，并按照固定延迟/时间间隔执行任务
 * 使用场景：周期性任务
 * 返回值ScheduledExecutorService
 */
public class NewScheduledThreadPool {
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
//        testSchedule();
        testScheduleAtFixedRate();
//        testScheduleWithFixedDelay();
    }

    /**
     * 延迟1s执行任务
     */
    public static void testSchedule() {
        try {
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
            System.out.println("提交任务时间：" + getCurrentTime());
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("开始执行任务时间：" + getCurrentTime());
                }
            }, 2, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 延迟1s,并每隔2s执行任务，下一个任务不必等上一个任务执行完成
     */
    public static void testScheduleAtFixedRate() {
        try {
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
            System.out.println("提交任务时间：" + getCurrentTime());

            executor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "-执行任务时间：" + getCurrentTime());
                }
            }, 1, 2, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 延迟1s,并延迟2两秒执行下个任务，下一个任务必须等上一个任务执行完成
     */
    public static void testScheduleWithFixedDelay() {
        try {
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
            System.out.println("提交任务时间：" + getCurrentTime());
            executor.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "-执行任务时间：" + getCurrentTime());
                }
            }, 1, 2, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }
}
