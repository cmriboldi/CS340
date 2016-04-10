package server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import plugin.EmptyPlugin;
import plugin.IPluginData;
import plugin.PluginData;
import server.database.IDatabase;
import plugin.IPersistencePlugin;
import server.database.VolatileDatabase;
import server.facade.IServerFacade;
import server.facade.ServerFacade;

/**
 * Created by Clayton on 3/16/2016.
 */
public class VolatileRealModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(IPluginData.class).to(PluginData.class).in(Singleton.class);
        bind(IPersistencePlugin.class).to(EmptyPlugin.class).in(Singleton.class);
        bind(IServerFacade.class).to(ServerFacade.class).in(Singleton.class);
        bind(IDatabase.class).to(VolatileDatabase.class).in(Singleton.class);
    }
}
