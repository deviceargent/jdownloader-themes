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
			g2.setPaint( new GradientPaint( 0, 0, new Color( 0x29, 0x29, 0x29 ), 0, c.getHeight(), new Color( 0x1b, 0x1b, 0x1b ) ) );
			g2.fillRect( 0, 0, c.getWidth(), c.getHeight() );
		} finally {
			g2.dispose();
		}
		paint( g, c );
	}
}
