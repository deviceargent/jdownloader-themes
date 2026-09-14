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
	private static final Color ORANGE_TOP = new Color( 0xf5, 0x9a, 0x52 );
	private static final Color ORANGE_BOTTOM = new Color( 0xc7, 0x4d, 0x16 );
	private static final Color GLOSS = new Color( 0xff, 0xff, 0xff, 95 );

	public ZuneTitlePane( JRootPane rootPane ) {
		super( rootPane );
	}

	@Override
	protected JButton createButton( String type, String accessibleName, ActionListener actionListener ) {
		JButton button = super.createButton( type, accessibleName, actionListener );
		button.putClientProperty( "FlatLaf.style",
			"arc: 3; background: #E8792B; hoverBackground: #F6A05B; pressedBackground: #B94713" );
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
			g2.setPaint( new GradientPaint( 0, 0, ORANGE_TOP, 0, getHeight(), ORANGE_BOTTOM ) );
			g2.fillRect( 0, 0, getWidth(), getHeight() );
			g2.setColor( GLOSS );
			g2.fillRect( 0, 1, getWidth(), 2 );
		} finally {
			g2.dispose();
		}
	}
}
