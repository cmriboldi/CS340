package shared.communication.JSON;

public class LoadJSON implements IJavaJSON
{
	private String name;
	
	public LoadJSON()
	{
		
	}
	
	public LoadJSON(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
