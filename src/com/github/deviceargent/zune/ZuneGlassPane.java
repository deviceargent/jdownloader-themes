package com.github.deviceargent.zune;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JRootPane;

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
		int top = 32;
		int height = getHeight() - top;
		if( getWidth() <= 0 || height <= 0 )
			return;
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			int reflectionHeight = Math.max( 1, height / 3 );
			g2.setPaint( new GradientPaint(
				0, top, new Color( 0xff, 0xff, 0xff, 58 ),
				0, top + reflectionHeight, new Color( 0xff, 0xff, 0xff, 0 ) ) );
			g2.fillRect( 0, top, getWidth(), reflectionHeight );
		} finally {
			g2.dispose();
		}
	}
}
