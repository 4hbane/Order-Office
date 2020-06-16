package com.ensa.blockchainApp.Controller;


import com.ensa.blockchainApp.Business.ReportedProblem;
import com.ensa.blockchainApp.Repositories.ReportedProblemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
public class ReportedProblemController {
    @Autowired
    ReportedProblemRepo reportedProblemRepo;


    @PostMapping("/addProblem")
    public String saveReportedProblem(@ModelAttribute ReportedProblem newProblem){
         Authentication authentication = SecurityContextHolder.getContext ().getAuthentication ();
        ReportedProblem problem = new ReportedProblem (  null,authentication.getName (), newProblem.getObject (), newProblem.getMessage (), new Date (  ));
        reportedProblemRepo.save (problem);
        return "redirect:/";
    }

    @GetMapping("/problem/{id}")
    @ResponseBody
    public ReportedProblem getReportedProblem(@PathVariable(name = "id") Long id ){

         return reportedProblemRepo.findById ( id ).get ()  ;
    }

}
