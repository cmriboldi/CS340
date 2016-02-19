package client.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.base.*;
import clientfacade.Facade;
import model.CatanModel;
import serverProxy.ServerException;
import shared.definitions.CatanColor;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer {

	public ChatController(IChatView view) {
		
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		try {
			Facade.chat(message);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		List<String> chatMessages = Facade.getChatMessages();
		List<String> chatSources = Facade.getChatSources();
		List<LogEntry> entries = new ArrayList<LogEntry>();
		
		for(int i = 0; (i < chatMessages.size()) && (i < chatSources.size()); i++)
		{
			String message = chatMessages.get(i);
			String source = chatSources.get(i);
			
			CatanColor color = Facade.getColorByName(source);			
			LogEntry entry = new LogEntry(color,message);
			entries.add(entry);
		}
		
		this.getView().setEntries(entries);
	}

}

