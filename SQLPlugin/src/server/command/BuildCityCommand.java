package command;


import shared.communication.JSON.BuildCityJSON;
import shared.communication.JSON.IJavaJSON;
import definitions.PieceType;
import shared.exceptions.resources.InvalidPieceTypeException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;
import shared.exceptions.resources.NotEnoughResourcesException;
import server.exception.ServerException;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import model.CatanModel;
import server.AuthToken;
import server.facade.IServerFacade;

public class BuildCityCommand extends ICommand {


    private final IServerFacade facade;

    public BuildCityCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade) {
        super(authToken, jsonBody);
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
            int cityLocX = ((BuildCityJSON)body).getVertexLocation().getX();
            int cityLocY = ((BuildCityJSON)body).getVertexLocation().getY();
            VertexDirection cityLocDir = VertexDirection.toEnum(((BuildCityJSON)body).getVertexLocation().getDirection());
            VertexLocation cityLoc = new VertexLocation(new HexLocation(cityLocX, cityLocY), cityLocDir);

            //Make the change to the model
            cm.getMapManager().upgradeSettlement(cityLoc, ((BuildCityJSON)body).getPlayerIndex());

            cm.playerManager.decrementPieceCount(((BuildCityJSON)body).getPlayerIndex(), PieceType.CITY);
            cm.playerManager.incrementPieceCount(((BuildCityJSON)body).getPlayerIndex(), PieceType.SETTLEMENT);
            cm.resourceManager.buyPiece(((BuildCityJSON)body).getPlayerIndex(), PieceType.CITY);
            cm.playerManager.incrementPlayerPoints(((BuildCityJSON)body).getPlayerIndex());
            cm.chatManager.logAction(cm.playerManager.getPlayerName(((BuildCityJSON)body).getPlayerIndex()) + " built a city.", cm.playerManager.getPlayerName(((BuildCityJSON)body).getPlayerIndex()));
            
            //Update the changed model in the ServerFacade

            


        } catch (ServerException | NotEnoughPlayerResourcesException | InvalidPieceTypeException | NotEnoughResourcesException e) {
            e.printStackTrace();
        }

        //Return the new model to the HTTP Handler
        return cm;
    }
}
