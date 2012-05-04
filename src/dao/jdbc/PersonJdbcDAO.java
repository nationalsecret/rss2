package dao.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.Person;


import dao.PersonDAO;

public class PersonJdbcDAO implements PersonDAO{
	
	public static Connection getConnection() {
		try {
		    // load the Oracle JDBC Driver
		    Class.forName("com.mysql.jdbc.Driver");
		    // define database connection parameters
		    return DriverManager.getConnection("jdbc:mysql://localhost:3306/java_01", "redy",
		        "qwerty123");
		} catch (ClassNotFoundException ce) {
			// if the driver class not found, then we will be here
			System.out.println(ce.getMessage());
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Person> getAllPersons() {
		Connection conn   = null; // connection object
	    Statement stmt    = null; // statement object
	    ResultSet rs      = null; // result set object
	    List<Person> temp = new ArrayList<Person>();
	    Person actual     = null;
		try{ 
			conn = getConnection(); // without Connection, can not do much
			// create a statement: This object will be used for executing
			// a static SQL statement and returning the results it produces.
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select * from person"); 
			while( rs.next()) { 
				actual = new Person();
				actual.               setId( rs.   getInt("PER_ID"        ) );
				actual.        setFirstName( rs.getString("PER_FIRST_NAME") );
				actual.         setLastName( rs.getString("PER_LAST_NAME" ) );
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime( rs.getDate("PER_BIRTH_DATE") );
				actual.        setBirthDate( gc );
				actual.setWeightInKilograms( rs.getDouble("PER_WEIGHT_KG" ) );
				actual.   setHeightInMeters( rs.getDouble("PER_HEIGHT_M"  ) );
				temp.add(actual);
			}
		} catch( Exception e ){ 
			e.printStackTrace(); 
		} finally { // close db resources
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
			}
	    }
		return temp;
	}
	@Override
	public Person getPersonById(int id) {
		Connection conn = null; // connection object
	    Statement stmt  = null; // statement object
	    ResultSet rs    = null; // result set object
	    Person temp     = new Person();
		try{ 
			conn = getConnection(); // without Connection, can not do much
			// create a statement: This object will be used for executing
			// a static SQL statement and returning the results it produces.
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select distinct * from person where PER_ID = " + id); 
			while( rs.next()) {
				temp.               setId( rs.   getInt("PER_ID"        ) );
				temp.        setFirstName( rs.getString("PER_FIRST_NAME") );
				temp.         setLastName( rs.getString("PER_LAST_NAME" ) );
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime( rs.getDate("PER_BIRTH_DATE") );
				temp.        setBirthDate( gc );
				temp.setWeightInKilograms( rs.getDouble("PER_WEIGHT_KG" ) );
				temp.   setHeightInMeters( rs.getDouble("PER_HEIGHT_M"  ) );
			}
		} catch( Exception e ){ 
			e.printStackTrace(); 
		} finally { // close db resources
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
			}
	    }
		
		return temp;
	}
	@Override
	public List<Person> getPersonsByFirstName(String first_name) {
		Connection conn   = null; // connection object
	    Statement stmt    = null; // statement object
	    ResultSet rs      = null; // result set object
	    List<Person> temp = new ArrayList<Person>();
	    Person actual     = null;
		try{ 
			conn = getConnection(); // without Connection, can not do much
			// create a statement: This object will be used for executing
			// a static SQL statement and returning the results it produces.
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select * from person where PER_FIRST_NAME LIKE '" + first_name + "%'"); 
			while( rs.next()) { 
				actual = new Person();
				actual.               setId( rs.   getInt("PER_ID"        ) );
				actual.        setFirstName( rs.getString("PER_FIRST_NAME") );
				actual.         setLastName( rs.getString("PER_LAST_NAME" ) );
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime( rs.getDate("PER_BIRTH_DATE") );
				actual.        setBirthDate( gc );
				actual.setWeightInKilograms( rs.getDouble("PER_WEIGHT_KG" ) );
				actual.   setHeightInMeters( rs.getDouble("PER_HEIGHT_M"  ) );
				temp.add(actual);
			}
		} catch( Exception e ){ 
			e.printStackTrace(); 
		} finally { // close db resources
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
			}
	    }
		return temp;
	}
	@Override
	public void insertPerson(Person person) {
		Connection conn = null; // connection object
	    Statement  stmt = null; // statement object
	    String     sql  = null;

		try{ 
			conn = getConnection(); // without Connection, can not do much
			// create a statement: This object will be used for executing
			// a static SQL statement and returning the results it produces.
			stmt = conn.createStatement();
			
			conn.setAutoCommit(false);
			sql = "insert into person ( `PER_FIRST_NAME`, `PER_LAST_NAME`, `PER_BIRTH_DATE`, `PER_WEIGHT_KG`, `PER_HEIGHT_M`) values ( \"" 
					+ person.getFirstName()			+ "\", \""
					+ person.getLastName()			+ "\", \""
					+ 
						person.getBirthDate().get(Calendar.YEAR)  + "-" +
						person.getBirthDate().get(Calendar.MONTH) + "-" +
						person.getBirthDate().get(Calendar.DAY_OF_MONTH) 
					+ "\", \""
					+ person.getWeightInKilograms() + "\", \""
					+ person.getHeightInMeters()
					+ "\" )";
			stmt.executeUpdate( sql ); 
			
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// something went wrong, we are handling the exception here
			if (conn != null) {
				try {
					conn.rollback();
					conn.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			System.out.println("--- SQLException caught ---");
			// iterate and get all of the errors as much as possible.
			while (e != null) {
				System.out.println("Message   : " + e.getMessage());
				System.out.println("SQLState  : " + e.getSQLState());
				System.out.println("ErrorCode : " + e.getErrorCode());
				System.out.println(sql);
				System.out.println("---");
				e = e.getNextException();
			}
	    } finally { // close db resources
	    	try {
	    		stmt.close();
	    		conn.close();
	    	} catch (Exception e) {
	    	}
	    }
	}
	@Override
	public void updatePerson(Person person) {
		Connection conn = null; // connection object
	    Statement  stmt = null; // statement object
	    String     sql  = null;

		try{ 
			conn = getConnection(); // without Connection, can not do much
			// create a statement: This object will be used for executing
			// a static SQL statement and returning the results it produces.
			stmt = conn.createStatement();
			
			conn.setAutoCommit(false);
			sql = "UPDATE person SET " 
					+ "PER_FIRST_NAME = \"" + person.getFirstName()         + "\", "
					+ "PER_LAST_NAME = \""  + person.getLastName()          + "\", "
					+ "PER_BIRTH_DATE = \""  + 
						person.getBirthDate().get(Calendar.YEAR)  + "-" +
						person.getBirthDate().get(Calendar.MONTH) + "-" +
						person.getBirthDate().get(Calendar.DAY_OF_MONTH) 
					+ "\", "
					+ "PER_WEIGHT_KG = \""  + person.getWeightInKilograms() + "\", "
					+ "PER_HEIGHT_M = \""     + person.getHeightInMeters() + "\""
					+ " WHERE PER_ID = "  + person.getId() ;
			stmt.executeUpdate( sql ); 
			
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// something went wrong, we are handling the exception here
			if (conn != null) {
				try {
					conn.rollback();
					conn.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			System.out.println("--- SQLException caught ---");
			// iterate and get all of the errors as much as possible.
			while (e != null) {
				System.out.println("Message   : " + e.getMessage());
				System.out.println("SQLState  : " + e.getSQLState());
				System.out.println("ErrorCode : " + e.getErrorCode());
				System.out.println(sql);
				System.out.println("---");
				e = e.getNextException();
			}
	    } finally { // close db resources
	    	try {
	    		stmt.close();
	    		conn.close();
	    	} catch (Exception e) {
	    	}
	    }
	}
	@Override
	public void deletePerson(int id) {
		Connection conn = null; // connection object
	    Statement stmt  = null; // statement object

		try{ 
			conn = getConnection(); // without Connection, can not do much
			// create a statement: This object will be used for executing
			// a static SQL statement and returning the results it produces.
			stmt = conn.createStatement();
			
			conn.setAutoCommit(false);
			
			stmt.executeUpdate( "DELETE FROM person WHERE PER_ID = " + id ); 
			
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// something went wrong, we are handling the exception here
			if (conn != null) {
				try {
					conn.rollback();
					conn.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			System.out.println("--- SQLException caught ---");
			// iterate and get all of the errors as much as possible.
			while (e != null) {
				System.out.println("Message   : " + e.getMessage());
				System.out.println("SQLState  : " + e.getSQLState());
				System.out.println("ErrorCode : " + e.getErrorCode());
				System.out.println("---");
				e = e.getNextException();
			}
	    } finally { // close db resources
	    	try {
	    		stmt.close();
	    		conn.close();
	    	} catch (Exception e) {
	    	}
	    }
	}

}