package server;

import server.database.IDatabase;
import server.database.VolatileDatabase;
import server.exception.FactoryException;
import server.exception.ServerException;
import server.facade.FacadeProxy;
import server.facade.IServerFacade;
import server.facade.MockFacade;
import server.facade.ServerFacade;

/**
 * Created by Joshua on 3/10/2016.
 */
public class DIFactory
{
    private Class<?> facade;
    private Class<?> database;

    public DIFactory()
    {

    }

    public void bind(Class<?> interfaceType, Class<?> concreteType) throws ServerException
    {
        if(interfaceType.equals(IServerFacade.class))
        {
            facade = concreteType;
        }
        else if(interfaceType.equals(IDatabase.class))
        {
            database = concreteType;
        }
        else
            throw new FactoryException("Whatever type it is you just passed me, this factory is not responsible for");
    }

    public void buildFacade() throws ServerException
    {
        if(facade == null || database == null)
            throw new FactoryException("The necessary dependancies to build a facade have not been bound yet... id10t");

        IDatabase database;
        IServerFacade facade;

        if(this.database.equals(VolatileDatabase.class))
            database = new VolatileDatabase();
        else
            throw new FactoryException("Whatever class you bound for IDatabase was not a valid implemented concrete class");

        if(this.facade.equals(ServerFacade.class))
            facade = new ServerFacade(database);
        else if(this.facade.equals(MockFacade.class))
            facade = new MockFacade();
        else
            throw new FactoryException("Whatever class you bound for IServerFacade was not a vlid implemented concrete class");

        FacadeProxy.setFacade(facade);
    }
}
