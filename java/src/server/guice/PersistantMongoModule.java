package server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import plugin.MongoDB.MongoPlugin;
import server.database.*;
import server.facade.IServerFacade;
import server.facade.ServerFacade;

/**
 * Created by Joshua Powers on 3/22/2016.
 */
public class PersistantMongoModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(IPluginClass.class).to(PluginClass.class).in(Singleton.class);
        bind(IPersistencePlugin.class).to(MongoPlugin.class).in(Singleton.class);

        bind(IServerFacade.class).to(ServerFacade.class).in(Singleton.class);
        bind(IDatabase.class).to(VolatileDatabase.class).in(Singleton.class);
    }
}