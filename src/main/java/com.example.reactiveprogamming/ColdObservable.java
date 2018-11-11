package com.example.reactiveprogamming;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

/**
 *  A COLD Observable, waits until an observer subscribes to it before it begins to emit items,
 *  and so such an observer is guaranteed to see the whole sequence from the beginning
 */
public class ColdObservable {

	public static void main(String[] args) throws InterruptedException {
		Flowable<Long> interval =
				Flowable.interval(1, TimeUnit.SECONDS);

		interval.subscribe(data -> System.out.println("Subscriber 1 :"+data));

		Thread.sleep(5000);

		interval.subscribe(data ->System.out.println("Subscriber 2 :"+data));

		Thread.sleep(10000);
	}


}
