package com.example.reactiveprogamming.rxjava;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.FutureTask;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Most of the time while working you will be interacting with existing Observables, typically combining, filtering, and wrapping them with one another.
 * However, unless you work with an external API that already exposes Observables, you first must learn where Observables come from and how you can
 * create a stream and handle subscriptions. First, there are several factory methods that create fixed constant Observables.
 */
public class CreateObservable {

	public static void main(String[] args) {

        //Examples for creating Observables


		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("-----------------------Observable creation with create()------------------------------");
		System.out.println("--------------------------------------------------------------------------------------");
		create().subscribe(value -> System.out.println(value), (error) -> System.out.println("error"), () -> System.out.println("Completed"));


		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("-----------------------Observable creation with fromCallable()------------------------");
		System.out.println("--------------------------------------------------------------------------------------");
		fromCallable().subscribe(value -> System.out.println(value), (error) -> System.out.println("error"), () -> System.out.println("Completed"));



		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("-----------------------Observable creation with just()--------------------------------");
		System.out.println("--------------------------------------------------------------------------------------");
		just().subscribe(value -> System.out.println(value), (error) -> System.out.println("error"), () -> System.out.println("Completed"));

		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("-----------------------Observable creation with fromArray()---------------------------");
		System.out.println("--------------------------------------------------------------------------------------");

		fromArray().subscribe(value -> System.out.println(value), (error) -> System.out.println("error"), () -> System.out.println("Completed"));


		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("-----------------------Observable creation with fromIterable()---------------------------");
		System.out.println("--------------------------------------------------------------------------------------");

		fromIterable().subscribe(value -> System.out.println(value), (error) -> System.out.println("error"), () -> System.out.println("Completed"));


		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("-----------------------Observable creation with fromFuture()--------------------------");
		System.out.println("--------------------------------------------------------------------------------------");

		fromFuture().subscribe(value -> System.out.println(value), (error) -> System.out.println("error"), () -> System.out.println("Completed"));


		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("-----------------------Observable creation with range()------------------------------");
		System.out.println("--------------------------------------------------------------------------------------");
		range().subscribe(value -> System.out.println(value), (error) -> System.out.println("error"), () -> System.out.println("Completed"));


		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("-----------------------Observable creation with empty()------------------------------");
		System.out.println("--------------------------------------------------------------------------------------");
		emptyObservable().subscribe(value -> System.out.println(value), (error) -> System.out.println("error"), () -> System.out.println("Completed"));

		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("-----------------------Observable creation with error()------------------------------");
		System.out.println("--------------------------------------------------------------------------------------");
		error().subscribe(value -> System.out.println(value), (error) -> System.out.println("error"), () -> System.out.println("Completed"));


	}

	private static Observable error() {
		return Observable.error(new RuntimeException("Some Error"));
	}

	private static Observable emptyObservable() {
		return Observable.empty();
	}

	private static Observable<Integer> range() {
		return Observable.range(0,10);
	}

	public static Observable<Integer> create(){
		return Observable.create(subscriber -> {
			try {
				subscriber.onNext(1);
				subscriber.onComplete();
			} catch (Exception e) {
				subscriber.onError(e);
			}
		});
	}

	public static Observable<Integer> fromCallable(){
		return Observable.fromCallable(() -> 1);
	}
	public static Observable<Integer> fromArray() {
		return Observable.fromArray(1,2,3,4,5,6);
	}

	public static Observable<Integer> fromIterable() {
		return Observable.fromIterable(Arrays.asList(1,1,2,3,5,8,13));
	}

	public static Observable<List<Integer>> fromFuture(){
		FutureTask<List<Integer>> future =new FutureTask<>(() -> Arrays.asList(1,2,3,4));
		Observable<List<Integer>>  futureObservable = Observable.fromFuture(future);
        Schedulers.computation().scheduleDirect(()-> future.run()); //the future needs to be scheduled to run
		return futureObservable;

	}

	public static Observable<Integer> just() {
		return Observable.just(1);
	}
}
