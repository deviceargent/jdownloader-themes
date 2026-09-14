package com.github.deviceargent.zune;

import com.formdev.flatlaf.ui.FlatRootPaneUI;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;

public class ZuneRootPaneUI
	extends FlatRootPaneUI
{
	private ZuneGlassPane glassPane;

	public static ComponentUI createUI( JComponent c ) {
		return new ZuneRootPaneUI();
	}

	@Override
	public void installUI( JComponent c ) {
		super.installUI( c );
		if( c instanceof JRootPane ) {
			JRootPane rootPane = (JRootPane) c;
			glassPane = new ZuneGlassPane( rootPane );
			rootPane.setGlassPane( glassPane );
			glassPane.setVisible( true );
			rootPane.addPropertyChangeListener( "glassPane", event -> {
				if( rootPane.getGlassPane() != glassPane )
					SwingUtilities.invokeLater( () -> {
						rootPane.setGlassPane( glassPane );
						glassPane.setVisible( true );
						glassPane.repaint();
					} );
			} );
			SwingUtilities.invokeLater( () -> {
				if( rootPane.getGlassPane() != glassPane )
					rootPane.setGlassPane( glassPane );
				glassPane.setVisible( true );
				glassPane.revalidate();
				glassPane.repaint();
			} );
		}
	}

	@Override
	protected com.formdev.flatlaf.ui.FlatTitlePane createTitlePane() {
		return new ZuneTitlePane( rootPane );
	}
}
