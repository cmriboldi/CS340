package client.points;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import clientfacade.Facade;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, Observer {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		Facade.addObserverStatic(this);
		setFinishedView(finishedView);

		if(!Facade.hasGameStarted())
			return;

		initFromModel();
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		int localPlayerIndex = Facade.getLocalPlayerIndex();

		int player0Points = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[0].getPoints();
		int player1Points = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[1].getPoints();
		int player2Points = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[2].getPoints();
		int player3Points = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[3].getPoints();

		getPointsView().setPoints(Facade.getCatanModel().getPlayerManager().getCatanPlayers()[localPlayerIndex].getPoints());
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(!Facade.hasGameStarted())
			return;

		initFromModel();
	}
	
}

