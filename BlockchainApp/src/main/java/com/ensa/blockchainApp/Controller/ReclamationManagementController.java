package com.ensa.blockchainApp.Controller;


import com.ensa.blockchainApp.Business.Person;
import com.ensa.blockchainApp.Business.Reclamation;
import com.ensa.blockchainApp.Business.Respondent;
import com.ensa.blockchainApp.Business.Traceability;
import com.ensa.blockchainApp.Core.Block;
import com.ensa.blockchainApp.Repositories.BlockRepository;
import com.ensa.blockchainApp.Repositories.TracabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
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
    public String addReclamation(@ModelAttribute Respondent respondent, @ModelAttribute Reclamation reclamation, @RequestParam("file") MultipartFile file) throws IOException {

        Reclamation newReclamation = new Reclamation (this.person, respondent, reclamation.getObject (), file.getBytes(), new Date (  )  );
        if ( blockRepository.count () == 0) {
             blockRepository.save ( new Block() );
        }

        if (blockRepository.count () == 1 ){ // Insret la premier en 2019  TEST !

              Calendar c = Calendar.getInstance ();
              c.add ( Calendar.DATE,-400 );
              newReclamation.setDate ( c.getTime () );
              newReclamation.setOrderNumber ( 01+"-"+c.get ( Calendar.YEAR ) );
              System.out.println (newReclamation.getOrderNumber () + "== 1 ");
        }
        else {
                String[] lastOrderNumberInChainAndItsYear = blockRepository.findById ( blockRepository.count () ).get ().getData ().getOrderNumber ().split ( "-" );

                String lastOrderNumber = lastOrderNumberInChainAndItsYear[0];
                String itsYear = lastOrderNumberInChainAndItsYear[1];

                if (String.valueOf ( Calendar.getInstance ().get ( Calendar.YEAR ) ).equals ( itsYear )) {
                    newReclamation.setOrderNumber ( Integer.valueOf ( lastOrderNumber ) + 1 + "-" + itsYear );
                } else {
                    newReclamation.setOrderNumber ( 01 + "-" + Calendar.getInstance ().get ( Calendar.YEAR ) );
                }
        }


        Block b = new Block ( newReclamation, blockRepository.findById ( blockRepository.count ()  ).get ().getHash () );
        Block newblock = blockRepository.save ( b );
        tracabilityRepository.save (new Traceability ( newblock.getId (), newReclamation.getOrderNumber (), this.connectedUser, new Date (  ) ));

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

    @GetMapping("/reclamationfile/{id}")
    @ResponseBody
    public ResponseEntity<Resource> downloadReclamationFile(@PathVariable(name = "id") Long id) {
        // Load file from database
        Reclamation r = blockRepository.findById(id).get().getData();
        byte[] file =  r.getReclamation();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + r.getObject() + ".pdf\"")
                .body(new ByteArrayResource(file));
    }

}
