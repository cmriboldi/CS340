package server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import server.database.EmptyPlugin;
import server.database.IDatabase;
import server.database.IPersistencePlugin;
import server.database.VolatileDatabase;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import com.google.inject.Module;

/**
 * Created by Clayton on 3/16/2016.
 */
public class VolatileRealModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(IPersistencePlugin.class).to(EmptyPlugin.class).in(Singleton.class);
        bind(IServerFacade.class).to(ServerFacade.class).in(Singleton.class);
        bind(IDatabase.class).to(VolatileDatabase.class).in(Singleton.class);
    }
}
