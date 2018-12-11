package example.demosmartconfig;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import example.demosmartconfig.package_tools_javaClass.Device;
import example.demosmartconfig.package_tools_javaClass.Sj_RoundIcon;
import example.demosmartconfig.package_tools_javaClass.sj_store_data;

public class MainActivity extends AppCompatActivity {

    ArrayList<Device> devices = new ArrayList<>();
    MyAdapter myAdapter ;
    File file ;
    sj_store_data tools ;
    Sj_RoundIcon sj_roundIcon ;
    EditText editText ;
    final static String TAG = "测试";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File(getExternalFilesDir("data"),"device.txt");
        tools = new sj_store_data(file);
        myAdapter = new MyAdapter();

        ListView listView  = (ListView) findViewById(R.id.id_chooseDevice);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("测试","点击了");
                onclickItem(i);
            }
        });

        addDevice();

        //去除焦点
        editText = (EditText) findViewById(R.id.id_search);
        editText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(getWindow().getDecorView().getWindowToken(), 0);

        final Drawable leftDrawable = editText.getCompoundDrawables()[0];
        if(leftDrawable!=null)
        {
            leftDrawable.setBounds(5, 5, 60, 60);
            editText.setCompoundDrawables(leftDrawable, editText.getCompoundDrawables()[1], editText.getCompoundDrawables()[2], editText.getCompoundDrawables()[3]);
        }

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus())
                {
                    leftDrawable.setBounds(0,0,0,0);
                }
                else
                {
                    leftDrawable.setBounds(5, 5, 60, 60);
                    editText.setCompoundDrawables(leftDrawable, editText.getCompoundDrawables()[1], editText.getCompoundDrawables()[2], editText.getCompoundDrawables()[3]);
                }
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                leftDrawable.setBounds(5, 5, 60, 60);
                editText.setCompoundDrawables(leftDrawable, editText.getCompoundDrawables()[1], editText.getCompoundDrawables()[2], editText.getCompoundDrawables()[3]);
                return false;
            }
        });
        load();
    }

    public void load()
    {
        try {
            devices = tools.loadInFile();
            Log.d(TAG,devices.get(0).deviceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        myAdapter.notifyDataSetChanged();
    }

    private void onclickItem(int i)
    {
        Device device = (Device) myAdapter.getItem(i);
        Intent intent = new Intent(MainActivity.this,resetting_device_Activity.class);
//        intent.putExtra("id",device.id);
        Log.d("测试",device.deviceName);
        startActivity(intent);
    }

    //查询设备
    public void onSearch(View view)
    {
        devices.clear();
        String searchContent = editText.getText().toString();
        devices = tools.findInFile(searchContent);
        myAdapter.notifyDataSetChanged();
    }

    public void addDevice()
    {
        tools.addCOODevice(new Device("单片机1","danpianji.png","Mac:AC:DB:EF",1));
        tools.addCOODevice(new Device("单片机2","danpianji.png","Mac:AC:DB:EF",2));
        try {
            tools.WriteInFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyAdapter extends BaseAdapter
    {
        public MyAdapter()
        {
        }
        @Override
        public int getCount() {
            return devices.size();
        }

        @Override
        public Object getItem(int i) {
            return devices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return devices.get(i).id;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            if(convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.all_device_item,parent,false);
            }
            Device item = devices.get(i);
            TextView itemName = (TextView)convertView.findViewById(R.id.id_deviceName);
            itemName.setText(item.deviceName);

            sj_roundIcon = (Sj_RoundIcon)convertView.findViewById(R.id.id_icon_device);
            Drawable drawable = getDrawable(R.drawable.me);
            if(drawable != null)
            {
                sj_roundIcon.setIcon(drawable);
            }

            return convertView ;
        }
    }
}
