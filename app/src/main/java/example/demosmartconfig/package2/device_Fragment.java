package example.demosmartconfig.package2;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import example.demosmartconfig.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class device_Fragment extends Fragment {

    View contentView ;
    ArrayList<Item> items = new ArrayList<>();
    public device_Fragment() {
        // Required empty public constructor
    }

    public static class Item
    {
        public Drawable iconDevice ;
        public String MacAddress ;
        public String deviceName ;
        public boolean isOnLine ;
        public Item(Drawable iconDevice,String MacAdress,String deviceName,boolean isOnLine)
        {
            this.iconDevice = iconDevice ;
            this.deviceName = deviceName ;
            this.MacAddress = MacAdress ;
            this.isOnLine = isOnLine ;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.fragment_device_,container,false);
        ListView listView = (ListView)contentView.findViewById(R.id.id_device_listView);
        MyListAdapter mylistAdapter = new MyListAdapter();
        listView.setAdapter(mylistAdapter);

        return contentView ;
    }

    private class MyListAdapter extends BaseAdapter
    {
        LayoutInflater inflater ;
        Drawable[] icons = new Drawable[3];
        public MyListAdapter()
        {
            inflater = LayoutInflater.from(getContext());
            icons[0] = inflater.getContext().getDrawable(R.drawable.isonline);
            icons[1] = inflater.getContext().getDrawable(R.drawable.notonline);
            icons[2] = inflater.getContext().getDrawable(R.drawable.danpianji);
            items.add(new Item(icons[2],"MAC:AC:DB:FA","单片机A",true));
            items.add(new Item(icons[2],"MAC:AC:DB:FA","单片机B",false ));
        }
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
           if(convertView == null)
           {
               convertView = inflater.inflate(R.layout.all_device_item2,viewGroup,false);
           }
           ImageView deviceIcon = (ImageView)convertView.findViewById(R.id.id_iconDevice);
            ImageView onlineIcon = (ImageView)convertView.findViewById(R.id.id_onlineIcon);
            TextView deviceName = (TextView)convertView.findViewById(R.id.id_deviceName);
            TextView MacAddress = (TextView)convertView.findViewById(R.id.id_MacAddress);
            TextView isOnLine = (TextView)convertView.findViewById(R.id.id_isonLine);

            Item item = (Item) getItem(i);
            deviceIcon.setImageDrawable(item.iconDevice);
            if(item.isOnLine)
            {
                onlineIcon.setImageDrawable(icons[0]);
                isOnLine.setText("在线");
            }
            else
            {
                onlineIcon.setImageDrawable(icons[1]);
                isOnLine.setText("离线");
            }
            deviceName.setText(item.deviceName);
            MacAddress.setText(item.MacAddress);

            return convertView ;
        }
    }
}
