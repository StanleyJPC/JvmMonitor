package com.anyuncloud.snmptrap.SnmpTrapAgent.HostAgent;

import com.anyuncloud.snmptrap.SnmpTrapAgent.SnmpTrapV2;
import com.anyuncloud.snmptrap.TrapSenderVersion2;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-10-17 上午9:57                            /\ /\
 */
public class HostAgent {



    public static void main(String[] args) {

        final SnmpTrapV2 snmpTrapV2 = new SnmpTrapV2();

        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println(new Date());
                snmpTrapV2.sendTrap_Version2();

            }
        };
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
        service.scheduleWithFixedDelay(runnable, 1, 5, TimeUnit.SECONDS);
    }

}
