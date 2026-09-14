package com.github.deviceargent.zune;

import java.awt.Color;
import java.awt.GradientPaint;
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
			g2.setPaint( new GradientPaint( 0, 0, new Color( 0x28, 0x32, 0x3a ), 0, c.getHeight(), new Color( 0x09, 0x0b, 0x0e ) ) );
			g2.fillRect( 0, 0, c.getWidth(), c.getHeight() );
			g2.setPaint( new GradientPaint( 0, 0, new Color( 0xff, 0xff, 0xff, 32 ), 0, Math.max( 1, c.getHeight() / 3 ), new Color( 0xff, 0xff, 0xff, 0 ) ) );
			g2.fillRect( 0, 0, c.getWidth(), Math.max( 1, c.getHeight() / 3 ) );
		} finally {
			g2.dispose();
		}
		paint( g, c );
	}
}
