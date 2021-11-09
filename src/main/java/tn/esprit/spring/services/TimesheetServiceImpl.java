package tn.esprit.spring.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
	
	private static final Logger l = LogManager.getLogger(TimesheetServiceImpl.class);

	

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	public Long ajouterMission(Mission mission) {
		missionRepository.save(mission);
		return mission.getId();
	}
    
	public void affecterMissionADepartement(Long missionId, int depId) {
		l.info("lancer la methode affecter mission à une département");
		l.debug("maintenant je vais faire l'affectation");
		Optional<Mission> value = missionRepository.findById(missionId);
		if (value.isPresent()) {
			Mission mission=value.get();
		
	
			Optional<Departement> value1 = deptRepoistory.findById(depId);
			if (value1.isPresent()) {
				Departement dep=value1.get();
			
			
		mission.setDepartement(dep);
		missionRepository.save(mission);
	}}}

	public void ajouterTimesheet(Long missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		
	}

	
	public void validerTimesheet(Long missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		l.info("lancer la methode validerTimesheet");
		l.debug("je vais faire la validation de timesheet par date debut et date fin");
		
		
		Optional<Employe> value = employeRepository.findById(validateurId);
		if (value.isPresent()) {
			Employe validateur=value.get();
		
			Optional<Mission> value1 = missionRepository.findById(missionId);
			if (value1.isPresent()) {
				Mission mission=value1.get();
			
			
		if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
			
			
			l.debug("verification qu'il a le role d'un chef de departement est éfectué");
			l.info("l'employé à le role d'un chef de departement");
			
			return;
		}
		
		boolean chefDeLaMission = false;
		for(Departement dep : validateur.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				
				l.debug("verification qu'il est le chef de departement de la mission en question est éfectué");
				l.info("l'employé est le chef de departement de la mission en question");
				
				break;
			}
		}
		if(!chefDeLaMission){
		
			
			l.debug("verification qu'il est le chef de departement de mission en question n'est éfectué");
			l.info("l'employé n'est pas le chef de departement de la mission en question");
			
			return;
		}

		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		timesheet.setValide(true);
		
		
		l.debug("validation de timesheet est éfectué");
		l.info("fin de la méthode valider timesheet");
		
	}}}

	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}

	@Override
	public List<Employe> getAllEmployeByMission(Long missionId) {
		return timesheetRepository.getAllEmployeByMission(missionId) ;
	}

	
	

}
