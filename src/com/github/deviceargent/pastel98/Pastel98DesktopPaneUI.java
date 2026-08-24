package com.github.deviceargent.pastel98;

import com.formdev.flatlaf.ui.FlatDesktopPaneUI;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

public class Pastel98DesktopPaneUI
	extends FlatDesktopPaneUI
{
	private static volatile TexturePaint sharedPaint;

	public static ComponentUI createUI( JComponent c ) {
		return new Pastel98DesktopPaneUI();
	}

	@Override
	public void update( Graphics g, JComponent c ) {
		if( c.isOpaque() ) {
			TexturePaint p = getSharedPaint();
			if( p != null && g instanceof Graphics2D ) {
				Rectangle r = g.getClipBounds();
				if( r == null )
					r = new Rectangle( c.getWidth(), c.getHeight() );
				Graphics2D g2 = (Graphics2D) g.create();
				try {
					g2.setPaint( p );
					g2.fill( r );
				} finally {
					g2.dispose();
				}
				paint( g, c );
				return;
			}
		}
		super.update( g, c );
	}

	private static TexturePaint getSharedPaint() {
		TexturePaint p = sharedPaint;
		if( p == null ) {
			synchronized( Pastel98DesktopPaneUI.class ) {
				if( sharedPaint == null )
					sharedPaint = Pastel98PanelUI.buildPaint(
						UIManager.getColor( "Desktop.background" ), 0.14f, 0.60f );
				p = sharedPaint;
			}
		}
		return p;
	}
}
