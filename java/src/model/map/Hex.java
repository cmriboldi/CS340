package model.map;

//Project Imports

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

/**
 * The Hex class is used to represent each tile on the board
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */

public class Hex {
    /**
     * String, possible enumeration, of the resource this hex provides
     */
    String resource;

    /**
     * The representation of the number tile associated with this hex
     */
    int number;

    /**
     * The representation of this tile's x,y coordinates. Most cases hexes will be accessed from
     * outside the Hex class, so this is more for reference
     */
    public HexLocation location;

    /**
     * Most independent constructor, used when there is no map to store the HexLocation
     * @param x_t
     * @param y_t
     * @param resource_t
     * @param number_t
     */
    public Hex(int x_t, int y_t, String resource_t, int number_t) {
        location = new HexLocation(x_t, y_t);
		number = number_t;
        resource = resource_t;
    }

    /**
     * Constructor dependant on an outside Map storing the HexLocation
     * @param resource_t
     * @param number_t
     */
    public Hex(String resource_t, int number_t){
        resource = resource_t;
        number = number_t;
        location = null;
    }

    public String getResource(){return resource;}
}
