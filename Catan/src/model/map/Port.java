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

    public Port(int x_t, int y_t, VertexDirection dir, int player_t, PortType type_t) {
        super(x_t, y_t, dir, player_t);
        type = type_t;
    }

    public Port(int player_t, PortType type_t) {
        super(player_t);
        type = type_t;
    }
}
