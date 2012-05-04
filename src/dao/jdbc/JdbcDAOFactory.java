package dao.jdbc;
import dao.DAOFactory;
import dao.PersonDAO;

public class JdbcDAOFactory extends DAOFactory{
	
	public PersonDAO getPersonDAO(){
		return new PersonJdbcDAO();
	}
}