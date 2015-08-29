package br.com.Util;

public class Tempo extends Thread {

	public void run() {
		while (true) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Jeffgoy: " + Thread.currentThread().getName());
		}
	}
}