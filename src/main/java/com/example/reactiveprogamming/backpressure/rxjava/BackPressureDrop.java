package com.example.reactiveprogamming.backpressure.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;

/**
 * This is another simple example to demonstrate BackPressureStrategy DROP
 * i.e values are discarded if the subscriber is busy
 */
public class BackPressureDrop {

	public static void main(String[] args) {
		Flowable.<Integer>create(emitter -> emit(emitter), BackpressureStrategy.DROP)
				.filter(data -> data > 4)
				.observeOn(Schedulers.io(), false, 2)
				.subscribe(BackPressureDrop::printIt,
						err -> System.out.println("ERROR: " + err),
						() -> System.out.println("DONE"));
	}

	private static void printIt(Integer value) throws InterruptedException {
		System.out.println(value + " -- " + Thread.currentThread());
		Thread.sleep(2000);
	}

	private static void emit(FlowableEmitter<Integer> emitter) throws InterruptedException {
		int count = 0;

		while(count < 20) {
			System.out.println("publishing " + count + " --" + Thread.currentThread());
			emitter.onNext(count++);

			Thread.sleep(500);
		}

		System.out.println("DONE publishing");
		Thread.sleep(10000);
	}
}
