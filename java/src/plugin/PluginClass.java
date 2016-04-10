package plugin;

/**
 * Created by clayt on 4/8/2016.
 */
public class PluginClass implements IPluginClass {

    Class classType = null;

    @Override
    public void setClassType(Class classType_p) {
        classType = classType_p;
    }

    @Override
    public Class getClassType() {
        return classType;
    }
}
