package com.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest implements Callable<Integer> {

	private int length;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public Integer call() throws Exception {

		int sum = 0;
		for (int i = 0; i <= length; i++) {

			if (i % 2 == 0) {
				sum += i;
				System.out.println(i);
			}
		}
		return sum;
	}

	public static void main(String[] args) {

		CallableTest test = new CallableTest();

		test.setLength(10);

		FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(test);

		new Thread(integerFutureTask).start();

		Integer sum;
		try {
			sum = integerFutureTask.get();
			System.out.println("总计：" + sum);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
