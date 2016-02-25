package client.map.presenters;

import client.data.RobPlayerInfo;
import serverProxy.ServerException;
import shared.definitions.*;
import shared.locations.*;

/**
 * The MapControllerState contains the state of the MapController. Depending on the state most
 * methods behave differently.
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Feb, 2016.
 */
public interface MapControllerState {
    /**
     * Initializes the MapControllerState from the Model passed in.
     */
    public void initFromModel(); 
    
    
    // ===================== NECESSARY CanDos ====================== // 

    public boolean canPlaceRoad(EdgeLocation edgeLoc);
    
    public boolean canPlaceSettlement(VertexLocation vertLoc);

    public boolean canPlaceCity(VertexLocation vertLoc);

    public boolean canPlaceRobber(HexLocation hexLoc);
    
    
    // ===================== USE MAYBE CanDos ====================== // 
    
    public boolean canPlaySoldier();
    
    public boolean canPlayRoadBuildingCard(); 
    
    public boolean canRobPlayer(); 

    // ============================================================= // 

    
   
    

    public void placeRoad(EdgeLocation edgeLoc) throws ServerException;

    public void placeSettlement(VertexLocation vertLoc);

    public void placeCity(VertexLocation vertLoc);

    public void placeRobber(HexLocation hexLoc);

    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected);

    public void cancelMove();

    public void playSoldierCard();

    public void playRoadBuildingCard();

    public void robPlayer(RobPlayerInfo victim);
    
    
}
