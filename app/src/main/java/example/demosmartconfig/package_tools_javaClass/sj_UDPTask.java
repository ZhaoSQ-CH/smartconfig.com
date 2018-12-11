package example.demosmartconfig.package_tools_javaClass;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by ${sunJie} on 2018/12/7.
 */

public abstract class sj_UDPTask extends Thread
{
    Handler handler = new MyHandler();
    protected abstract Object doInBackground();
    protected abstract Object onPostExecute(Bundle bundle);

    public void execute()
    {
        start();
    }

    @Override
    public void run() {

        Bundle result = (Bundle) doInBackground();
        Message msg = new Message();
        msg.what = 1 ;
        msg.setData(result);
        handler.sendMessage(msg);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1)
            {
                Bundle bundle = (Bundle)msg.getData();
                onPostExecute(bundle);
            }
        }
    }
}
