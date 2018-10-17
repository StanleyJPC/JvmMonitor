package com.anyuncloud.snmptrap.SnmpTrapAgent.HostAgent;

import com.anyuncloud.snmptrap.SnmpTrapAgent.RemoteShellTool;

/**
 * @Author Stanlly_Jpc
 * @E-mail <1073887513@qq.com>                   Y(^_^)Y  ≡[。。]≡  o(╯□╰)o
 * @Date on 2018-10-17 上午10:52                            /\ /\
 */
public class HostDataGetImpl implements HostDataGet {
    public String hostdata(){
        RemoteShellTool tool = new RemoteShellTool("15.45.192.138", 2222,"root",
                "1234qwer", "utf-8");

        String result2 = tool.exec("/Jpc/./BTC.sh xiaojun");


        return "50 60 70";
    }
}
