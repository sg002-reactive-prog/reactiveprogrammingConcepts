package com.example.reactiveprogamming.rxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

/*A HOT Observable may begin emitting items as soon as it is created,
and so any observer who later subscribes to that Observable may start
 observing the sequence somewhere in the middle. */
public class HotObservable {

	public static void main(String[] args) throws InterruptedException {
		Flowable<Long> interval = Flowable.interval(1, TimeUnit.SECONDS).share();

		interval.subscribe(data -> System.out.println("Subscriber 1 :" + data));

		Thread.sleep(5000);

		interval.subscribe(data -> System.out.println("Subscriber 2 :" + data));

		Thread.sleep(10000);
	}

}
