package example.demosmartconfig;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import example.demosmartconfig.package_tools_javaClass.sj_UDPTask;

public class connect_Wifi_Activity extends AppCompatActivity {

    WifiManager wm;
    TextView ed_WifiName ;
    EditText ed_WifiPassword ;
    Button bd_ok ;
    UDPTask udpTask ;
    final static String TAG = "测试";
    static DatagramSocket udpSocket ;
    static DatagramPacket udpsendPacket ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect__wifi);

        wm = (WifiManager)this.getApplicationContext().getSystemService(WIFI_SERVICE);
        ed_WifiName = (TextView) findViewById(R.id.id_WifiName);
        ed_WifiPassword = (EditText) findViewById(R.id.id_WifiPassword);
        bd_ok = (Button)findViewById(R.id.id_next2);

        //连接UDPSocket
        connect();

        //获取ssid,设置ssid
        final String ssid = getSSid();

        // 为下一步按钮添加listener
        bd_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(ed_WifiName.getText().toString().trim().equals("当前未连接Wifi"))
//                {
//
//                }
//                udpTask = new UDPTask();
//                udpTask.execute();

                Intent intent = new Intent(connect_Wifi_Activity.this,connect_Wifi_Progress_Activity.class);
                startActivity(intent);
             }
        });

        Intent intent = getIntent();

    }

    private void connect()
    {
        try {
            udpSocket = new DatagramSocket(4560);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    //获取Wifi ssid
    private String getSSid()
    {
        if(wm != null)
        {
            WifiInfo wf = wm.getConnectionInfo();
            if(wf != null)
            {
                String ssid = wf.getSSID();
                Log.w(TAG, ssid.substring(1,ssid.length()-1));
                if (ssid.length() > 2 && ssid.charAt(0) == '"' && ssid.charAt(ssid.length() - 1) == '"')
                {
                    ed_WifiName.setText("当前Wifi："+ssid.substring(1,ssid.length()-1));
                    return ssid.substring(1,ssid.length()-1);
                }
            }
        }
        ed_WifiName.setText("当前未连接Wifi");
        return "";
    }

    //完成发送ssid,password小任务
    private class UDPTask extends sj_UDPTask
    {

        @Override
        protected Object doInBackground() {
            Bundle bundle = new Bundle();
            bundle.putString("ssid",getSSid());
            bundle.putString("password",ed_WifiPassword.getText().toString().trim());

            return bundle ;
        }

        @Override
        protected Object onPostExecute(Bundle bundle) {

            send(bundle);
            return null;
        }
    }

    private void send(Bundle bundle)
    {
        try{
            String ssid = bundle.getString("ssid");
            String password = bundle.getString("password");

            byte[] encodedBytes = getBytesForSmartConfig(ssid,password);
            encodedBytes = MakeCRC8(encodedBytes);
            byte[] encBuf = SmartLinkEncode(encodedBytes);
            byte[] dummybuf = new byte[18];
            int delayms = 5;
            long beginTime = System.currentTimeMillis();

            udpsendPacket = new DatagramPacket(dummybuf, dummybuf.length);
            udpsendPacket.setData(dummybuf);
            udpsendPacket.setPort(80);
            udpsendPacket.setAddress(InetAddress.getByName("255.255.255.255"));

            while (true)
            {
                delayms++;
                if (delayms > 27) {
                    delayms = 20;
                }
                for (byte anEncBuf : encBuf) {
                    udpsendPacket.setLength(anEncBuf);
                    udpSocket.send(udpsendPacket);
                    Thread.sleep(delayms);
                }
                Thread.sleep(200);
                if ((System.currentTimeMillis() - beginTime) / 1000 >= 25)
                    break;
            }}catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    byte[] getBytesForSmartConfig(String... values) {
        StringBuilder combinedStrings = new StringBuilder();
        for(String value : values) {
            combinedStrings.append(value);
            combinedStrings.append('\n');
        }

        Log.w(TAG,combinedStrings.toString());
        return combinedStrings.toString().getBytes();
    }

    private byte[] SmartLinkEncode(byte[] src) {
        byte[] rtlval;
        int curidx = 0;
        rtlval = new byte[src.length * 10];
        byte tmp;
        for (int i = 0; i < src.length; i++) {
            rtlval[curidx++] = 0;
            rtlval[curidx++] = 0;
            tmp = (byte) (i & 0xF);
            rtlval[curidx++] = (byte) (tmp + 1);//pos_L
            rtlval[curidx++] = (byte) ((15 - tmp) + 1);//~pos_L
            tmp = (byte) ((i & 0xF0) >> 4);
            rtlval[curidx++] = (byte) (tmp + 1);//pos_H
            rtlval[curidx++] = (byte) ((15 - tmp) + 1);//~pos_H
            tmp = (byte) (src[i] & 0xF);
            rtlval[curidx++] = (byte) (tmp + 1);//val_L
            rtlval[curidx++] = (byte) ((15 - tmp) + 1);//~val_L
            tmp = (byte) ((src[i] & 0xF0) >> 4);
            rtlval[curidx++] = (byte) (tmp + 1);//val_H
            rtlval[curidx++] = (byte) ((15 - tmp) + 1);//~val_H
        }
        return rtlval;
    }

    byte[] MakeCRC8(byte[] src) {
        byte crc = calcrc_bytes(src, src.length);
        byte[] rtlval = new byte[src.length + 1];
        System.arraycopy(src, 0, rtlval, 0, src.length);
        rtlval[src.length] = crc;
        return rtlval;
    }

    byte calcrc_bytes(byte[] p, int len) {
        byte crc = 0;
        int i = 0;
        while (i < len) {
            crc = (byte) calcrc_1byte(crc ^ p[i]);
            int j = crc & 0xff;
            Log.e(TAG, "crc=" + j);
            i++;
        }
        return crc;
    }

    int calcrc_1byte(int abyte) {
        int i, crc_1byte;
        crc_1byte = 0;
        for (i = 0; i < 8; i++) {
            if (((crc_1byte ^ abyte) & 0x01) > 0) {
                crc_1byte ^= 0x18;
                crc_1byte >>= 1;
                crc_1byte |= 0x80;
            } else {
                crc_1byte >>= 1;
            }
            abyte >>= 1;
        }
        return crc_1byte;
    }
}
