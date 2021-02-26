package com.ensa.blockchainApp.Controller;


import com.ensa.blockchainApp.Business.Contact;
import com.ensa.blockchainApp.Repositories.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
public class ContactController {
    @Autowired
    ContactRepo contactRepo;


    @PostMapping("/addContactMessage")
    public String saveReportedProblem(@ModelAttribute Contact newContact){

        Contact contact = new Contact (  null,newContact.getFirstName (), newContact.getLastName (), newContact.getEmail (),newContact.getObject (),newContact.getMessage (), new Date (  ));
        contactRepo.save (contact);
        return "redirect:/";
    }

    @GetMapping("/contacts/{id}")
    @ResponseBody
    public Contact getReportedProblem(@PathVariable(name = "id") Long id ){

         return contactRepo.findById ( id ).get ()  ;
    }

}
