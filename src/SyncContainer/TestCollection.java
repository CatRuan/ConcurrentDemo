package SyncContainer;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步容器 Collections.synchronizedList
 * 同步容器类 实现同步的方式是，对每个共有方法进行同步，使得每次只有一个线程能访问容器的状态 缺点：串行访问容器状态，效率低 同样会出现线程不安全的情况
 * @author ruand
 *
 */
public class TestCollection {

	public static void main(String[] args) {
		try {
			testGet();
			testAdd();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int NUM = 100000;  
    private static int THREAD_COUNT = 16;  
  
   
    public static void testAdd() throws Exception {  
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = Collections.synchronizedList(new ArrayList<Integer>());

        CountDownLatch add_countDownLatch = new CountDownLatch(THREAD_COUNT);  
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);  
  
        int add_copyCostTime = 0;  
        int add_synchCostTime = 0;  
        for (int i = 0; i < THREAD_COUNT; i++) {  
            add_copyCostTime += executor.submit(new AddTestTask(list1, add_countDownLatch)).get();  
        }  
        for (int i = 0; i < THREAD_COUNT; i++) {  
            add_synchCostTime += executor.submit(new AddTestTask(list2, add_countDownLatch)).get();
        } 
        add_countDownLatch.await();
        System.out.println("add:ArrayList " + add_copyCostTime);  
        System.out.println("add:Collections.synchronizedList " + add_synchCostTime);  
    }  
  
   
    public static void testGet() throws Exception {  
        List<Integer> list = initList();  
  
        List<Integer> list1 = initList(); 
        List<Integer> list2 = Collections.synchronizedList(list);  
  
        int get_copyCostTime = 0;  
        int get_synchCostTime = 0;  
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);  
        CountDownLatch get_countDownLatch = new CountDownLatch(THREAD_COUNT);  
        for (int i = 0; i < THREAD_COUNT; i++) {  
            get_copyCostTime += executor.submit(new GetTestTask(list1, get_countDownLatch)).get();  
        }  
        for (int i = 0; i < THREAD_COUNT; i++) {  
            get_synchCostTime += executor.submit(new GetTestTask(list2, get_countDownLatch)).get();  
        }  
        get_countDownLatch.await();
        System.out.println("get:ArrayList  " + get_copyCostTime); 
        System.out.println("get:Collections.synchronizedList " + get_synchCostTime);  
  
    }  
  
  
    private static List<Integer> initList() {  
        List<Integer> list = new ArrayList<Integer>();  
        int num = new Random().nextInt(1000);  
        for (int i = 0; i < NUM; i++) {  
            list.add(num);  
        }  
        return list;  
    }  
  
    static class AddTestTask implements Callable<Integer> {  
        List<Integer> list;  
        CountDownLatch countDownLatch;  
  
        AddTestTask(List<Integer> list, CountDownLatch countDownLatch) {  
            this.list = list;  
            this.countDownLatch = countDownLatch;  
        }  
  
        @Override  
        public Integer call() throws Exception {  
            int num = new Random().nextInt(1000);  
            long start = System.currentTimeMillis();  
            for (int i = 0; i < NUM; i++) {  
                list.add(num);  
            }  
            long end = System.currentTimeMillis();  
            countDownLatch.countDown();  
            return (int) (end - start);  
        }  
    }  
  
    static class GetTestTask implements Callable<Integer> {  
        List<Integer> list;  
        CountDownLatch countDownLatch;  
  
        GetTestTask(List<Integer> list, CountDownLatch countDownLatch) {  
            this.list = list;  
            this.countDownLatch = countDownLatch;  
        }  
  
        @Override  
        public Integer call() throws Exception {  
            int pos = new Random().nextInt(NUM);  
            long start = System.currentTimeMillis();  
            for (int i = 0; i < NUM; i++) {  
                list.get(pos);  
            }  
            long end = System.currentTimeMillis();  
            countDownLatch.countDown();  
            return (int) (end - start);  
        }  
    }  

}
