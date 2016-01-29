package model.map;

//Project Imports

import shared.locations.EdgeLocation;

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
    EdgeLocation location;

}
