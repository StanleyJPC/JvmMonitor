package com.anyuncloud.jvm;

import java.lang.management.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-09-30 下午1:01                            /\ /\
 */
public class JvmCpu {

    public static void main(String[] args) {
        int sampleTime = 10000;
        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        OperatingSystemMXBean osMxBean = ManagementFactory.getOperatingSystemMXBean();
        Map<Long, Long> threadInitialCPU = new HashMap<Long, Long>();
        Map<Long, Float> threadCPUUsage = new HashMap<Long, Float>();
        long initialUptime = runtimeMxBean.getUptime();

        ThreadInfo[] threadInfos = threadMxBean.dumpAllThreads(false, false);
        for (ThreadInfo info : threadInfos) {
            threadInitialCPU.put(info.getThreadId(), threadMxBean.getThreadCpuTime(info.getThreadId()));

        }

        try {Thread.sleep(sampleTime);} catch (InterruptedException e) {}

        long upTime = runtimeMxBean.getUptime();

        Map<Long, Long> threadCurrentCPU = new HashMap<Long, Long>();
        threadInfos = threadMxBean.dumpAllThreads(false, false);
        for (ThreadInfo info : threadInfos) {
            threadCurrentCPU.put(info.getThreadId(), threadMxBean.getThreadCpuTime(info.getThreadId()));
        }

        // CPU over all processes
//int nrCPUs = osMxBean.getAvailableProcessors();
// total CPU: CPU % can be more than 100% (devided over multiple cpus)
        long nrCPUs = 1;
        // elapsedTime is in ms.
        long elapsedTime = (upTime - initialUptime);
        for (ThreadInfo info : threadInfos) {
            // elapsedCpu is in ns
            Long initialCPU = threadInitialCPU.get(info.getThreadId());
            System.out.println(info.getThreadId());
            if (initialCPU != null) {
                long elapsedCpu = threadCurrentCPU.get(info.getThreadId()) - initialCPU;
                float cpuUsage = elapsedCpu / (elapsedTime * 1000000F * nrCPUs);
                threadCPUUsage.put(info.getThreadId(), cpuUsage);
            }
        }

// threadCPUUsage contains cpu % per thread
        System.out.println(threadCPUUsage);
    }

}

