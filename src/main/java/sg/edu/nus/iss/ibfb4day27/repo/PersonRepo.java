package sg.edu.nus.iss.ibfb4day27.repo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import sg.edu.nus.iss.ibfb4day27.model.Person;

@Repository
public class PersonRepo {

    private MongoTemplate mongoTemplate;

    public PersonRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;

    }

    // day27 - slide 3
    // db.persons.insert({
    // _id: 1, name: "Pang Ching Kuang", age: 18, gender: "M", hobbies: ["gaming",
    // "coding"];
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

    public void deletePerson(Person person) {
        mongoTemplate.remove(person);
    }

    public Person updatePerson(Person person) {
        Person updatedPerson = mongoTemplate.save(person);
        return updatedPerson;
    }

    // day27 - slide 7 & 11
    // example query
    // db.persons.updateOne(
    // { _id: ObjectId("xxxxxxxxxxxxxx") },
    // { $set: { name: "Emily" },
    //   $inc: { age: 1 }}
    // );
    public void findAndUpdatePerson(ObjectId objId, Person person) {
        Query query = Query.query(Criteria.where("_id").is(objId));

        Update updateOperation = new Update()
                .set("name", person.getName())
                .inc("age", 1);

        UpdateResult result = mongoTemplate.updateMulti(query, updateOperation, "persons");

        System.out.printf("Document updated: %d\n", result.getModifiedCount());

    }
}
