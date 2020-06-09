package com.ensa.blockchainApp.Controller;


import com.ensa.blockchainApp.Business.Person;
import com.ensa.blockchainApp.Business.Reclamation;
import com.ensa.blockchainApp.Business.Respondent;
import com.ensa.blockchainApp.Business.Traceability;
import com.ensa.blockchainApp.Core.Block;
import com.ensa.blockchainApp.Repositories.BlockRepository;
import com.ensa.blockchainApp.Repositories.TracabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class ReclamationManagementController {

    private  Person person;
    private String connectedUser;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private TracabilityRepository tracabilityRepository;

    @PostMapping("/addPerson")
    public String addPerson(@ModelAttribute Person p, ModelMap map){
        this.person = p;
        this.connectedUser = SecurityContextHolder.getContext().getAuthentication().getName ();

        Respondent respondent = new Respondent (  );
        Reclamation reclamation = new Reclamation (  );
        map.addAttribute (  "respondent", respondent);
        map.addAttribute ( "reclamation", reclamation );
        return "addReclamationRespondent";
    }

    @PostMapping("/saveReclamation")
    public String addReclamation( @ModelAttribute Respondent respondent, @ModelAttribute Reclamation reclamation){

        Reclamation newReclamation = new Reclamation (this.person, respondent, reclamation.getObject (), reclamation.getReclamation (), new Date (  )  );

        if ( blockRepository.count () == 0) {
            Block b = new Block ( );
            b.setData ( newReclamation );

            System.out.println (b.getData ());

            Block newblock = blockRepository.save ( b );
            tracabilityRepository.save ( new Traceability ( newblock.getId (), this.connectedUser, new Date (  )));
        }

        else{
            Block b = new Block ( newReclamation, blockRepository.findById ( blockRepository.count ()  ).get ().getHash () );
            Block newblock = blockRepository.save ( b );
            System.out.println (b.getData ());
            tracabilityRepository.save (new Traceability ( newblock.getId (), this.connectedUser, new Date (  ) ));
        }

        return "redirect:/";
    }

    @GetMapping("/reclamation/{id}")
    @ResponseBody
    public Reclamation getBlock(@PathVariable(name = "id") Long id){
        if (blockRepository.existsById ( id ) ){
            return blockRepository.findById ( id ).get ().getData ();
        }
        return null;
    }


}
