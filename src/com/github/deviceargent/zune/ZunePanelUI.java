package com.github.deviceargent.zune;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicPanelUI;

public class ZunePanelUI
	extends BasicPanelUI
{
	public static ZunePanelUI createUI( JComponent c ) {
		return new ZunePanelUI();
	}

	@Override
	public void update( Graphics g, JComponent c ) {
		if( !(g instanceof Graphics2D) ) {
			super.update( g, c );
			return;
		}
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			g2.setColor( new Color( 0x1b, 0x21, 0x26 ) );
			g2.fillRect( 0, 0, c.getWidth(), c.getHeight() );
			g2.setColor( new Color( 0x24, 0x2c, 0x33 ) );
			for( int x = -c.getHeight(); x < c.getWidth(); x += 8 )
				g2.drawLine( x, 0, x + c.getHeight(), c.getHeight() );
			g2.setColor( new Color( 0x16, 0x1b, 0x20 ) );
			for( int x = 0; x < c.getWidth() + c.getHeight(); x += 8 )
				g2.drawLine( x, 0, x - c.getHeight(), c.getHeight() );
		} finally {
			g2.dispose();
		}
		paint( g, c );
	}
}
