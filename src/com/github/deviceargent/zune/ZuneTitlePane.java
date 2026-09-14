package com.github.deviceargent.zune;

import com.formdev.flatlaf.ui.FlatTitlePane;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JRootPane;

public class ZuneTitlePane
	extends FlatTitlePane
{
	private static final Color AERO_TOP = new Color( 0xa5, 0xc9, 0xdc );
	private static final Color AERO_MIDDLE = new Color( 0x5d, 0x87, 0xa1 );
	private static final Color AERO_BOTTOM = new Color( 0x22, 0x3e, 0x50 );
	private static final Color GLOSS = new Color( 0xff, 0xff, 0xff, 125 );

	public ZuneTitlePane( JRootPane rootPane ) {
		super( rootPane );
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
		if( !(g instanceof Graphics2D) ) {
			super.paintComponent( g );
			return;
		}
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			g2.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
			g2.setPaint( new GradientPaint( 0, 0, AERO_TOP, 0, getHeight() / 2f, AERO_MIDDLE ) );
			g2.fillRect( 0, 0, getWidth(), getHeight() );
			g2.setPaint( new GradientPaint( 0, getHeight() / 2f, AERO_MIDDLE, 0, getHeight(), AERO_BOTTOM ) );
			g2.fillRect( 0, getHeight() / 2, getWidth(), getHeight() / 2 );
			g2.setColor( GLOSS );
			g2.fillRect( 0, 1, getWidth(), 2 );
			g2.setColor( new Color( 0xff, 0xff, 0xff, 45 ) );
			g2.fillRect( 0, 4, getWidth(), 1 );
		} finally {
			g2.dispose();
		}
	}
}
