package example.demosmartconfig.package_tools_javaClass;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import example.demosmartconfig.R;

public class sj_tabBar_Adapter extends BaseAdapter
{
    // 颜色也字体
    int colorNormal = Color.argb(0xFF, 0x44, 0x44, 0x44);
    int colorActive = Color.argb(0xFF, 0x00, 0xFF, 0x00);

    LayoutInflater inflater ;
    Context context ;
    ArrayList<Item> tabList = new ArrayList<>() ;

    public sj_tabBar_Adapter(Context context)
    {
        this.context = context ;
        inflater = LayoutInflater.from(context);
    }

    public void setActive(int position)
    {
        Item itemSelected = tabList.get(position);
        for(Item item:tabList)
        {
           if (item.tabName.equals(itemSelected.tabName))
           {
               item.selected = true ;
           }
           else
           {
               item.selected = false ;
           }
        }
        notifyDataSetChanged();
    }

    public void addTabBar(ArrayList<Item> items)
    {
        for(Item item :items)
        {
            tabList.add(item);
        }
    }

    public sj_tabBar_Adapter()
    {

    }

    public static class Item
    {
        public String tabName;
        public String value;
        public Drawable iconActive;
        public Drawable iconNormal;
        public boolean selected;

        public Item(String tabName, String value,Drawable iconActive,Drawable iconNormal,boolean selected)
        {
            this.tabName = tabName;
            this.value = value;
            this.iconActive = iconActive ;
            this.iconNormal = iconNormal ;
            this.selected = selected ;
        }
    }
    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public Object getItem(int i) {
        return tabList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.activity_sj_tab_bar__adapter,viewGroup,false);
        }
        Item item = (Item) getItem(i);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.id_tabBar_Icon);
        TextView textView = (TextView)convertView.findViewById(R.id.id_tabBar_Name);
        textView.setText(item.tabName);

        if(item.selected)
        {
            textView.setTextColor(colorActive);
            imageView.setImageDrawable(item.iconActive);
        }
        else
        {
            textView.setTextColor(colorNormal);
            imageView.setImageDrawable(item.iconNormal);
        }
        return convertView ;
    }
}
