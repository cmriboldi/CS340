package client.roll;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import clientfacade.Facade;
import serverProxy.ServerException;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

    private IRollResultView resultView;

    /**
     * RollController constructor
     *
     * @param view       Roll view
     * @param resultView Roll result view
     */
    public RollController(IRollView view, IRollResultView resultView) {

        super(view);

        setResultView(resultView);
        Facade.addObserverStatic(this);
    }

    public IRollResultView getResultView() {
        return resultView;
    }

    public void setResultView(IRollResultView resultView) {
        this.resultView = resultView;
    }

    public IRollView getRollView() {
        return (IRollView) this.getView();
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

    @Override
    public void update(Observable o, Object arg) {
    	
    	
    	
        if (Facade.getTurnStatus().equals("Rolling") && Facade.isMyturn()) {
        	
        	if (Facade.getMaxPoints() >= 10) 
    		{
    		return; // game is already over
    		}
        	
        	
            if (!getRollView().isModalShowing()) {
                getRollView().showModal();
            }

        }

    }

}

