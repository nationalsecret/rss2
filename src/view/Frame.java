package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import model.Person;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import controller.PersonController;

public class Frame {

	/**
	 * 
	 */
	private Table  table        = null;
	
	private Button insertButton = null;
	private Button deleteButton = null;
	private Button updateButton = null;
	
	private PersonController pc = null;
	private Display display;
	private Shell shell;

	public Frame( Display display, Shell shell, PersonController personController) {
		this.display = display;
		this.shell= shell;
		this.pc = personController;
		initializeFrame();
		setListeners();
	}
	
	private void initializeFrame() {
		shell.setText( pc.getMessages().getString("frame_title") );
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension(640,480);
        shell.setSize(frameSize.width, frameSize.height);
        shell.setLocation(screenSize.width / 2 - (frameSize.width / 2), screenSize.height / 2 - (frameSize.height / 2));
        shell.setLayout(new RowLayout(SWT.VERTICAL));
        
        Menu menuBar = new Menu(shell, SWT.BAR);
        MenuItem menu = new MenuItem(menuBar, SWT.CASCADE);
        menu.setText("&File");
        
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        menu.setMenu(fileMenu);
        
        MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
        fileExitItem.setText("E&xit");
        
        fileExitItem.addSelectionListener(new fileExitItemListener());
        
        table = new Table(shell, SWT.VIRTUAL | SWT.BORDER);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        TableColumn[] column = new  TableColumn[6];
        
        column[0] = new  TableColumn(table, SWT.RIGHT);
        column[1] = new  TableColumn(table, SWT.RIGHT);
        column[2] = new  TableColumn(table, SWT.RIGHT);
        column[3] = new  TableColumn(table, SWT.RIGHT);
        column[4] = new  TableColumn(table, SWT.RIGHT);
        column[5] = new  TableColumn(table, SWT.RIGHT);
        
        column[0].setText(pc.getMessages().getString("person_id"));
        column[1].setText(pc.getMessages().getString("person_first_name"));
        column[2].setText(pc.getMessages().getString("person_last_name"));
        column[3].setText(pc.getMessages().getString("person_birthdate"));
        column[4].setText(pc.getMessages().getString("person_weight_kg"));
        column[5].setText(pc.getMessages().getString("person_height_m"));
        
        fillTable(table, pc.getPd().getAllPersons());
        
        for  (int  i = 0, n = column.length; i < n; i++) {
        	column[i].pack();
        }
        
        Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		
        insertButton = new Button(composite, SWT.PUSH);
        deleteButton = new Button(composite, SWT.PUSH);
        updateButton = new Button(composite, SWT.PUSH);
        
        insertButton.setText( pc.getMessages().getString("insert_button") );
        deleteButton.setText( pc.getMessages().getString("delete_button") );
        updateButton.setText( pc.getMessages().getString("update_button") );
        
        shell.pack();
        shell.open();
	}
	
	class fileExitItemListener implements SelectionListener {
	    public void widgetSelected(SelectionEvent event) {
	      shell.close();
	      display.dispose();
	    }

	    public void widgetDefaultSelected(SelectionEvent event) {
	      shell.close();
	      display.dispose();
	    }
	  }
	
	private void setListeners() {
		
		insertButton.addListener( 
			SWT.Selection,
			new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					
					final Shell dialog = new Shell(shell, SWT.APPLICATION_MODAL
					        | SWT.DIALOG_TRIM);
					
					new Dialog( dialog, pc, null );
					
					while (!dialog.isDisposed()) {
				      if (!display.readAndDispatch())
				        display.sleep();
				    }
					fillTable(table, pc.getPd().getAllPersons());
				}
			}
		);
		
		deleteButton.addListener( 
			SWT.Selection,
			new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					int idx = 0;
					try {
						TableItem[] tblItem = table.getSelection();
						idx = Integer.parseInt( tblItem[0].getText() );
					} catch( Exception ex ) {
						MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
						messageBox.setMessage(pc.getMessages().getString("selected_error"));
						messageBox.open();
						return;
					}
					pc.getPd().deletePerson(idx);
					fillTable(table, pc.getPd().getAllPersons());
				}
			}
		);
		
		updateButton.addListener( 
			SWT.Selection,
			new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					
					int idx = 0;
					try {
						TableItem[] tblItem = table.getSelection();
						idx = Integer.parseInt( tblItem[0].getText() );
					} catch( Exception ex ) {
						MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
						messageBox.setMessage(pc.getMessages().getString("selected_error"));
						messageBox.open();
						return;
					}
					
					Person person = pc.getPd().getPersonById(idx);
					final Shell dialog = new Shell(shell, SWT.APPLICATION_MODAL
					        | SWT.DIALOG_TRIM);
					
					new Dialog( dialog, pc, person );
					
					while (!dialog.isDisposed()) {
				      if (!display.readAndDispatch())
				        display.sleep();
				    }
					fillTable(table, pc.getPd().getAllPersons());
				}
			}
		);
	}
	
	protected void fillTable(Table table, List<Person> list) {
		
		table.removeAll();
		table.setRedraw(false);
        
        for(Iterator<Person> iterator = list.iterator(); iterator.hasNext(); ) {
	        Person person = iterator.next();
	        TableItem item = new  TableItem(table, SWT.NONE);
	        int  c = 0;
	        item.setText(c++, String.valueOf(person.getId()));
	        item.setText(c++, person.getFirstName());
	        item.setText(c++, person.getLastName());
	        item.setText(c++, 
	        		person.getBirthDate().get( Calendar.YEAR ) + "-" +
	        		person.getBirthDate().get( Calendar.MONTH ) + "-" +
	        		person.getBirthDate().get( Calendar.DAY_OF_MONTH )
	        );
	        item.setText(c++, String.valueOf(person.getWeightInKilograms()));
	        item.setText(c++, String.valueOf(person.getHeightInMeters()));
        }
        
        table.setRedraw(true);
	}
	
	public Table getTable() {
		return table;
	}
}
