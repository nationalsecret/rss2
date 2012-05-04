package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Person;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import controller.PersonController;

public class Dialog {
	
	private Shell dialog;
	private PersonController pc;
	private Person person;
	private Text fNameTF;
	private Text lNameTF;
	private Text weightTF;
	private Text heightTF;
	
	private Text bYearTF;
	private Text bMonthTF;
	private Text bDayTF;
	
	private Button insertButton;

	public Dialog( Shell dialog, PersonController pc, Person person ) {
		
		this.dialog = dialog;
		this.pc = pc;
		this.person = person;
	    dialog.setText("Insert");
	    dialog.setSize(250, 300);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    dialog.setLocation(screenSize.width / 2 - (200 / 2), screenSize.height / 2 - (350 / 2));
	    GridLayout layout = new GridLayout(2, false);
	    dialog.setLayout(layout);
	    
	    fNameTF  = new Text(dialog, SWT.BORDER);
		lNameTF  = new Text(dialog, SWT.BORDER);
		weightTF = new Text(dialog, SWT.BORDER);
		heightTF = new Text(dialog, SWT.BORDER);
		
		Composite composite = new Composite(dialog, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite.moveBelow(lNameTF);
		
		bYearTF  = new Text(composite, SWT.BORDER);
		bMonthTF = new Text(composite, SWT.BORDER);
		bDayTF   = new Text(composite, SWT.BORDER);
		Utils.setBirthdateListeners(bYearTF, bMonthTF, bDayTF);
		
		if(person != null) {
			fNameTF.setText( person.getFirstName() );
			lNameTF.setText( person.getLastName() );
			weightTF.setText( person.getWeightInKilograms()   + "" );
			heightTF.setText( person.getHeightInMeters()      + "" );
			
			GregorianCalendar date = person.getBirthDate();
			bYearTF.setText( date.get(Calendar.YEAR)         + "" );
			bMonthTF.setText( date.get(Calendar.MONTH)         + "" );
			bDayTF.setText( date.get(Calendar.DAY_OF_MONTH) + "" );
		}
		else {
			bYearTF.setText( pc.getMessages().getString("year_field") );
	        bMonthTF.setText( pc.getMessages().getString("month_field") );
	        bDayTF.setText( pc.getMessages().getString("day_field") );
		}
        Label fNameL = new Label( dialog, SWT.NONE );
        Label lNameL = new Label( dialog, SWT.NONE );
        Label birthDL = new Label( dialog, SWT.NONE );
        Label weightL = new Label( dialog, SWT.NONE );
        Label heightL = new Label( dialog, SWT.NONE );
        
        fNameL.moveAbove(fNameTF);
        lNameL.moveAbove(lNameTF);
        birthDL.moveAbove(composite);
        weightL.moveAbove(weightTF);
        heightL.moveAbove(heightTF);
        
        fNameL.setText( pc.getMessages().getString("person_first_name") + ": " );
        lNameL.setText( pc.getMessages().getString("person_last_name") + ": " );
        birthDL.setText( pc.getMessages().getString("person_birthdate") + ": " );
        weightL.setText( pc.getMessages().getString("person_weight_kg") + ": " );
        heightL.setText( pc.getMessages().getString("person_height_m") + ": " );
        
        insertButton = new Button(dialog, SWT.PUSH);
        if(person == null)
        	insertButton.setText( pc.getMessages().getString("insert_button") );
        else
        	insertButton.setText( pc.getMessages().getString("update_button") );
        setListener();
        
        dialog.pack();
        dialog.open();
	}

	private void setListener() {

		insertButton.addListener( 
				SWT.Selection,
				new Listener() {
					
					@Override
					public void handleEvent(Event e) {
						String valid = Utils.validData(pc, fNameTF, lNameTF, bYearTF, bMonthTF, bDayTF,
								weightTF, heightTF);
						
						if( !valid.equals("OK") ) {
							MessageBox messageBox = new MessageBox(dialog, SWT.ICON_ERROR);
							messageBox.setMessage(valid + " " + pc.getMessages().getString("field_error"));
							messageBox.open();
						}
						else {
							if(person == null) {
								Person temp = new Person();
								temp.setFirstName         ( fNameTF.getText() );
								temp.setLastName          ( lNameTF.getText() );
								GregorianCalendar gc = new GregorianCalendar(
										Integer.parseInt( bYearTF.getText()  ),
										Integer.parseInt( bMonthTF.getText() ),
										Integer.parseInt( bDayTF.getText() )
								);
								temp.setBirthDate         ( gc );
								temp.setWeightInKilograms ( Double.parseDouble(weightTF.getText()) );
								temp.setHeightInMeters    ( Double.parseDouble(heightTF.getText()) );
								pc.getPd().insertPerson( temp );
							}
							else {
								person.setFirstName         ( fNameTF.getText() );
								person.setLastName          ( lNameTF.getText() );
								GregorianCalendar gc = new GregorianCalendar(
										Integer.parseInt( bYearTF.getText()  ),
										Integer.parseInt( bMonthTF.getText() ),
										Integer.parseInt( bDayTF.getText() )
								);
								person.setBirthDate         ( gc );
								person.setWeightInKilograms ( Double.parseDouble(weightTF.getText()) );
								person.setHeightInMeters    ( Double.parseDouble(heightTF.getText()) );
								pc.getPd().updatePerson( person );
							}
						}
						dialog.dispose();
					}
				}
				);
	}
}
