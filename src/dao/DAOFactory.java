package dao;
import dao.jdbc.JdbcDAOFactory;

public abstract class DAOFactory {
	public static DAOFactory getInstance(){
		return new JdbcDAOFactory();
	}
	public abstract PersonDAO getPersonDAO();
}