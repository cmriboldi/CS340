package client.login;

import client.base.*;
import client.misc.*;
import clientfacade.Facade;
import serverProxy.ServerException;

import java.net.UnknownHostException;

//import java.net.*;
//import java.io.*;
//import java.util.*;
//import java.lang.reflect.*;
//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	private IMessageView messageView;
	private IAction loginAction;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() 
	{	
		// TODO: log in user
		try 
		{
			String username = ((ILoginView) getView()).getLoginUsername();
			String password = ((ILoginView) getView()).getLoginPassword();
			Facade.login(username, password);
			// If log in succeeded
			getLoginView().closeModal();
			loginAction.execute();
		} 
		catch (ServerException e) 
		{
			if(e.getMessage() == "SERVER NOT RESPONDING")
			{
				messageView.setTitle("Login Failed");
				messageView.setMessage(e.getMessage());
				messageView.showModal();
				e.printStackTrace();
			}
			else
			{
				messageView.setTitle("Login Failed");
				messageView.setMessage("INVALID CREDENTIALS");
				messageView.showModal();
				e.printStackTrace();
			}
		}
	}

	@Override
	public void register() 
	{
		try 
		{
			String username = ((ILoginView) getView()).getRegisterUsername();
			String password = ((ILoginView) getView()).getRegisterPassword();
			String passwordAgain = ((ILoginView) getView()).getRegisterPasswordRepeat();
			if(!password.equals(passwordAgain))
			{
				messageView.setTitle("Register Failed");
				messageView.setMessage("PASSWORDS DO NOT MATCH");
				messageView.showModal();
				return;
			}
			if(!validUsername(username))
			{
				messageView.setTitle("Register Failed");
				messageView.setMessage("USERNAME MUST BE BETWEEN 3 AND 7 CHARACTERS IN LENGTH");
				messageView.showModal();
				return;
			}
			if(password.equals(""))
			{
				messageView.setTitle("Register Failed");
				messageView.setMessage("PASSWORD CANNOT BE BLANK");
				messageView.showModal();
				return;
			}
			if(!validPassword(password))
			{
				messageView.setTitle("Register Failed");
				messageView.setMessage("PASSWORD MUST HAVE AT LEAST 5 DIGITS AND ONLY A-B, 0-9, -, OR _");
				messageView.showModal();
				return;
			}
			Facade.register(username, password);
			// If register in succeeded
			getLoginView().closeModal();
			loginAction.execute();
		} 
		catch (ServerException e) 
		{
			messageView.setTitle("Register Failed");
			messageView.setMessage("USERNAME ALREADY IN USE");
			messageView.showModal();
			e.printStackTrace();
		}
	}

	private boolean validPassword(String password)
	{
		if(password.length() < 5)
			return false;
		for(int i = 0; i < password.length(); i++)
		{
			String a = password.charAt(i) + "";
			if(!(a.matches("[a-zA-Z]+") || a.matches("\\d") || a.matches("-") || a.matches("_")))
			{
				return false;
			}
		}
		return true;
	}

	private boolean validUsername(String username)
	{
		if(username.length() < 3 || username.length() > 7)
			return false;
		return true;
	}
}

