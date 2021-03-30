/**
 * 
 */
package uk.ac.qub.artemislite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Andy
 *
 */
public class BufferedInterrupter implements Runnable {

	/**
	 * Method that will keep reader going until it is either interrupted or receives
	 * an input
	 */
	@Override
	public void run() {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		try {

			// Loop while reader has nothing
			while (!bufferedReader.ready()) {
				/*
				 * Poll rate - decrease for more accuracy, but more demanding 100ms feels
				 * perfectly responsive to me (AP), but could lead to some into-message
				 * duplication, more testing needed!
				 */
				Thread.sleep(100);
			}
			//TODO: fall through fixed, should we be closing bufferedReader at any point?
			bufferedReader.readLine();

		} catch (InterruptedException e) {
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}// END

}
