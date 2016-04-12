package app.model.map;

//Project Imports
import app.locations.*;

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
     * indicates the location of the edge on the board
     */
    public EdgeLocation location;

    /**
     * Independent constructor. Used when EdgeObject is not being stored in a map.
     *
     * @param x_t
     * @param y_t
     * @param dir
     */
    public EdgeObject(int x_t, int y_t, EdgeDirection dir) {
        location = new EdgeLocation(new HexLocation(x_t, y_t), dir);
    }

    public EdgeObject(EdgeLocation location_t) {
        location = location_t;
    }
}
