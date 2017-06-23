package com.shadow.demo.jvm.javaMultiThread.CAS;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Class: SpinLock
 * Author: wanghf
 * Date: 2017/6/23 0023  10:21
 * Descrption:  线程只有在unlocked之后，下一个线程才能execute ，之前的线程都属于locked 自旋状态
 * @See： https://coderbee.net/index.php/concurrent/20131115/577
 */
public class SpinLock {

    private  volatile AtomicReference<Thread> sign =new AtomicReference<>();

    public void lock(){
        Thread current = Thread.currentThread();
        while(!sign .compareAndSet(null, current)){
            System.out.println(Thread.currentThread().getId()+" locked ,"+System.nanoTime());
        }
    }

    public void unlock (){
        Thread current = Thread.currentThread();
        System.out.println(Thread.currentThread().getId()+" un locked,"+System.nanoTime());
        sign .compareAndSet(current, null);
    }


    public static void main(String[] args) {

        SpinLock spinLock = new SpinLock();

        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<Thread> collect = IntStream.range(0, 100).mapToObj(i -> new Thread(() -> {
            spinLock.lock();
            System.out.println(Thread.currentThread().getId()+ " executed,"+System.nanoTime());
            spinLock.unlock();
        })).collect(Collectors.toList());

        collect.forEach(thread -> threadPool.execute(thread));



    }
}