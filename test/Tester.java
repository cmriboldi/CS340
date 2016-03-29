package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class Tester {

	@Test
	public static void main(String[] args) {
		String[] testClasses = new String[]
		{
			"test.JSONSerializer.TestJSONSerializer","test.server.JSONDeserializerTest","test.server.MockProxyTest","test.server.RealProxyTest","test.model.development.DevCardListTest","test.model.development.DevCardManagerDeserialTest","test.model.development.DevCardManagerTest","test.model.development.PlayerDevCardsTest","test.model.map.TestMap","test.model.options.OptionsTest","test.model.player.PlayerCanDoTest","test.model.player.PlayerManagerDeserializerTest","test.model.resources.ResourceListTest","test.model.resources.ResourceManagerDeserialTest","test.phase3Tests.TestAcceptTradeCommand","test.phase3Tests.TestBuildCityCommand","test.phase3Tests.TestBuildRoadCommand","test.phase3Tests.TestBuyDevCardCommand","test.phase3Tests.TestDiscardCardsCommand","test.phase3Tests.TestFinishTurnCommand","test.phase3Tests.TestMaritimeTradeCommand","test.phase3Tests.TestMonopolyCommand","test.phase3Tests.TestMonumentCommand","test.phase3Tests.TestOfferTradeCommand","test.phase3Tests.TestRegisterCommand","test.phase3Tests.TestRoadBuildingCommand","test.phase3Tests.TestRobPlayerCommand","test.phase3Tests.TestRollNumberCommand","test.phase3Tests.TestSendChatCommand","test.phase3Tests.TestSoldierCommand","test.phase3Tests.TestYearOfPlentyCommand"
		};
		//There is an error with this method when run in eclipse but it's used to run all of our tests from ant.
		org.junit.runner.JUnitCore.main(testClasses);
	}

}
