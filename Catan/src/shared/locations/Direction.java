package shared.locations;

/**
 * Created by clayt on 2/1/2016.
 */
public class Direction {

    public static String shortToLong(String short_t){

        if(short_t.equals("N"))
            return "North";

        if(short_t.equals("NE"))
            return "NorthEast";

        if(short_t.equals("E"))
            return "East";

        if(short_t.equals("SE"))
            return "SouthEast";

        if(short_t.equals("S"))
            return "South";

        if(short_t.equals("SW"))
            return "SouthWest";

        if(short_t.equals("W"))
            return "West";

        if(short_t.equals("NW"))
            return "NorthWest";

        return null;

    }
}
