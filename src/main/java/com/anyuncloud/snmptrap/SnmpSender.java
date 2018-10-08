package com.anyuncloud.snmptrap;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.Vector;

/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-09-26 上午11:25                            /\ /\
 */
public class SnmpSender {
    private static final Logger LOG = Logger.getLogger(SnmpSender.class);
    private Snmp snmp = null;

    private Address targetAddress = null;

    public void initComm() throws IOException {
        // 设置管理进程的IP和端口
        targetAddress = GenericAddress.parse("udp:localhost/");
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
        LOG.info("init SNMP object succes !!    target = udp:localhost/161");
    }

    /**
     * 向管理进程发送Trap报文
     *
     * @throws IOException
     */
    public void sendTrap() throws IOException { targetAddress = GenericAddress.parse("udp:localhost/7070");
        // 设置 target
        CommunityTarget target = new CommunityTarget();
        target.setAddress(targetAddress);
        // 通信不成功时的重试次数
        target.setRetries(2);
        // 超时时间
        target.setTimeout(1500);
        // snmp版本
        target.setVersion(SnmpConstants.version2c);
        // 创建 PDU
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(".1.3.6.1.2.3377.10.1.1.1.1"),
                new OctetString("SnmpTrap")));
        pdu.add(new VariableBinding(new OID(".1.3.6.1.2.3377.10.1.1.1.2"),
                new OctetString("JavaEE")));
        pdu.setType(PDU.TRAP);

        // 向Agent发送PDU，并接收Response
        ResponseEvent respEvnt = snmp.send(pdu, target);
        // 解析Response
        readResponse(respEvnt);
//      if (respEvnt != null && respEvnt.getResponse() != null) {
//          Vector<VariableBinding> recVBs = respEvnt.getResponse()
//                  .getVariableBindings();
//          for (int i = 0; i < recVBs.size(); i++) {
//              VariableBinding recVB = recVBs.elementAt(i);
//              System.out
//                      .println(recVB.getOid() + " : " + recVB.getVariable());
//          }
//      }
    }

    public ResponseEvent sendPDU(PDU pdu) throws IOException {
        // 设置 target
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("1234qwer"));
        target.setAddress(targetAddress);
        // 通信不成功时的重试次数
        target.setRetries(1);
        // 超时时间
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        // 向Agent发送PDU，并返回Response
        return snmp.send(pdu, target);
    }

    public void doSet() throws IOException {
        // set PDU
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 5,
                0 }), new OctetString("SNMPTEST")));
        pdu.setType(PDU.SET);
        readResponse(sendPDU(pdu));
    }

    public void doGet() throws IOException {
        // get PDU
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 1,
                0 })));
        pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 2,
                0 })));
        pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 3,
                0 })));
        pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 4,
                0 })));
        pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 5,
                0 })));
        pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 6,
                0 })));
        pdu.setType(PDU.GET);
        readResponse(sendPDU(pdu));
    }

    private void readResponse(ResponseEvent respEvnt) {
        // 解析Response
        if (respEvnt != null && respEvnt.getResponse() != null) {
            Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse()
                    .getVariableBindings();
            for (int i = 0; i < recVBs.size(); i++) {
                VariableBinding recVB = recVBs.elementAt(i);
                LOG.info("THREAD NUM--"+Thread.currentThread() +  recVB.getOid() + " : " + recVB.getVariable());
            }
        }
    }




    public void doWork(){
        for(int i=0;i<1;i++){
            Thread t  = new Thread(new WorkThread());
            t.start();
        }
    }

    class WorkThread implements Runnable{

        @Override
        public void run() {
            while(!Thread.currentThread().interrupted()){
                try {
//                  doGet();
//                  doSet();
                    Thread.sleep(1*1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    LOG.error("THREAD NUM--"+Thread.currentThread() + "InterruptedException",e);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("THREAD NUM--"+Thread.currentThread() + "other Exception",e);
                    continue;
                }
            }

        }

    }


    public static void main(String[] args) {
        BasicConfigurator.configure();
        try {
            SnmpSender util = new SnmpSender();
            util.initComm();
          util.sendTrap();
            LOG.info("---  DO GET --");
          util.doGet();
            LOG.info("----do set---");
          util.doSet();
            util.doWork();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
