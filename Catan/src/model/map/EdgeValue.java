package model.map;

//Project Imports
import shared.locations.EdgeLocation;

/**
 * Used to represent road states.
 * @author Clayton
 *
 */
public class EdgeValue 
{
	/** indicates the player who owns the road occupying this edge */
	int owner;
	
	/** indicates the location of the edge on the board */
	EdgeLocation location;

}
