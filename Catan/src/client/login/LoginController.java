package client.login;

import client.base.*;
import client.misc.*;
import clientfacade.Facade;
import serverProxy.ServerException;

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
		System.out.println("Attempting to login");
		
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
			//((ILoginView) getView()).setLoginErrorMessage("INVALID CREDENTIALS");
			messageView.setTitle("Login Failed");
			messageView.setMessage("INVALID CREDENTIALS");
			messageView.showModal();
			e.printStackTrace();
		}
	}

	@Override
	public void register() 
	{	
		System.out.println("Attempting to Register");
		try 
		{
			String username = ((ILoginView) getView()).getRegisterUsername();
			String password = ((ILoginView) getView()).getRegisterPassword();
			String passwordAgain = ((ILoginView) getView()).getRegisterPasswordRepeat();
			if(!password.equals(passwordAgain))
			{
				//((ILoginView) getView()).setRegisterErrorMessage("PASSWORDS DO NOT MATCH");
				messageView.setTitle("Register Failed");
				messageView.setMessage("PASSWORDS DO NOT MATCH");
				messageView.showModal();
				return;
			}
			if(username.equals(""))
			{
				//((ILoginView) getView()).setRegisterErrorMessage("USERNAME CANNOT BE BLANK");
				messageView.setTitle("Register Failed");
				messageView.setMessage("USERNAME CANNOT BE BLANK");
				messageView.showModal();
				return;
			}
			if(password.equals(""))
			{
				//((ILoginView) getView()).setRegisterErrorMessage("PASSWORD CANNOT BE BLANK");
				messageView.setTitle("Register Failed");
				messageView.setMessage("PASSWORD CANNOT BE BLANK");
				messageView.showModal();
				return;
			}
			Facade.register(username, password);
			// If register in succeeded
			System.out.println("Yet I got here in the register code");
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
}

