package client.base;

/**
 * Base class for controllers
 */
public abstract class Controller implements IController
{
	
	private IView view;
	
	protected Controller(IView view)
	{
		System.out.format("Controller:: construct: start");
		setView(view);
	}
	
	private void setView(IView view)
	{
		this.view = view;
	}
	
	@Override
	public IView getView()
	{
		return this.view;
	}
	
}

