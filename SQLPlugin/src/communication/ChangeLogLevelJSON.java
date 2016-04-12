package communication;

public class ChangeLogLevelJSON
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
