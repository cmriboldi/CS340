package plugin;

import com.google.inject.Inject;
import server.database.*;
import server.exception.DatabaseException;
import server.facade.IServerFacade;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by clayt on 4/8/2016.
 */
public class ReflexivePlugin implements IPersistencePlugin {

    private IServerFacade facade;
    private Class classInstance = null;
    private Object constructedInstance = null;


    @Inject
    public ReflexivePlugin(IPluginClass pluginClass_p, IServerFacade facade_p)
    {
        facade = facade_p;

        try {
            classInstance = pluginClass_p.getClassType();
            Constructor pluginConstructor = classInstance.getDeclaredConstructor(IServerFacade.class);
            constructedInstance = pluginConstructor.newInstance(facade);

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startTransaction() throws DatabaseException {
        try{
            Method startTransaction = classInstance.getDeclaredMethod("startTransaction");
            Object result = startTransaction.invoke(constructedInstance);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endTransaction(boolean commit) throws DatabaseException {
        try{
            Method endTransaction = classInstance.getDeclaredMethod("endTransaction");
            Object result = endTransaction.invoke(constructedInstance);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() throws DatabaseException {
        try{
            Method clear = classInstance.getDeclaredMethod("clear");
            Object result = clear.invoke(constructedInstance);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void thaw() throws DatabaseException {
        try{
            Method thaw = classInstance.getDeclaredMethod("thaw");
            Object result = thaw.invoke(constructedInstance);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IGameDAO getGameDAO() throws DatabaseException {
        Method getGameDAO;
        Object result = null;

        try{
            getGameDAO = classInstance.getDeclaredMethod("getGameDAO");
            result = getGameDAO.invoke(constructedInstance);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return (IGameDAO)result;
    }

    @Override
    public IUserDAO getUserDAO() throws DatabaseException {
        Method getUserDAO;
        Object result = null;

        try{
            getUserDAO = classInstance.getDeclaredMethod("getUserDAO");
            result = getUserDAO.invoke(constructedInstance);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return (IUserDAO)result;
    }

    @Override
    public ICommandDAO getCommandDAO() throws DatabaseException {
        Method getCommandDAO;
        Object result = null;

        try{
            getCommandDAO = classInstance.getDeclaredMethod("getCommandDAO");
            result = getCommandDAO.invoke(constructedInstance);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return (ICommandDAO)result;
    }
}
