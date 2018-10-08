package com.anyuncloud.prometheus;

import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.PushGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-10-08 下午5:08                            /\ /\
 */
public class PdbTest {
    public static void main(String[] args) {
        Histogram ctsLog = Histogram.build().name("elk_monitor_cts").help("log trace").labelNames("host", "timestamp", "lbmname", "instance").buckets(50d, 100d, 500d, 1000d, 5000d).register();
        PushGateway pg = new PushGateway("192.168.101.145.:9091");
        Map<String, String> map = new HashMap<>();
        map.put("a", "2");
        while (true) {
            int i = 0;
            while (i < 30) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctsLog.labels("SZ", "2017-05-05", "LBM0001", "my_job2")
                        .observe(new Random().nextInt(5000));

                i++;
            }
            try {
                pg.pushAdd(ctsLog, "my_job2", map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Push success!");
        }
    }
}
