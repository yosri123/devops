package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {
	
	private static final Logger l = LogManager.getLogger(EmployeServiceImpl.class);

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Optional<Employe> value = employeRepository.findById(employeId);
		if(value.isPresent()) {
		Employe employe = value.get();
		employe.setEmail(email);
		employeRepository.save(employe);
		}
		else 
			l.debug("Employe not found !");

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Optional<Employe> value = employeRepository.findById(employeId);
		Optional<Departement> value2 = deptRepoistory.findById(depId);
		if(value.isPresent() &&  value2.isPresent() ) {
		Departement depManagedEntity = value2.get();
		Employe employeManagedEntity =value.get();

		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);

		}}
		else
			l.debug("Can't affect");

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{	
		Optional<Departement> value = deptRepoistory.findById(depId);
		if (value.isPresent()) {
		Departement dep = value.get();

		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;//a revoir
			}
		}}
		else 
			l.debug("Not Found !");
		
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Optional<Employe> value = employeRepository.findById(employeId) ;
		Optional<Contrat> value1 = contratRepoistory.findById(contratId) ;
		if (value1.isPresent() && value.isPresent()) {
		Contrat contratManagedEntity = value1.get();
		Employe employeManagedEntity = value.get();

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		}
		else
			l.debug("Not Found !") ;
		
	}

	public String getEmployePrenomById(int employeId) {
		Optional<Employe> value = employeRepository.findById(employeId);
		if(value.isPresent()) {
		Employe employeManagedEntity = value.get();
		return employeManagedEntity.getPrenom();
		}
		else
			l.debug("No employee found !");
		return null ;
	}

	public void deleteEmployeById(int employeId)
	{	
		
		
		Optional<Employe> value = employeRepository.findById(employeId);
		if (value.isPresent()) {
		Employe employe = value.get();
		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}
		employeRepository.delete(employe); }
		else {
			l.debug("Employe not found !");
		}
	}

	public void deleteContratById(int contratId) {
		Optional<Contrat> value = contratRepoistory.findById(contratId) ;
		if (value.isPresent()) {
		Contrat contratManagedEntity = value.get();
		contratRepoistory.delete(contratManagedEntity);
		}
		else
			l.debug("Your desired item is not found");

	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
         employeRepository.deleteAllContratJPQL();
	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

}
