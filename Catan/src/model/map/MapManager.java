package model.map;

//Project Imports
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import model.resources.ResourceList;

//Java imports
import java.util.ArrayList;

/**
 * The MapManager class manages all interactions with the Map.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class MapManager implements IMapManager
{

	@Override
	public int findLargestRoad() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void placeRoad(EdgeLocation edge, int player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeSettlement(VertexLocation vertex, int player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upgradeSettlement(VertexLocation vertex, int player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ResourceList> distributeResources(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void placeRobber(HexLocation hex) {
		// TODO Auto-generated method stub
		
	}


}
