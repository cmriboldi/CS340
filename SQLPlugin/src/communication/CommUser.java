package communication;

/**
 * Communication object transferring information on users
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class CommUser
{
	private String username;
	private String password;

	public CommUser()
	{

	}
	
	public CommUser(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
