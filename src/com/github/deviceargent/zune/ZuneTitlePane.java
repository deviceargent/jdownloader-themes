package com.github.deviceargent.zune;

import com.formdev.flatlaf.ui.FlatTitlePane;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.plaf.basic.BasicButtonUI;

public class ZuneTitlePane
	extends FlatTitlePane
{
	private static final Color AERO_TOP = new Color( 0xa5, 0xc9, 0xdc );
	private static final Color AERO_MIDDLE = new Color( 0x5d, 0x87, 0xa1 );
	private static final Color AERO_BOTTOM = new Color( 0x22, 0x3e, 0x50 );
	private static final Color GLOSS = new Color( 0xff, 0xff, 0xff, 125 );

	public ZuneTitlePane( JRootPane rootPane ) {
		super( rootPane );
		if( buttonPanel != null )
			buttonPanel.setOpaque( false );
		styleButton( iconifyButton );
		styleButton( maximizeButton );
		styleButton( restoreButton );
		styleButton( closeButton );
	}

	private void styleButton( JButton button ) {
		if( button != null ) {
			button.setUI( new BasicButtonUI() );
			button.setOpaque( false );
			button.setBorderPainted( false );
			button.setFocusPainted( false );
			button.setBackground( new Color( 0, 0, 0, 0 ) );
			button.putClientProperty( "FlatLaf.style", "arc: 0; background: #00000000; hoverBackground: #00000000; pressedBackground: #00000000; borderWidth: 0" );
		}
	}

	@Override
	protected JButton createButton( String type, String accessibleName, ActionListener actionListener ) {
		JButton button = super.createButton( type, accessibleName, actionListener );
		button.putClientProperty( "FlatLaf.style",
			"background: #52758D; hoverBackground: #8FB4C9; pressedBackground: #315266" );
		return button;
	}

	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		if( buttonPanel != null && g instanceof Graphics2D ) {
			Graphics2D g2 = (Graphics2D) g.create();
			try {
				java.awt.Rectangle bounds = buttonPanel.getBounds();
				g2.setColor( new Color( 0xf0, 0x78, 0x2b ) );
				g2.fillRoundRect( bounds.x, bounds.y, bounds.width, bounds.height, 6, 6 );
			} finally {
				g2.dispose();
			}
		}
	}
}
