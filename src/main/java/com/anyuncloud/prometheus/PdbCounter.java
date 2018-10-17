package com.anyuncloud.prometheus;

import com.codahale.metrics.MetricRegistry;
import io.prometheus.client.*;

import java.io.IOException;
import java.util.Random;


/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-10-10 上午11:48                            /\ /\
 */
public class PdbCounter {

//    static final Gauge g = Gauge.build().name("gauge").help("blah").register();
//    static final Counter c = Counter.build().name("counter").help("meh").register();
//    static final Summary s = Summary.build().name("summary").help("meh").register();
//    static final Histogram h = Histogram.build().name("histogram").help("meh").register();
//    static final Gauge l = Gauge.build().name("Stanlly").help("blah").labelNames("l").register();
    static final Gauge Exec_Time = Gauge.build().name("Exec_Time").help("test").labelNames("Third_party_name").register();
    static final Gauge Host_CpuUsage = Gauge.build().name("Host_CpuUsage").labelNames("Host_IP").help("test").register();
    static final Gauge Host_MemUsage = Gauge.build().name("Host_MemUsage").labelNames("Host_IP").help("test").register();
    static final Gauge Host_DiskUsage = Gauge.build().name("Host_DiskUsage").help("test").labelNames("Host_IP").register();
    static final Gauge Host_NetInRate = Gauge.build().name("Host_NetInRate").help("test").labelNames("Host_IP").register();
    static final Gauge Host_NetOutRate = Gauge.build().name("Host_NetOutRate").help("test").labelNames("Host_IP").register();
    static final Gauge Api_Exec_Time = Gauge.build().name("Api_Exec_Time").help("test").labelNames("ApiName").register();
    static final Gauge Api_Exec_Result = Gauge.build().name("Api_Exec_Result").help("test").labelNames("ApiName","results").register();
    static final Gauge Api_Exec_Rx = Gauge.build().name("Api_Exec_Rx").help("test").labelNames("ApiName").register();
    static final Gauge Api_Exec_Tx = Gauge.build().name("Api_Exec_Tx").help("test").labelNames("ApiName").register();
    static final Gauge Api_Node = Gauge.build().name("Api_Node").help("test").labelNames("Host_IP").register();
    static final Gauge Service_Exec_Time = Gauge.build().name("Service_Exec_Time").help("test").labelNames("ServiceName").register();
    static final Gauge Service_Exec_Result = Gauge.build().name("Service_Exec_Result").help("test").labelNames("ServiceName","results").register();
    static final Gauge Service_Exec_Rx = Gauge.build().name("Service_Exec_Rx").help("test").labelNames("ServiceName").register();
    static final Gauge Service_Exec_Tx = Gauge.build().name("Service_Exec_Tx").help("test").labelNames("ServiceName").register();
//    static final Gauge Title = Gauge.build().name("Title").help("test").labelNames("Host_IP").register();
//    static final Gauge WarnType = Gauge.build().name("WarnType").help("test").labelNames("Host_IP").register();
//    static final Gauge WarnTime = Gauge.build().name("WarnTime").help("test").labelNames("Host_IP").register();
    static final Gauge Jvm_Thread_CpuUsage = Gauge.build().name("Jvm_Thread_CpuUsage").labelNames("ThreadName").help("test").register();
    static final Gauge Jvm_Thread_MemUsage = Gauge.build().name("Jvm_Thread_MemUsage").labelNames("ThreadName").help("test").register();
    public static void main(String[] args) throws IOException {
        new HTTPServer(9100);
        while (true) {
            int i = 0;
            while (i < 30) {
                try {
//                    c.inc(new Random().nextInt(5));
//                    g.set(new Random().nextInt(5));
//                    s.observe(new Random().nextInt(5));
//                    h.observe(new Random().nextInt(5));
//                    l.labels("foo").set(new Random().nextInt(10));
//                    l.labels("foo1").set(new Random().nextInt(1000));
//                    System.out.println(g.get());
                    String[] result = new String[]{
                            "SUCCESS", "SELF-FAIL", "ROUTE-FAIL"
                    };
                    String[] urls = new String[]{
                            "/camera-app-rtp/v1/dc",
                            "/camera-app-rtp/v2/camera",
                            "/camera-app-rtp/v2/test1",
                            "/camera-app-rtp/v1/test2",
                    };
                    String[] services = new String[]{
                            "6950506558CE848B331B6D2483F39790",
                            "76A75B5F1D5BD6360429A97F6E4AD1BC",
                    };
                    String[] adrr = new String[]{
                            "15.45.255.143",
                            "15.45.255.144",
                            "15.45.255.145",
                            "15.45.255.146",
                    };
                    String res = result[new Random().nextInt(3)];
                    String api = urls[new Random().nextInt(4)];
                    String service = services[new Random().nextInt(2)];
                    String ip = adrr[new Random().nextInt(4)];
                    System.out.println(res);
                    System.out.println(api);
                    System.out.println(service);
                    System.out.println(ip);
//        第三方api
                    Exec_Time.labels("Thrid").set(new Random().nextInt(5));
//        宿主机
                    Host_CpuUsage.labels(ip).set(new Random().nextInt(5));
                    Host_MemUsage.labels(ip).set(new Random().nextInt(5));
                    Host_DiskUsage.labels(ip).set(new Random().nextInt(5));
                    Host_NetInRate.labels(ip).set(new Random().nextInt(5));
                    Host_NetOutRate.labels(ip).set(new Random().nextInt(5));
//        Api
                    Api_Exec_Time.labels(api).set(new Random().nextInt(5));
                    Api_Exec_Result.labels(api,res).set(new Random().nextInt(5));
                    Api_Exec_Rx.labels(api).set(new Random().nextInt(5));
                    Api_Exec_Tx.labels(api).set(new Random().nextInt(5));
                    Api_Node.labels(ip).set(new Random().nextInt(5));
//        服务
                    Service_Exec_Time.labels(service).set(new Random().nextInt(5));
                    Service_Exec_Result.labels(service,res).set(new Random().nextInt(5));
                    Service_Exec_Rx.labels(service).set(new Random().nextInt(5));
                    Service_Exec_Tx.labels(service).set(new Random().nextInt(5));
//        JvmTheard
                    Jvm_Thread_CpuUsage.labels(service).set(new Random().nextInt(5));
                    Jvm_Thread_MemUsage.labels(service).set(new Random().nextInt(5));
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                i++;
            }

            System.out.println("Push success!");
        }
    }
    }
