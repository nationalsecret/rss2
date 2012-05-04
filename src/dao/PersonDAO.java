package dao;
import java.util.List;

import model.Person;

public interface PersonDAO {
	List<Person> getAllPersons();
	Person getPersonById(int id);
	List<Person> getPersonsByFirstName(String first_name);
	void insertPerson(Person person);
	void updatePerson(Person person);
	void deletePerson(int id);
}