package com.ensa.blockchainApp;

import com.ensa.blockchainApp.Business.Person;
import com.ensa.blockchainApp.Business.Reclamation;
import com.ensa.blockchainApp.Business.Respondent;
import com.ensa.blockchainApp.Core.Block;
import com.ensa.blockchainApp.Repositories.BlockRepository;
import org.apache.james.mime4j.parser.RecursionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;


@SpringBootApplication
public class BlockchainAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainAppApplication.class, args);
	}

	@Autowired
	BlockRepository blockRepository;

	@Override
	public void run(String... args) throws Exception {

		/*Person p = new Person ( "Homme", "Abdou", "Ahbane", "JC762", "aa@uca.ma"," Aoulouz, taroudant","0608632756" );
		Respondent r = new Respondent ( "Mounib", "Elboujbaoui", "Sidi ifni" );
		Reclamation reclamation = new Reclamation ( p,r," He owes me 1M $ HaHa !"," url...",new Date (  ) );

		blockRepository.save(new Block (  ));

		Block b = new Block ( reclamation, blockRepository.findById ( blockRepository.count ()  ).get ().getHash () );

		blockRepository.save ( b );

		Block bb = new Block ( reclamation, blockRepository.findById ( blockRepository.count ()  ).get ().getHash () );

		blockRepository.save ( bb );

		blockRepository.findAll ().forEach ( System.out::println );
		System.out.println ("****************************");
		Reclamation recl = blockRepository.findById ( b.getId () ).get ().getData ();
		System.out.println ("Reclamation : ********* ");
		System.out.println (recl);
		*/
		System.out.println ("Hello World !");
	}


}

