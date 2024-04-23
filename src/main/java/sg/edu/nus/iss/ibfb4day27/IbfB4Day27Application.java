package sg.edu.nus.iss.ibfb4day27;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.ibfb4day27.model.Person;
import sg.edu.nus.iss.ibfb4day27.repo.PersonRepo;

@SpringBootApplication
public class IbfB4Day27Application implements CommandLineRunner {

	@Autowired
	PersonRepo personRepo;

	public static void main(String[] args) {
		SpringApplication.run(IbfB4Day27Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Person newPerson01 = new Person("Ze Jian", 25, "M", Arrays.asList("Coding", "Drinking", "Snokeling"));
		personRepo.savePerson(newPerson01);

		Person newPerson02 = new Person("Felicia", 25, "F", Arrays.asList("Skateboarding", "Cycling", "Snokeling"));
		personRepo.savePerson(newPerson02);

		Person newPerson03 = new Person("Charan", 25, "F", Arrays.asList("Coding", "Shopping", "Snokeling"));
		personRepo.savePerson(newPerson03);
	}
}
