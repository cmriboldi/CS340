package model.map;

//Project Imports

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

/**
 * Used to represent road states.
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class EdgeObject {
    /**
     * indicates the player who owns the road occupying this edge
     */
    int owner;

    /**
     * indicates the location of the edge on the board
     */
    public EdgeLocation location;

    /**
     * Independent constructor. Used when EdgeObject is not being stored in a map.
     * @param x_t
     * @param y_t
     * @param dir
     * @param owner_t
     */
    public EdgeObject(int x_t, int y_t, EdgeDirection dir, int owner_t) {
        location = new EdgeLocation(new HexLocation(x_t, y_t), dir);
        owner = owner_t;
    }

    /**
     * Dependant constructor, used when storing EdgeObjects in a map of EdgeLocations
     * @param owner_t
     */
    public EdgeObject(int owner_t) {
        location = null;
        owner = owner_t;
    }
}
