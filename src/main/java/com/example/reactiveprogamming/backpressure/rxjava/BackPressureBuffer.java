package com.example.reactiveprogamming.backpressure.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;

/**
 * This is a simple example to explain BackPressure with Buffer Strategy
 *
 * The publisher emits 0 -19 integers with a delay of 500ms
 *
 * The subscriber is slower than the publisher, to simulate this 1 second delay is added in the subscribe onNext method
 *
 * Since the BackPressure strategy is BUFFER ( the data is buffered here so that the subscriber can consume when it is ready
 */
public class BackPressureBuffer {


	public static void main(String[] args) {
		Flowable.<Integer>create(publisher -> publisher(publisher), BackpressureStrategy.BUFFER) //BackPressure strategy is buffer
				.observeOn(Schedulers.io(), false, 2) //different schedulers can be used here
				.subscribe(BackPressureBuffer::slowComputationalSubscriber,
						err -> System.out.println("ERROR: " + err),
						() -> System.out.println("DONE"));
	}

	private static void slowComputationalSubscriber(Integer value) throws InterruptedException {
		System.out.println(value + " -- " + Thread.currentThread());
		Thread.sleep(1000); // slow subscriber sleep for 1 second to simulate a slow computational operation
	}
	private static void publisher(FlowableEmitter<Integer> publisher) throws InterruptedException {
		int count = 0;

		while(count < 20) {
			System.out.println("returning " + count + " --" + Thread.currentThread());
			publisher.onNext(count++);

			Thread.sleep(500);// publisher emits with a delay of 500ms
		}

		System.out.println("DONE publishing");

		Thread.sleep(20000); //simulate a long delay to see what is happening at the subscriber end
	}
}
