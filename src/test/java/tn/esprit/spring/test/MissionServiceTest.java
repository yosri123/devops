package tn.esprit.spring.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.IMissionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MissionServiceTest {
	
	@Autowired
	private IMissionService ms ; 
	
	@Test
	public void testRetrieveAllMissions() {
		List<Mission> listUsers = ms.retrieveAllMissions(); 
		Assert.assertEquals(6, listUsers.size());
	}
	
	@Test
	public void testAddMission() {
		Mission m = new Mission("WebDev", "WebDev"); 
		Mission missionAdded = ms.addMission(m); 
		Assert.assertEquals(m.getName() , missionAdded.getName());
		ms.deleteMission(missionAdded.getId().toString());
	}
	
	@Test
	public void testModifyMission()  {
		Mission m = new Mission(6L, "sss", "sss"); 
		Mission missionUpdated  = ms.updateMission(m); 
		Assert.assertEquals(m.getName(), missionUpdated.getName());
	}
	
	
	@Test
	public void testRetrieveMission() {
		Mission missionRetrieved = ms.retrieveMission("1"); 
		Assert.assertEquals(1L, missionRetrieved.getId().longValue());
	}
	/*
 	@Test
	public void testDeleteMission() {
		ms.deleteMission("16");
		Assert.assertNull(ms.retrieveMission("16"));
		
		
	}
*/
}
