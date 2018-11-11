package com.example.reactiveprogamming;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

/**
 * An example in java 9
 * Publisher --publishes first 100 fibonacci numbers
 * Subscriber -- subscribes to the feed and gets the first 50 and then cancels the subscription
 */
public class PublisherSubscriberExample {




	public static void main(String[] args) throws Exception {
		SubmissionPublisher<Integer> fibonacciPublisher = new SubmissionPublisher<>();


		Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<Integer>() {
			private Flow.Subscription subscription;
			@Override
			public void onSubscribe(Flow.Subscription subscription) {
				System.out.println("subscribed...");
				this.subscription=subscription;
				subscription.request(1);
			}

			@Override
			public void onNext(Integer item) {
				System.out.println("Got item "+item);
				if(item>=50) {
					subscription.cancel();
				}
				subscription.request(1); //keep requesting 1 item at at time until the value is more than 50

			}

			@Override
			public void onError(Throwable throwable) {

			}

			@Override
			public void onComplete() {

			}
		};

		fibonacciPublisher.subscribe(subscriber);

		int[] fibonacciList = IntStream.generate(new FibonacciSupplier()).limit(100).toArray();



		for (Integer element :fibonacciList) {
			Thread.sleep(500);
			if(!fibonacciPublisher.hasSubscribers()) {
				System.out.println("getting out");
				break;
			}
			fibonacciPublisher.submit(element);
		}

		Thread.sleep(2000);
	}

	private static class FibonacciSupplier implements IntSupplier {

		int current = 1;
		int previous = 0;

		@Override
		public int getAsInt() {
			int result = current;
			current = previous + current;
			previous = result;
			return result;
		}
	}
}
