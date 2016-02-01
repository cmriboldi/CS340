package model.map;

import shared.definitions.PortType;
import shared.locations.VertexDirection;

/**
 * Created by clayt on 1/29/2016.
 */
public class Port extends VertexObject {

    /**
     * Defines the type of port
     */
    PortType type;

    int ratio;

    public Port(int x_t, int y_t, VertexDirection dir, PortType type_t, int ratio_t) {
        super(x_t, y_t, dir, -1);
        type = type_t;
        ratio = ratio_t;
    }

    public Port(int player_t, PortType type_t, int ratio_t) {
        super(player_t);
        type = type_t;
        ratio = ratio_t;
    }
}
