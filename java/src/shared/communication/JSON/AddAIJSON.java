package shared.communication.JSON;

public class AddAIJSON
{
	private String AIType;
	
	public AddAIJSON()
	{
		
	}
	
	public AddAIJSON(String AIType)
	{
		this.AIType = AIType;
	}

	public String getAIType()
	{
		return AIType;
	}

	public void setAIType(String aIType) 
	{
		AIType = aIType;
	}
}
