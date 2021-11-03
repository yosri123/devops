package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Mission;

public interface IMissionService  {
	
	public List<Mission> retrieveAllMissions(); 
	public Mission addMission(Mission m) ; 
	public Mission updateMission(Mission m) ; 
	public void deleteMission(String id) ; 
	public Mission retrieveMission(String id) ;

}
