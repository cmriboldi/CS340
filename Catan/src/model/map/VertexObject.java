package model.map;

import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * The VertexObject class is used represent either a settlement or a city
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class VertexObject
{
	/**
	 * Represents the location of this vertexObject, normalized VertexLocation location;
	 * 
	 * Indicates the player index to which this object is assigned
	 */
	int player;

	/**
	 * Represents the location of this vertex on the board. May be null if the Vertex Object is stored in
	 * a map.
	 */
	VertexLocation location;

	/**
	 * Constructor independant of VertexObject storage. Notable because it will assign and store the
	 * VertexLocation locally.
	 * @param x_t
	 * @param y_t
	 * @param dir
	 * @param player_t
     */
	public VertexObject(int x_t, int y_t, VertexDirection dir, int player_t){

		location = new VertexLocation(new HexLocation(x_t, y_t), dir);
		player = player_t;
	}

	/**
	 * Map dependant constructor, used only if the VertexLocation is stored outside the object
	 * @param player_t
     */

	public VertexObject(int player_t){
		player = player_t;
		location = null;
	}


}
