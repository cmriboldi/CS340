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
 *
 * The DIFactory will be responsible for maintaining the dependencies of the Catan Server.
 * Particularly for constructing the proper facade for the server build.
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class DIFactory
{
    private Class<?> facade;
    private Class<?> database;

    public DIFactory()
    {

    }

    /**
     * Binds the type of interface with the concrete class that will be used for the build
     * @param interfaceType The interface that will be bound to the concreteType, this should be either IServerFacade or IDatabase
     * @param concreteType The concrete type that will be used to construct the facade
     * @throws ServerException
     */
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

    /**
     * Builds the server facade based on the types bound to it, and then initializes the singleton FacadeProxy with the facade type
     * @throws ServerException
     */
    public void buildFacade() throws ServerException
    {
        if(facade == null || database == null)
            throw new FactoryException("The necessary dependencies to build a facade have not been bound yet... id10t");

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
            throw new FactoryException("Whatever class you bound for IServerFacade was not a valid implemented concrete class");

        FacadeProxy.setFacade(facade);
    }
}
