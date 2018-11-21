package Semaphore;

/**
 * 信号量 管理着一组许可，执行操作时可以先获取许可，执行后可释放许可。如果没有许可，那么acquire（）将阻塞到获取一个许可
 * release将返回一个许可给信号量
 * @author ruand
 *
 */
public class Test {

	
	public static void main(String[] args){
	  Restaurant restaurant = new Restaurant(5);
	  int customerNum = 20;
	  Customer customer = null;
      for (int i = 0; i < customerNum; i++) {
    	  customer = new Customer(restaurant);
    	  customer.setName("客户【"+i+"】");
    	  customer.start();
	   }
    }    
}
