package com.github.deviceargent.zune;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicViewportUI;

public class ZuneViewportUI
	extends BasicViewportUI
{
	public static ZuneViewportUI createUI( JComponent c ) {
		return new ZuneViewportUI();
	}

	@Override
	public void paint( Graphics g, JComponent c ) {
		if( g instanceof Graphics2D ) {
			Graphics2D g2 = (Graphics2D) g.create();
			try {
				int h = Math.max( 1, c.getHeight() );
				g2.setPaint( new GradientPaint( 0, 0, new Color( 0x32, 0x3e, 0x48 ), 0, h, new Color( 0x09, 0x0b, 0x0e ) ) );
				g2.fillRect( 0, 0, c.getWidth(), h );
				g2.setPaint( new GradientPaint( 0, 0, new Color( 0xff, 0xff, 0xff, 52 ), 0, Math.max( 1, h / 3 ), new Color( 0xff, 0xff, 0xff, 0 ) ) );
				g2.fillRect( 0, 0, c.getWidth(), Math.max( 1, h / 3 ) );
			} finally {
				g2.dispose();
			}
		}
		super.paint( g, c );
	}
}
