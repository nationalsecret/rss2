package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import controller.PersonController;

public class Utils {

	protected static void setBirthdateListeners(final Text bYearTF, final Text bMonthTF, final Text bDayTF) {

		bYearTF.addListener(
				SWT.FOCUSED,
				new Listener() {
					@Override
					public void handleEvent(Event e) {
						bYearTF.setText("");
					}
				}
		);
		
		bMonthTF.addListener(
				SWT.FOCUSED,
				new Listener() {
					@Override
					public void handleEvent(Event e) {
						bMonthTF.setText("");
					}
				}
		);
		
		bDayTF.addListener(
				SWT.FOCUSED,
				new Listener() {
					@Override
					public void handleEvent(Event e) {
						bDayTF.setText("");
					}
				}
		);
	}
	
	protected static String validData(PersonController pc, Text fNameTF, Text lNameTF, Text bYearTF,
			Text bMonthTF, Text bDayTF, Text weightTF, Text heightTF) {
		
		String fName  = fNameTF.getText();
		String lName  = lNameTF.getText();

		try {
			Double.parseDouble( weightTF.getText() );
			Double.parseDouble( heightTF.getText() );
		} catch( Exception ex ) {
			return pc.getMessages().getString("wh_field");
		}
		
		if( fName.equals("") || lName.equals("") )
			return pc.getMessages().getString("name_field");
		
		int year  = 0;
		int month = 0;
		int day   = 0;
		
		try {
			year  = Integer.parseInt( bYearTF.getText() );
			month = Integer.parseInt( bMonthTF.getText() );
			day   = Integer.parseInt( bDayTF.getText() );
		} catch (Exception ex){
			return pc.getMessages().getString("date_parse");
		};
		
		if( year > 2011 || year < 1900 )
			return pc.getMessages().getString("year_field");
		
		if( month > 12 || month < 1 )
			return pc.getMessages().getString("month_field");
		
		if( day > 31 || day < 1 )
			return pc.getMessages().getString("day_field");
		
		return "OK";
	}
	
}
