package sg.edu.nus.iss.ibfb4day27.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

import sg.edu.nus.iss.ibfb4day27.model.Person;

@Service
public class PersonService {
    
    @Autowired
    MongoTemplate mongoTemplate;

    public UpdateResult updatePerson(String conditionKey, String conditionValue, Person personRecord, String collectionName) {
        // day 27 - slide 13
		Query query = new Query(Criteria.where(conditionKey).is(conditionValue));

		Update updateOps = new Update()
				.set("name", personRecord.getName())
                .set("age", personRecord.getAge())
				.push("hobbies").each(personRecord.getHobbies());

		UpdateResult updateResult = mongoTemplate.upsert(query, updateOps, collectionName);

        return updateResult;
    }
}
