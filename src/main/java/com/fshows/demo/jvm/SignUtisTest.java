package com.fshows.demo.jvm;


import com.fshows.demo.jvm.javaMultiThread.SignUtil;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread) //默认状态。实例将分配给运行给定测试的每个线程。
@BenchmarkMode(Mode.Throughput) //吞吐量测试
//@BenchmarkMode(Mode.SampleTime)
//@BenchmarkMode(Mode.SingleShotTime)
//@BenchmarkMode(Mode.AverageTime)
@Fork(2)
@Threads(1)
@Warmup(iterations = 0)
@Measurement(iterations = 1,timeUnit = TimeUnit.SECONDS,time = 3)
public class SignUtisTest {


    TreeMap<String, String> map;
    @Setup
    public void init(){
        map = new TreeMap<>();
        map.put("app_id","20161206150707772500");
        map.put("method","openapi.payment.order.scan");
        map.put("format","json");
        map.put("sign_method","md5");
        map.put("nonce","solt"); //
        map.put("version","1.0");


        HashMap<String, Object> content = new HashMap<String, Object>();

        content.put("merchant_order_sn","1");
        content.put("type",1);
        content.put("total_fee",0.01f);
    }

    @Benchmark
    public void t(){
        System.out.println(SignUtil.createSign(map,"111"));

    }
}
