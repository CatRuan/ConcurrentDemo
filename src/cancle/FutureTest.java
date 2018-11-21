package cancle;

import java.util.concurrent.*;

public class FutureTest {

    public static void main(String[] args) {
        ScheduledExecutorService excutor = Executors.newScheduledThreadPool(1);
        Future<?> task = excutor.schedule(new Callable<String>() {

            @Override
            public String call() throws Exception {
                try {
                    System.out.println("do something");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "返回的结果";
            }
        },1, TimeUnit.SECONDS);
        try {
            String result = (String) task.get();
            System.out.println("收到结果：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            //
            System.out.println("取消任务");
            task.cancel(true);//如果任务正在进行，那么将被中断
        }
    }
}
