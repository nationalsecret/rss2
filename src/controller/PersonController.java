package controller;

import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import view.Frame;
import view.MenuFrame;
import dao.DAOFactory;
import dao.PersonDAO;

public class PersonController {

	/**
	 * @param args
	 */
	private DAOFactory df = null; 
	private PersonDAO  pd = null;
	
	private Locale currentlocale;
	private ResourceBundle messages;
	
	private Display display;
	private Shell shell;
	
	public PersonController() {
		
		display = new Display();
		shell = new Shell(display);
		
		new MenuFrame(display, shell, this);
		
		while (!shell.isDisposed ()) 
		{
			if (!display.readAndDispatch ()) 
				display.sleep ();
		}
	}
	
	public void initFrame(String lang, String reg) {
		
		try {
			currentlocale = new Locale(lang,reg);
		} catch (Exception ex) {
			currentlocale = Locale.getDefault();
		}
		messages = ResourceBundle.getBundle("MessagesBundle", currentlocale);
		
		df   = DAOFactory.getInstance();
		pd   = df.getPersonDAO();
		
		shell.dispose();
		shell = new Shell(display);
		
		new Frame(display,shell,this);
		
		while (!shell.isDisposed ()) 
		{
			if (!display.readAndDispatch ()) 
				display.sleep ();
		}
	}
	
	public PersonDAO getPd() {
		return pd;
	}
	
	public ResourceBundle getMessages() {
		return messages;
	}

	public static void main(String[] args) {
		new PersonController();
	}
}
