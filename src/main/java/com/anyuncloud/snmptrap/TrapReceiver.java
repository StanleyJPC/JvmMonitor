package com.anyuncloud.snmptrap;

import org.snmp4j.*;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.TransportIpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import java.io.IOException;

/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-09-26 上午11:31                            /\ /\
 */
public class TrapReceiver implements CommandResponder {
    public static void main(String[] args) {
        TrapReceiver snmp4jTrapReceiver = new TrapReceiver();
        try {
            snmp4jTrapReceiver.listen(new TcpAddress("0.0.0.0/162"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized  void listen(TransportIpAddress address)throws IOException {
        AbstractTransportMapping transport;
        if (address instanceof TcpAddress) {

            transport = new DefaultTcpTransportMapping((TcpAddress) address);
        } else {
            transport = new DefaultUdpTransportMapping((UdpAddress) address);

        }
        ThreadPool threadPool = ThreadPool.create("DispatcherPool", 10);

        MessageDispatcher mDispathcher = new MultiThreadedMessageDispatcher(

                threadPool, new MessageDispatcherImpl());
        // add message processing models

        mDispathcher.addMessageProcessingModel(new MPv1());

        mDispathcher.addMessageProcessingModel(new MPv2c());
        // add all security protocols

        SecurityProtocols.getInstance().addDefaultProtocols();

        SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());

        // Create Target
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("1234qwer"));

        Snmp snmp = new Snmp(mDispathcher, transport);
        snmp.addCommandResponder(this);

        transport.listen();
        System.out.println("Listening on " + address);

        try {
            this.wait();
        } catch (InterruptedException ex) {

            Thread.currentThread().interrupt();
        }

    }


    public synchronized void processPdu(CommandResponderEvent cmdRespEvent) {
        System.out.println("Received PDU...");

        PDU pdu = cmdRespEvent.getPDU();
        if (pdu != null) {

            System.out.println("Trap Type = " + pdu.getType());

            System.out.println("Variables = " + pdu.getVariableBindings());

        }

    }
}