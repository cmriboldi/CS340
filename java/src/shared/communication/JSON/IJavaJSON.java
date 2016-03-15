package shared.communication.JSON;

import shared.definitions.CommandType;

/**
 * Created by jvanstee on 3/11/2016.
 */
public abstract class IJavaJSON
{
    private String type;

    public IJavaJSON(String type)
    {
        this.type = type;
    }

    public CommandType getCommand()
    {
        return CommandType.toCommand(type);
    }

    public static Class<?> getTypeFromURI(String uri)
    {
        Class<?> type;
        switch(uri)
        {
            case "/moves/sendChat":
                type = SendChatJSON.class;
                break;

            case "/moves/rollNumber":
                type = RollNumberJSON.class;
                break;

            case "/moves/robPlayer":
                type = RobPlayerJSON.class;
                break;

            case "/moves/finishTurn":
                type = FinishTurnJSON.class;
                break;

            case "/moves/buyDevCard":
                type = BuyDevCardJSON.class;
                break;

            case "/moves/Year_of_Plenty":
                type = YearOfPlentyJSON.class;
                break;

            case "/moves/Road_Building":
                type = RoadBuildingJSON.class;
                break;

            case "/moves/Soldier":
                type = SoldierJSON.class;
                break;

            case "/moves/Monopoly":
                type = MonopolyJSON.class;
                break;

            case "/moves/Monument":
                type = MonumentJSON.class;
                break;

            case "/moves/buildRoad":
                type = BuildRoadJSON.class;
                break;

            case "/moves/buildSettlement":
                type = BuildSettlementJSON.class;
                break;

            case "/moves/buildCity":
                type = BuildCityJSON.class;
                break;

            case "/moves/offerTrade":
                type = OfferTradeJSON.class;
                break;

            case "/moves/acceptTrade":
                type = AcceptTradeJSON.class;
                break;

            case "/moves/maritimeTrade":
                type = MaritimeTradeJSON.class;
                break;

            case "/moves/discardCards":
                type = DiscardCardsJSON.class;
                break;

            default:
                type = null;
        }
        return type;
    }
}
