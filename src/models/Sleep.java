
package models;
import java.util.concurrent.TimeUnit;


public class Sleep {
	public int a = 11;
	public static void sleep(int second) {
		try {
			TimeUnit.SECONDS.sleep(second);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
