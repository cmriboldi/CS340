package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class Tester {

	@Test
	public static void main(String[] args) {
        //"test.model.map.TestMapManager",
		String[] testClasses = new String[]
		{
			"test.server.JSONDeserializerTest","test.server.MockProxyTest","test.server.PollerTest","test.server.RealProxyTest","test.model.development.DevCardListTest","test.model.development.DevCardManagerDeserialTest","test.model.development.DevCardManagerTest","test.model.development.PlayerDevCardsTest","test.model.map.TestMap","test.model.options.OptionsTest","test.model.player.PlayerCanDoTest","test.model.player.PlayerManagerDeserializerTest","test.model.resources.ResourceListTest","test.model.resources.ResourceManagerDeserialTest"
		};
		
		org.junit.runner.JUnitCore.main(testClasses);
	}

}
