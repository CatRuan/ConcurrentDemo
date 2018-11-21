package SyncContainer;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 同步容器Hashtable
 * 同步容器类 实现同步的方式是，对每个共有方法进行同步，使得每次只有一个线程能访问容器的状态 缺点：串行访问容器状态，效率低 同样会出现线程不安全的情况
 * 本例子展示迭代导致的线程不安全
 * @author ruand
 *
 */
public class TestHashTable {
	static Hashtable<Integer, String> hashtable = new Hashtable<>();
	static {
		for (int i = 0; i < 10; i++) {
			hashtable.put(i, "hahaha");
		}
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new Runnable() {

			@Override
			public void run() {
				System.out.println("hashtable111->" + hashtable);
				synchronized (hashtable) {
					Iterator<Integer> iterable = hashtable.keySet().iterator();
					while (iterable.hasNext()) {
						doSomething(hashtable.get(iterable.next()));
					}
				}
			}
		});
		executor.execute(new Runnable() {

			@Override
			public void run() {
				System.out.println("hashtable222->" + hashtable);
				synchronized (hashtable) {
					Iterator<Integer> iterable = hashtable.keySet().iterator();
					while (iterable.hasNext()) {
						hashtable.remove(iterable.next());
					}
				}
			}
		});

	}

	static int i = 0;

	static void doSomething(String str) {
		System.out.println((++i) + ":" + str);
	}

}
