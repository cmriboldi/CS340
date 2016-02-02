package model.map;

import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

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

    public Settlement(VertexLocation location_t, int player_t){
        super(location_t, player_t);
        isCity = false;
    }

    public String toString(){
        return "Settlement-" + location.toString() + " isCity[" + isCity + "] player[" + player + "]";
    }
}
