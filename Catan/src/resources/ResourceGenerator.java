package resources;

/** The ResourceGenerator has the responsibility of generating resources for all Players each roll.
* @author Christian Riboldi
* @author Clayton Condie
* @author Jacob Brewer
* @author Joshua Powers
* @author Joshua Van Steeter
* @version 1.0 Build Jan, 2016.
*/

public class ResourceGenerator 
{

	public ResourceGenerator()
	{
		
	}
	
	/**
	 * This function is the internal implementation for generating resources.
	 * @return An array of ResourceLists were the index of the resource list matches the player index to whom those resources belong.
	 */
	public ResourceList[] generateResources()
	{
		ResourceList[] newPlayerResources = new ResourceList[4];
		return newPlayerResources;
	}
	
}
