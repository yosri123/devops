package tn.esprit.spring.services;

import java.util.Date;
import java.util.List;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;



public interface ITimesheetService {
	
	public Long ajouterMission(Mission mission);
	public void affecterMissionADepartement(Long missionId, int depId);
	public void ajouterTimesheet(Long missionId, int employeId, Date dateDebut, Date dateFin);
	public void validerTimesheet(Long missionId, int employeId, Date dateDebut, Date dateFin, int validateurId);
	public List<Mission> findAllMissionByEmployeJPQL(int employeId);
	public List<Employe> getAllEmployeByMission(Long missionId);
}
