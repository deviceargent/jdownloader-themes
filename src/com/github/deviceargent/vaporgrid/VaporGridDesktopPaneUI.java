package com.github.deviceargent.vaporgrid;

import com.formdev.flatlaf.ui.FlatDesktopPaneUI;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class VaporGridDesktopPaneUI
	extends FlatDesktopPaneUI
{
	private static volatile TexturePaint gridPaint;

	public static ComponentUI createUI( JComponent c ) {
		return new VaporGridDesktopPaneUI();
	}

	@Override
	public void update( Graphics g, JComponent c ) {
		if( c.isOpaque() ) {
			super.update( g, c );
			TexturePaint gp = getGridPaint();
			if( gp != null && g instanceof Graphics2D ) {
				Rectangle r = g.getClipBounds();
				if( r == null )
					r = new Rectangle( c.getWidth(), c.getHeight() );
				Graphics2D g2 = (Graphics2D) g.create();
				try {
					g2.setPaint( gp );
					g2.fill( r );
				} finally {
					g2.dispose();
				}
			}
			paint( g, c );
			return;
		}
		super.update( g, c );
	}

	private static TexturePaint getGridPaint() {
		TexturePaint p = gridPaint;
		if( p == null ) {
			synchronized( VaporGridDesktopPaneUI.class ) {
				if( gridPaint == null )
					gridPaint = VaporGridPanelUI.buildGridPaint();
				p = gridPaint;
			}
		}
		return p;
	}
}
