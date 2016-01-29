package model.map;

import shared.locations.VertexDirection;

/**
 * Created by clayt on 1/29/2016.
 */
public class Settlement extends VertexObject {

    /**
     * Identifies whether or not the settlement is a city
     */
    boolean isCity;

    public Settlement(int x_t, int y_t, VertexDirection dir, int player_t){
        super(x_t, y_t, dir, player_t);
        isCity = false;
    }

    public Settlement(int player_t){
        super(player_t);
        isCity = false;
    }
}
