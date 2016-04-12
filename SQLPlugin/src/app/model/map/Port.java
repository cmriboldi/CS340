package app.model.map;


import app.definitions.PortType;
import app.locations.EdgeDirection;
import app.locations.EdgeLocation;

/**
 * Created by clayt on 1/29/2016.
 */
public class Port extends EdgeObject {

    /**
     * Defines the type of port
     */
    PortType type;
    int ratio;

    public Port(int x_t, int y_t, EdgeDirection dir, PortType type_t, int ratio_t) {
        super(x_t, y_t, dir);
        type = type_t;
        ratio = ratio_t;
    }

    public Port(EdgeLocation location_t, PortType type_t, int ratio_t) {
        super(location_t);
        type = type_t;
        ratio = ratio_t;
    }

    public String toString() {

        return "Port-" + location.getNormalizedLocation().toString() + " type[" + type + "] ratio[" + ratio + "]";
    }

    public PortType getType() {
        return type;
    }

    public int getRatio(){return ratio;}

    public void setRatio(int newRatio)
    {
        ratio = newRatio;
    }
}
