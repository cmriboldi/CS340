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
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		
		getView().setEntries(entries);
	
		//</temp>
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

