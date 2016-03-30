package server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import server.database.IDatabase;
import server.database.VolatileDatabase;
import server.facade.IServerFacade;
import server.facade.MockFacade;

/**
 * Created by Joshua Powers on 3/22/2016.
 */
public class VolatileMockModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(IServerFacade.class).to(MockFacade.class).in(Singleton.class);
        bind(IDatabase.class).to(VolatileDatabase.class).in(Singleton.class);
    }
}