package com.anyuncloud;

/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-09-30 下午1:02                            /\ /\
 */
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

public class RmiServer {
    public static void main(String[] args) {
        try{

            //创建并导出接受指定port请求的本地主机上的Registry实例。
            LocateRegistry.createRegistry(8081);
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            JMXConnectorServer cserver = JMXConnectorServerFactory
                    .newJMXConnectorServer(new JMXServiceURL(
                                    "service:jmx:rmi:///jndi/rmi://localhost:8081/jmxrmi"),
                            null, server);
            cserver.start();
            System.out.println("Server启动成功");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
