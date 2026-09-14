package com.github.deviceargent.zune;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

final class ZuneGlassPane
	extends JComponent
{
	private final JRootPane rootPane;

	ZuneGlassPane( JRootPane rootPane ) {
		this.rootPane = rootPane;
		setOpaque( false );
		setFocusable( false );
	}

	@Override
	public boolean contains( int x, int y ) {
		return false;
	}

	@Override
	protected void paintComponent( Graphics g ) {
		if( !(g instanceof Graphics2D) )
			return;
		Rectangle content = SwingUtilities.convertRectangle(
			rootPane.getContentPane(),
			rootPane.getContentPane().getBounds(),
			this );
		if( content.width <= 0 || content.height <= 0 )
			return;
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			int reflectionHeight = Math.max( 1, content.height / 3 );
			g2.setPaint( new GradientPaint(
				0, content.y, new Color( 0xff, 0xff, 0xff, 34 ),
				0, content.y + reflectionHeight, new Color( 0xff, 0xff, 0xff, 0 ) ) );
			g2.fillRect( content.x, content.y, content.width, reflectionHeight );
		} finally {
			g2.dispose();
		}
	}
}
