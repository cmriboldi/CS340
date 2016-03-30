package client.points;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import clientfacade.Facade;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, Observer {

	public PointsController(IPointsView view, IGameFinishedView finishedView) {

		super(view);
		Facade.addObserverStatic(this);
		setFinishedView(finishedView);

		if (!Facade.hasGameStarted())
			return;

		initFromModel();
	}

	private void initFromModel() {
		getPointsView().setPoints(Facade.getPlayerPoints());
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!Facade.hasGameStarted())
			return;

		initFromModel();

		int winnerIndex = Facade.getWinner();
		if (winnerIndex != -1) {
			getFinishedView().setWinner(Facade.getPlayerName(winnerIndex), winnerIndex == Facade.getLocalPlayerIndex());
			getFinishedView().showModal();
			// 
		}
	}




	private IGameFinishedView finishedView;
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	public IPointsView getPointsView() {

		return (IPointsView)super.getView();
	}
	
}

