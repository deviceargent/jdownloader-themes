package com.github.deviceargent.zune;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.WindowEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;

public class Zune
	extends FlatDarkLaf
{
	public static final String NAME = "Zune";

	public static boolean setup() {
		return setup( new Zune() );
	}

	@Override
	public void initialize() {
		super.initialize();
		AWTEventListener listener = event -> {
			if( event instanceof WindowEvent && event.getID() == WindowEvent.WINDOW_OPENED )
				applyTabbedPaneUI( (Window) ((WindowEvent) event).getWindow() );
		};
		Toolkit.getDefaultToolkit().addAWTEventListener( listener, AWTEvent.WINDOW_EVENT_MASK );
		for( Window window : Window.getWindows() )
			applyTabbedPaneUI( window );
	}

	private static void applyTabbedPaneUI( Component component ) {
		if( component instanceof JTabbedPane && component.getClass().getName().equals( "jd.gui.swing.jdgui.MainTabbedPane" ) ) {
			JTabbedPane tabs = (JTabbedPane) component;
			if( tabs.getClientProperty( Zune.class ) == null ) {
				tabs.putClientProperty( Zune.class, Boolean.TRUE );
				tabs.setUI( new ZuneTabbedPaneUI() );
				tabs.setOpaque( false );
				tabs.addChangeListener( event -> SwingUtilities.invokeLater( () -> applyTabbedPaneUI( tabs ) ) );
				tabs.addContainerListener( new ContainerAdapter() {
					@Override
					public void componentAdded( ContainerEvent event ) {
						SwingUtilities.invokeLater( () -> applyTabbedPaneUI( tabs ) );
					}
				} );
			}
			Component selected = tabs.getSelectedComponent();
			if( selected instanceof JComponent )
				clearContainerOpacity( (JComponent) selected );
		}
		if( component instanceof Container )
			for( Component child : ((Container) component).getComponents() )
				applyTabbedPaneUI( child );
	}

	private static void clearContainerOpacity( JComponent component ) {
		if( component instanceof JPanel || component instanceof JScrollPane || component instanceof JViewport || component instanceof JLayer )
			component.setOpaque( false );
		if( component instanceof Container )
			for( Component child : ((Container) component).getComponents() )
				if( child instanceof JComponent )
					clearContainerOpacity( (JComponent) child );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, Zune.class );
	}

	@Override
	public UIDefaults getDefaults() {
		UIDefaults defaults = super.getDefaults();
		defaults.put( "PanelUI", ZunePanelUI.class.getName() );
		defaults.put( "ViewportUI", ZuneViewportUI.class.getName() );
		defaults.put( "RootPaneUI", ZuneRootPaneUI.class.getName() );
		return defaults;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
