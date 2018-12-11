package example.demosmartconfig.package_tools_javaClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ${sunJie} on 2018/12/2.
 */

public class sj_store_data
{
    ArrayList<Device> dataList = new ArrayList<>();
    ArrayList<Device> findData = new ArrayList<>();
    File file ;
    public sj_store_data(File file)
    {
        this.file = file ;
    }

    public void addCOODevice(Device device)
    {
        dataList.add(device);
    }

    public void WriteInFile() throws Exception
    {
        FileOutputStream fStream = new FileOutputStream(file);
        for(Device device:dataList)
        {
            String content = device.deviceIcon+","+device.deviceName+","+device.deviceMac+","+device.id+"\n";
            fStream.write(content.getBytes("UTF-8"));
        }
        fStream.close();
        dataList.clear();
    }

    public ArrayList<Device> loadInFile() throws Exception
    {
        InputStreamReader m = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader reader = new BufferedReader(m);

        dataList.clear();
        while (true)
        {
            String line = reader.readLine();
            if(line == null)
            {
                break;
            }
            String[] kkk = line.split(",");
            Device device = new Device();

            if(kkk.length<4)
            {
                break;
            }
            device.deviceIcon = kkk[0].trim();
            device.deviceName = kkk[1].trim();
            device.deviceMac = kkk[2].trim();
            device.id = Integer.valueOf(kkk[3].trim());

            dataList.add(device);
        }
        reader.close();
        return dataList ;
    }

    public ArrayList<Device> findInFile(String name)
    {
        try {
            this.loadInFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Device device:dataList)
        {
            if(device.deviceName.indexOf(name)>=0)
            {
                findData.add(device);
            }
        }
        return findData;
    }
}
