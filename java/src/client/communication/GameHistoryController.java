package client.communication;

import java.util.*;

import client.base.*;
import clientfacade.Facade;
import shared.definitions.*;
import shared.exceptions.player.PlayerNameNotFoundException;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, Observer {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();
		Facade.addObserverStatic(this);
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		if(Facade.getCatanModel() != null)
		{
			List<String> historyMessages = Facade.getHistoryMessages();
			List<String> historySources = Facade.getHistorySources();
			List<LogEntry> entries = new ArrayList<LogEntry>();
			
			for(int i = 0; (i < historyMessages.size()) && (i < historySources.size()); i++)
			{
				String message = historyMessages.get(i);
				String source = historySources.get(i);
				CatanColor color = null;
				try {
					color = Facade.getColorByName(source);
				} catch (PlayerNameNotFoundException e) {
					e.printStackTrace();
				}
				LogEntry entry = new LogEntry(color,message);
				entries.add(entry);
			}
			
			this.getView().setEntries(entries);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		List<String> historyMessages = Facade.getHistoryMessages();
		List<String> historySources = Facade.getHistorySources();
		List<LogEntry> entries = new ArrayList<LogEntry>();
		
		for(int i = 0; (i < historyMessages.size()) && (i < historySources.size()); i++)
		{
			String message = historyMessages.get(i);
			String source = historySources.get(i);
			CatanColor color = null;
			try {
				color = Facade.getColorByName(source);
			} catch (PlayerNameNotFoundException e) {
				e.printStackTrace();
			}
			LogEntry entry = new LogEntry(color,message);
			entries.add(entry);
		}
		
		this.getView().setEntries(entries);
	}
	
}

