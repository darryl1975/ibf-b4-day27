package sg.edu.nus.iss.ibfb4day27.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    
    @Id
    private String personId;

    private String name;

    private long age;

    private String gender;

    private List<String> hobbies;

}
