package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.BuildSettlementJSON;
import shared.communication.JSON.IJavaJSON;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class BuildSettlementCommand implements ICommand {

    private AuthToken authToken = null;
    private BuildSettlementJSON body = null;
    private final IServerFacade facade;

    public BuildSettlementCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade) {
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

        CatanModel cm = null;
        try {
            //Retrieve the game model
            cm = facade.getGameModel(authToken);

            //Translate from JSONbody into a Java settlement location
            int settleLocX = body.getVertexLocation().getX();
            int settleLocY = body.getVertexLocation().getY();
            VertexDirection settleLocDir = VertexDirection.valueOf(body.getVertexLocation().getDirection());
            VertexLocation settleLoc = new VertexLocation(new HexLocation(settleLocX, settleLocY), settleLocDir);

            //Make change to the model
            cm.getMapManager().placeSettlement(settleLoc, body.getPlayerIndex());

            //Update the model with the facade
            facade.updateGame(authToken, cm);

        } catch (ServerException e) {
            e.printStackTrace();
        }

        //Return the new model to the HTTP Handlers
        return cm;
    }

}
