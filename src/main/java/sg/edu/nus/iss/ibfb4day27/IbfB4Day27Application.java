package sg.edu.nus.iss.ibfb4day27;

import java.util.List;

import javax.print.Doc;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import sg.edu.nus.iss.ibfb4day27.model.Person;
import sg.edu.nus.iss.ibfb4day27.repo.PersonRepo;

@SpringBootApplication
public class IbfB4Day27Application implements CommandLineRunner {

	@Autowired
	PersonRepo personRepo;

	@Autowired
	MongoTemplate mt;

	public static void main(String[] args) {
		SpringApplication.run(IbfB4Day27Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Person newPerson01 = new Person("Ze Jian", 25, "M", Arrays.asList("Coding",
		// "Drinking", "Snokeling"));
		// personRepo.savePerson(newPerson01);

		// Person newPerson02 = new Person("Felicia", 25, "F",
		// Arrays.asList("Skateboarding", "Cycling", "Snokeling"));
		// personRepo.savePerson(newPerson02);

		// Person newPerson03 = new Person("Charan", 25, "F", Arrays.asList("Coding",
		// "Shopping", "Snokeling"));
		// personRepo.savePerson(newPerson03);

		// Person newPerson04 = new Person("Emily", 25, "F", Arrays.asList("Coding",
		// "Drinking", "Snokeling"));
		// personRepo.savePerson(newPerson01);

		// ObjectId objKey = new ObjectId("xxxxxx");

		// day 27 - slide 13
		Query query = new Query(Criteria.where("name").is("Emily"));

		Update updateOps = new Update()
				.set("name", "Emily Ng")
				.push("hobbies").each(List.of("movies", "prawning").toArray());

		UpdateResult updateResult = mt.upsert(query, updateOps, "persons");

		System.out.println("Slide 13 Results: " + updateResult.getModifiedCount());

		// day 27 - slide 19
		TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingPhrase("Emily ng");

		TextQuery textQuery = TextQuery.queryText(textCriteria);

		List<Person> results = mt.find(textQuery, Person.class, "persons");

		System.out.println("Day 27 Slide 19: " + results.toString());

		// day 28 - slide 9
		MatchOperation matchOperation = Aggregation.match(Criteria.where("Rated").is("PG"));

		// day 28 - slide 11
		ProjectionOperation projectOperations = Aggregation.project("Title", "Year", "Rated", "Released")
				.andExclude("_id");

		// Aggregation pipeline = Aggregation.newAggregation(matchOperation);
		Aggregation pipeline = Aggregation.newAggregation(matchOperation, projectOperations);

		AggregationResults<Document> aggregationResults = mt.aggregate(pipeline, "movies", Document.class);

		List<Document> documentResults = aggregationResults.getMappedResults();

		System.out.println("Day 28 Slide 9, 11: " + documentResults.toString());

		// day 28 - slide 15
		GroupOperation groupOperation = Aggregation.group("Rated")
				.count().as("countA")
				.push("Title").as("titles");

		SortOperation sortOperation = Aggregation.sort(Sort.by(Direction.ASC, "countA"));

		Aggregation pipelineSlide15 = Aggregation.newAggregation(groupOperation, sortOperation);

		AggregationResults<Document> resultSlide15 = mt.aggregate(pipelineSlide15, "movies", Document.class);

		List<Document> documentSlide15 = resultSlide15.getMappedResults();

		System.out.println("day 28 slide 15: " + documentSlide15);
	}
}
