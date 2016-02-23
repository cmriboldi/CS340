package model.map;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;

/**
 * Created by clayt on 2/1/2016.
 */
public class Road extends EdgeObject {
    int owner;

    public Road(int x_t, int y_t, EdgeDirection dir, int owner_t) {
        super(x_t, y_t, dir);
        owner = owner_t;
    }

    public Road(EdgeLocation location_t, int owner_t) {
        super(location_t);
        owner = owner_t;
    }

    public String toString() {

        return "Road-" + location.getNormalizedLocation().toString() + " Owner[" + owner + "]";
    }

    public int getOwner() {
        return owner;
    }
}
