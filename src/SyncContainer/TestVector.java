package SyncContainer;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步容器 Vector 同步容器类 实现同步的方式是，对每个共有方法进行同步，使得每次只有一个线程能访问容器的状态
 * 同步容器都是线程安全的，但是对于复合操作，缺有些缺点：
 * ①迭代：在查觉到容器在迭代开始以后被修改，会抛出一个未检查异常ConcurrentModificationException，为了避免这个异常，需要在迭代期间，持有一个容器锁。但是锁的缺点也很明显，就是对性能的影响。
 * ②隐藏迭代器：StringBuilder的toString方法会通过迭代容器中的每个元素，另外容器的hashCode和equals方法也会间接地调用迭代。类似地，contailAll、removeAll、retainAll方法，以及容器作为参数的构造函数，都会对容器进行迭代。
 * ③缺少即加入等一些复合操作
 *
 * 例子展示复合操作导致的线程不安全
 * @author ruand
 *
 */
public class TestVector {
	static Vector<Integer> vector = new Vector<>();
	static {
		for (int i = 0; i < 10; i++) {
			vector.add(i);
		}
	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				System.out.println("vector111->" + vector);
				getLast(vector);
			}
		});
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				System.out.println("vector222->" + vector);
				removeLast(vector);
			}
		});
	}

	public static Integer getLast(Vector<Integer> vector) {
		int lastIndex = vector.size() - 1;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vector.get(lastIndex);
	}

	public static void removeLast(Vector<Integer> vector) {
		int lastIndex = vector.size() - 1;
		vector.remove(lastIndex);
	}

}
