package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.BuildCityJSON;
import shared.communication.JSON.IJavaJSON;
import shared.definitions.PieceType;
import shared.exceptions.resources.InvalidPieceTypeException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;
import shared.exceptions.resources.NotEnoughResourcesException;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class BuildCityCommand implements ICommand {

    private AuthToken authToken = null;
    private BuildCityJSON body = null;
    private final IServerFacade facade;

    public BuildCityCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade) {
        this.authToken = authToken;
        this.body = (BuildCityJSON) jsonBody;
        this.facade = facade;
    }

    /**
     * Updates the CatanModel to reflect the building of a city
     *
     * @return CatanModel, return the updated Catan model
     */
    @Override
    public Object execute() {

        CatanModel cm = null;
        try {
            //Retrieve the game mode designated in the authToken from the Server Facade
            cm = facade.getGameModel(authToken);

            //Translate from JSONbody into a Java city location
            int cityLocX = body.getVertexLocation().getX();
            int cityLocY = body.getVertexLocation().getY();
            VertexDirection cityLocDir = VertexDirection.toEnum(body.getVertexLocation().getDirection());
            VertexLocation cityLoc = new VertexLocation(new HexLocation(cityLocX, cityLocY), cityLocDir);

            //Make the change to the model
            cm.getMapManager().upgradeSettlement(cityLoc, body.getPlayerIndex());

            cm.playerManager.decrementPieceCount(this.body.getPlayerIndex(), PieceType.CITY);
            cm.playerManager.incrementPieceCount(this.body.getPlayerIndex(), PieceType.SETTLEMENT);
            cm.resourceManager.buyPiece(this.body.getPlayerIndex(), PieceType.CITY);
            cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " built a city.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
            
            //Update the changed model in the ServerFacade
            facade.updateGame(authToken, cm);
            
            facade.recordCommand(authToken, this);

        } catch (ServerException | NotEnoughPlayerResourcesException | InvalidPieceTypeException | NotEnoughResourcesException e) {
            e.printStackTrace();
        }

        //Return the new model to the HTTP Handler
        return cm;
    }
}
