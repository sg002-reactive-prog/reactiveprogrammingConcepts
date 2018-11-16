package com.example.reactiveprogamming.backpressure.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;

/**
 * This is an example with BackPressure strategy LATEST
 * If the subscriber is busy , the latest value emitted is kept for the subscriber and this value
 * is updated as and when it publishes new items.
 * Observe the ouput at the end of the test , the latest value is kept for the subscriber to consume
 */
public class BackPressureLatest {

	public static void main(String[] args) {
		Flowable.<Integer>create(emitter -> emit(emitter), BackpressureStrategy.LATEST)
				.map(data -> data * 1.0)
				.filter(data -> data > 4)
				.observeOn(Schedulers.io())
				.subscribe(BackPressureLatest::printIt,
						err -> System.out.println("ERROR: " + err),
						() -> System.out.println("DONE"));
	}

	private static void printIt(Double value) throws InterruptedException {
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
