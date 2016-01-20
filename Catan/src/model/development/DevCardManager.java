package model.development;

/** The DevCardManager handles all interactions with development cards.
* @author Christian Riboldi
* @author Clayton Condie
* @author Jacob Brewer
* @author Joshua Powers
* @author Joshua Van Steeter
* @version 1.0 Build Jan, 2016.
*/
public class DevCardManager 
{

	private DevCardList devCardStack;
	
	public DevCardManager()
	{
		
	}
	
	/**
	 * This function will return a DevCardList with a randomly generated card from the devCardStack.
	 * @return A DevCardList with a random card contained inside.
	 */
	public DevCardList drawCard()
	{
		DevCardList devCard = new DevCardList();
		return devCard;
	}
	
}
