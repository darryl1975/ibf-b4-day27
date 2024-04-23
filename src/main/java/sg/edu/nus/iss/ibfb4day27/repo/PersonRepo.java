package sg.edu.nus.iss.ibfb4day27.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.ibfb4day27.model.Person;

@Repository
public class PersonRepo {

    private final MongoTemplate mongoTemplate;


    public PersonRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;

    }
    
    // day27 - slide 3
    // db.persons.insert({
    //     _id: 1, name: "Pang Ching Kuang", age: 18, gender: "M", hobbies: ["gaming", "coding"]; 
    // })
    public Person insertPerson(Person person) {
        Person insertedPerson = mongoTemplate.insert(person);
        return insertedPerson;
    }

    public Person savePerson(Person person) {
        Person savedPerson = mongoTemplate.save(person);
        return savedPerson;
    }

    public List<Person> findAll() {
        return mongoTemplate.findAll(Person.class);
    }

    

}
