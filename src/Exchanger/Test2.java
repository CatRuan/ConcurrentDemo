package Exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test2 {
	public static void main(String[] args) {  
        ExecutorService executor = Executors.newCachedThreadPool();  
          
        final Exchanger exchanger = new Exchanger();  
        executor.execute(new Runnable() {  
            String data1 = "A";  
              
  
            @Override  
            public void run() { 
            	Thread.currentThread().setName("客户1");
                nbaTrade(data1, exchanger);  
            }  
        });  
          
  
        executor.execute(new Runnable() {  
            String data1 = "B";  
  
            @Override  
            public void run() {
            	Thread.currentThread().setName("客户2");
                nbaTrade(data1, exchanger);  
            }  
        });  
          
        executor.execute(new Runnable() {  
            String data1 = "C";  
  
            @Override  
            public void run() {  
            	Thread.currentThread().setName("客户3");
                nbaTrade(data1, exchanger);  
            }  
        });  
          
        executor.execute(new Runnable() {  
            String data1 = "D";  
  
            @Override  
            public void run() {  
            	Thread.currentThread().setName("客户4");
                nbaTrade(data1, exchanger);  
            }  
        });  
          
        executor.shutdown();  
    }  
  
    private static void nbaTrade(String data1, Exchanger exchanger) {  
        try {  
            System.out.println(Thread.currentThread().getName() + "在交易截止之前把 " + data1 + " 交易出去");  
            Thread.sleep((long) (Math.random() * 1000));  
  
            String data2 = (String) exchanger.exchange(data1);  
            System.out.println(Thread.currentThread().getName() + "交易得到" + data2);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}
