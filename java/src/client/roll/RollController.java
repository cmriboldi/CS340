package client.roll;

import client.base.*;
import clientfacade.Facade;
import serverProxy.ServerException;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
		setResultView(resultView);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {

		try {
			this.resultView.setRollValue(Facade.roll());
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getResultView().showModal();
	}

}
