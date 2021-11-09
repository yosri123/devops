package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	
	
	private static final Logger l = LogManager.getLogger(EntrepriseServiceImpl.class);
	
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
				
				Optional<Entreprise> value =  entrepriseRepoistory.findById(entrepriseId) ;
				Optional<Departement> value2 = deptRepoistory.findById(depId) ;
				if(value.isPresent() && value2.isPresent() ) {
				Entreprise entrepriseManagedEntity = value.get();
				Departement depManagedEntity = value2.get();
				
				depManagedEntity.setEntreprise(entrepriseManagedEntity);
				deptRepoistory.save(depManagedEntity); }
				
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		l.info("lancer  la methode get all department names by entreprise");
		l.debug("lancer  la recherche de l entreprise par id");
		Optional<Entreprise> value = entrepriseRepoistory.findById(entrepriseId);
		if (value.isPresent()) 
		
		{Entreprise entrepriseManagedEntity= value.get();
			
		
		List<String> depNames = new ArrayList<>();
		l.debug("je vais lancer  la boucle sur tous les departements et ajouter le nom du departementt au tableau depNames");
		
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
		}
		
		l.debug("je viens de remplir le tableau depNames");
		l.info("fin de   la methode get all department names by entreprise");
		return depNames;
		}
		else
		{l.debug("l'entreprisee n'existe pas");
		l.info("fin de   la methode get all department names by entreprise");
		
		return new ArrayList<>();
		}
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		Optional<Entreprise> value =  entrepriseRepoistory.findById(entrepriseId) ;
		if(value.isPresent()) {
		entrepriseRepoistory.delete(value.get());
		}
		else
			l.debug("Not found");
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		
		Optional<Departement> value2 = deptRepoistory.findById(depId) ;
		if(value2.isPresent()) {
		deptRepoistory.delete(value2.get());	
	}
		else
			l.debug("Not found");
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		l.info("lancer  la methode get entreprise by id");
		l.debug("je vais lancer  la recherche de l'entreprise par id");
		Optional<Entreprise> value = entrepriseRepoistory.findById(entrepriseId);
		if (value.isPresent()) {
			Entreprise ent=value.get();
			
		
		l.info("fin de   la methode get entreprise by id");
		 return ent;
		}
		else {l.debug("l'entreprise n'existeee pas");
		l.info("fin de   la methode get entreprise by id"); 
		return null;}
	}
}
