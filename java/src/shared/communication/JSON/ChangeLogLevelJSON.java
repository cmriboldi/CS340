package shared.communication.JSON;

public class ChangeLogLevelJSON implements IJavaJSON
{
	private String logLevel;

	public ChangeLogLevelJSON(String logLevel)
	{
		this.logLevel = logLevel;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
}
