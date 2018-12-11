package example.demosmartconfig.package_tools_javaClass;

/**
 * Created by ${sunJie} on 2018/12/3.
 */

public class Device
{
    public int id ;
    public String deviceName ;
    public String deviceIcon ;
    public String deviceMac ;
    public Device(String deviceName,String deviceIcon,String deviceMac,int id)
    {
        this.id = id ;
        this.deviceIcon = deviceIcon ;
        this.deviceName = deviceName ;
        this.deviceMac = deviceMac ;
    }
    public Device()
    {

    }
}
