package FutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

/**
 * FutureTask是一个runnable,使用FutureTask.get获取任务状态，如果任务已经完成，那么get会立即返回结果
 * ，否则get将阻塞至任务进入完成状态，然后返回结果或者抛出异常。
 * 以进入游戏前必须等待更新游戏为例
 * @author ruand
 *
 */
public class Test {
    private static FutureTask<Boolean> gameUpdateTask = new FutureTask<>(new Callable<Boolean>() {

		@Override
		public Boolean call() throws Exception {
			System.out.println("检测到游戏需要更新，此过程比较漫长请耐心等待");
			int i = 5;
			while (i>0) {
				Thread.sleep(2000);
				System.out.println("已更新：" + 20 * (5-(i-1)) + "%");
				i--;
			}

			return true;
		}
	});
    
    private static Thread gameThread = new Thread(gameUpdateTask);

	public static void main(String[] args) {
		System.out.println("启动游戏");
		gameThread.start();
		try {
			boolean initSuccess = gameUpdateTask.get();
			if (initSuccess) {
				System.out.println("更新完毕");
			} else {
				System.out.println("更新失败");
			}		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("再次启动游戏");
		try {
			boolean initSuccess = gameUpdateTask.get();
			if (initSuccess) {
				System.out.println("无更新任务，进入游戏");
				return;
			}
			System.out.println("更新失败");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
