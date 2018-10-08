package com.anyuncloud.snmptrap;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultTcpTransportMapping;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-09-26 上午11:32                            /\ /\
 */
public class TrapSenderVersion2 {
    public static final String community = "1234qwer";


    // Sending Trap for sysLocation of RFC1213

    public static final String Oid = ".1.3.6.1.4.1.2345";


    //IP of Local Host

    public static final String ipAddress = "192.168.2.103";


    //Ideally Port 162 should be used to send receive Trap, any other available Port can be used

    public static final int port = 162;


    public static void main(String[] args) {

            final TrapSenderVersion2 trapV2 = new TrapSenderVersion2();

            Runnable runnable = new Runnable() {
                public void run() {
                    System.out.println(new Date());
                    trapV2.sendTrap_Version2();

                }
            };
            ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
            service.scheduleWithFixedDelay(runnable, 1, 5, TimeUnit.SECONDS);
        }
//        TrapSenderVersion2 trapV2 = new TrapSenderVersion2();
//
//        trapV2.sendTrap_Version2();



    /**
     * This methods sends the V1 trap to the Localhost in port 162
     */

    public void sendTrap_Version2() {

        try {

            // Create Transport Mapping

            TransportMapping transport = new DefaultTcpTransportMapping();

            transport.listen();


            // Create Target

            CommunityTarget cTarget = new CommunityTarget();

            cTarget.setCommunity(new OctetString(community));

            cTarget.setVersion(SnmpConstants.version2c);

            cTarget.setAddress(new TcpAddress(ipAddress + "/" + port));

            cTarget.setRetries(2);

            cTarget.setTimeout(5000);


            // Create PDU for V2

            PDU pdu = new PDU();


            // need to specify the system up time

            pdu.add(new VariableBinding(SnmpConstants.sysUpTime,

                    new OctetString(new Date().toString())));
            System.out.println("----------------------"+SnmpConstants.sysUpTime);


            pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(

                    Oid)));

            pdu.add(new VariableBinding(SnmpConstants.snmpTrapAddress,

                    new IpAddress(ipAddress)));


            pdu.add(new VariableBinding(new OID(Oid), new OctetString(

                    "mytest is a oid")));

            pdu.setType(PDU.NOTIFICATION);


            // Send the PDU

            Snmp snmp = new Snmp(transport);

            System.out.println("Sending V2 Trap... Check Wheather NMS is Listening or not? ");

            snmp.send(pdu, cTarget);

//            snmp.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}

