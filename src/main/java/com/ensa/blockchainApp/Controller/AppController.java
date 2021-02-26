package com.ensa.blockchainApp.Controller;

import com.ensa.blockchainApp.Business.Contact;
import com.ensa.blockchainApp.Business.Person;
import com.ensa.blockchainApp.Business.ReportedProblem;
import com.ensa.blockchainApp.Business.User;
import com.ensa.blockchainApp.Core.Block;
import com.ensa.blockchainApp.Repositories.BlockRepository;
import com.ensa.blockchainApp.Repositories.ContactRepo;
import com.ensa.blockchainApp.Repositories.ReportedProblemRepo;
import com.ensa.blockchainApp.Repositories.TracabilityRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;


@Controller // Spring MVC

// This is the controller which manages the returning views according to the url given !
class AppController{

	@Autowired
	TracabilityRepository tracabilityRepository;
	@Autowired
	BlockRepository blockRepository;
	@Autowired
	ReportedProblemRepo reportedProblemRepo;
	@Autowired
	ContactRepo contactRepo;


	@RequestMapping( "/")
	public  String index() { //permet de retourner la vue index
			return "index";
	}

	@GetMapping("/UsersList")
	public String visualiseUsers(ModelMap map, Model model){
		// TODO(): This should be removed elsewhere.
		UsersManagementController usersManagementController = new UsersManagementController ();
		//Send  the list of users to the view
		map.addAttribute ("Users", usersManagementController.getUsers ());
		User user = new User();
		// create an empty user and send it to the view; we use it for creating a new user
		model.addAttribute ( "user",user );
		return "usersList";
	}
	@GetMapping("/InspectorsList")
	public String visualiseInspectors(ModelMap map, Model model){

		UsersManagementController usersManagementController = new UsersManagementController ();
		//Send the list of inspectors to the view
		map.addAttribute ( "inspectors", usersManagementController.getInspectors ());
		User user = new User();
		// create an empty inspecteur and send it to the view ; we use it for creating a new user
		model.addAttribute ( "inspector",user );
		return "InspectorsList";
	}

	@GetMapping("/tracabilite")
	public String visualiseTracabilite(Model model){
		model.addAttribute ( "tracabilites",tracabilityRepository.findAllByDateDesc ());

		return "tracabilite";
	}

	@GetMapping("/addReclamation")
	public String addReclamation(Model model){
		Person p = new Person (  );
		model.addAttribute ( "p", p );
		return "addReclamationComplainer";
	}

	@GetMapping("/voirReclamations")
	public String voirReclamations(Model model){
		ArrayList<Block> l = (ArrayList<Block>) blockRepository.findAllByDateDesc();
		if(l.size()>1) {
			model.addAttribute("Blocks", l.subList(0, l.size() - 1));
		} else {
			return "index";
		}
		return "voirReclamations";
	}

	@GetMapping("/signaler")

	public String report(Model model){
		model.addAttribute ( "ReportedProblem", new ReportedProblem () );
		return "signaler";
	}
	@GetMapping("/ReportedProblems")
	public String getProblems(Model model){
		model.addAttribute ( "Problems", reportedProblemRepo.findAllByDateDesc () );
		return "Problems";
	}

	@GetMapping("/contact")
	public String contact(Model model){
		model.addAttribute ( "contactMessage", new Contact () );
		return "contact";
	}
	@GetMapping("/visitorsMessges")
	public String getContactMessages(Model model){
		model.addAttribute ( "contactMessages", contactRepo.findAllByDateDesc () );
		return "voirContacts";
	}


}
