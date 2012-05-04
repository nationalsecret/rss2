package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import controller.PersonController;

public class MenuFrame {

	/**
	 * 
	 */
	private Button huButton     = null;
	private Button roButton     = null;
	private Button enButton 	 = null;
	private Display display = null;
	private Shell shell = null;
	private PersonController pc = null;
	
	public MenuFrame( Display display, Shell shell, PersonController personController ) {
		this.display = display;
		this.shell= shell;
		this.pc = personController;
		initializeFrame();
		setListeners();
	}
	
	private void initializeFrame() {
		
		shell.setText( "Select your language" );
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension(640,480);
        shell.setSize(frameSize.width, frameSize.height);
        shell.setLocation(screenSize.width / 2 - (frameSize.width / 2), screenSize.height / 2 - (frameSize.height / 2));
        shell.setLayout(new RowLayout(SWT.VERTICAL));
        
		
		huButton = new Button(shell, SWT.PUSH);
		roButton = new Button(shell, SWT.PUSH);
		enButton = new Button(shell, SWT.PUSH);
		
		
		huButton.setImage(new Image(display, "pics/hu.png"));
		roButton.setImage(new Image(display, "pics/ro.png"));
		enButton.setImage(new Image(display, "pics/gb.png"));

		shell.pack();
		shell.open();
	}
	
	private void setListeners() {
		huButton.addListener( 
				SWT.Selection,
				new Listener() {

					@Override
					public void handleEvent(Event event) {
						pc.initFrame("hu", "HU");
					}
				}
			);
		
		roButton.addListener( 
				SWT.Selection,
				new Listener() {

					@Override
					public void handleEvent(Event event) {
						pc.initFrame( "ro","RO" );
					}
				}
			);
		
		enButton.addListener( 
			SWT.Selection,
			new Listener() {

				@Override
				public void handleEvent(Event event) {
					pc.initFrame( "en","GB" );
				}
			}
		);
	}
}
