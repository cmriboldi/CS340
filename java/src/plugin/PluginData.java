package plugin;

/**
 * Created by clayt on 4/10/2016.
 */
public class PluginData implements IPluginData {
   int checkInSize = 0;

    @Override
    public int getCheckinSize() {
        return checkInSize;
    }

    @Override
    public void setCheckinSize(int _checkInSize) {
        checkInSize = _checkInSize;
    }
}
