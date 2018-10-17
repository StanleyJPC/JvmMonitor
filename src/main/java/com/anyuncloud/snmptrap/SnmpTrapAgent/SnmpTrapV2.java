package com.anyuncloud.snmptrap.SnmpTrapAgent;

import com.anyuncloud.snmptrap.SnmpTrapAgent.HostAgent.HostDataGetImpl;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultTcpTransportMapping;

import java.util.Date;

/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-10-17 上午10:41                            /\ /\
 */
public class SnmpTrapV2 {
    public static final String community = "1234qwer";

    // Sending Trap for sysLocation of RFC1213

    public static final String Oid = ".1.3.6.1.4.1.2345";


    //IP of Server Host

    public static final String ipAddress = "192.168.2.101";


    //Ideally Port 162 should be used to send receive Trap, any other available Port can be used

    public static final int port = 8899;
    public void sendTrap_Version2() {
         HostDataGetImpl hostDataGet = new HostDataGetImpl();
        try {

            // Create Transport Mapping

            TransportMapping transport = new DefaultTcpTransportMapping();

            //TransportMapping transport = new DefaultUdpTransportMapping();
            /*
                tcp最后关闭才能关闭链接，udp不同
             */

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

                    hostDataGet.hostdata().toString())));

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
