package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.BuildSettlementJSON;
import shared.communication.JSON.IJavaJSON;
import shared.definitions.PieceType;
import shared.definitions.TurnType;
import shared.exceptions.resources.InvalidPieceTypeException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;
import shared.exceptions.resources.NotEnoughResourcesException;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class BuildSettlementCommand implements ICommand {

    private AuthToken authToken = null;
    private BuildSettlementJSON body = null;
    private final IServerFacade facade;

    public BuildSettlementCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade) {
        System.out.println("Creating a Build Settlement Command");

        this.authToken = authToken;
        this.body = (BuildSettlementJSON) jsonBody;
        this.facade = facade;
    }

    /**
     * Updates the CatanModel to reflect the building of a settlement
     *
     * @return CatanModel, return the updated Catan model
     */
    @Override
    public Object execute() {

        System.out.println("Executing buildSettlementCommand");

        CatanModel cm = null;
        try {
            //Retrieve the game model designated by the authToken
            cm = facade.getGameModel(authToken);

            //Translate from JSONbody into a Java settlement location
            int settleLocX = body.getVertexLocation().getX();
            int settleLocY = body.getVertexLocation().getY();
            VertexDirection settleLocDir = VertexDirection.toEnum(body.getVertexLocation().getDirection());
            VertexLocation settleLoc = new VertexLocation(new HexLocation(settleLocX, settleLocY), settleLocDir);

            //Make change to the model
            cm.getMapManager().placeSettlement(settleLoc, body.getPlayerIndex());
            cm.playerManager.incrementPlayerPoints(this.body.getPlayerIndex());
            cm.playerManager.decrementPieceCount(this.body.getPlayerIndex(), PieceType.SETTLEMENT);
            if(cm.playerManager.getTurnStatus() == TurnType.PLAYING) {
            	cm.resourceManager.buyPiece(this.body.getPlayerIndex(), PieceType.SETTLEMENT);
            }

            cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " built a settlement.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
            
            //Update the model with the facade
            facade.updateGame(authToken, cm);
            
            facade.recordCommand(authToken, this);

        } catch (ServerException | NotEnoughPlayerResourcesException | InvalidPieceTypeException | NotEnoughResourcesException e) {
            e.printStackTrace();
        }

        //Return the new model to the HTTP Handlers
        return cm;
    }

}
