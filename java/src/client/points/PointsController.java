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
		getPointsView().setPoints(Facade.getCatanModel().getPlayerManager().getCatanPlayers()[localPlayerIndex].getPoints());
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(!Facade.hasGameStarted())
			return;

		initFromModel();
		
		int winnerIndex = Facade.getWinner();
		System.out.println("winner index is: " + winnerIndex);
		if(winnerIndex != -1) {
			System.out.println("local player is: " + Facade.getLocalPlayerIndex());
			System.out.println("winner name is: " + Facade.getPlayerName(winnerIndex));
			getFinishedView().setWinner(Facade.getPlayerName(winnerIndex), winnerIndex == Facade.getLocalPlayerIndex());
			getFinishedView().showModal();
		}
	}
	
}

