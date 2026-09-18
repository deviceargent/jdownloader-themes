package com.github.deviceargent.vaporgrid;

import com.formdev.flatlaf.ui.FlatPanelUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

public class VaporGridPanelUI
	extends FlatPanelUI
{
	private static volatile TexturePaint gridPaint;

	public VaporGridPanelUI() {
		super( false );
	}

	public static ComponentUI createUI( JComponent c ) {
		return new VaporGridPanelUI();
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
			synchronized( VaporGridPanelUI.class ) {
				if( gridPaint == null )
					gridPaint = buildGridPaint();
				p = gridPaint;
			}
		}
		return p;
	}

	static TexturePaint buildGridPaint() {
		int size = 48;
		BufferedImage img = new BufferedImage( size, size, BufferedImage.TYPE_INT_ARGB );
		Graphics2D ig = img.createGraphics();
		try {
			ig.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			Color line = new Color( 0xff, 0x3c, 0x98, 42 );
			ig.setColor( line );
			float w = 1.2f;
			for( int i = -size; i <= size * 2; i += 12 ) {
				ig.drawLine( i, 0, i + size, size );
				ig.drawLine( i, 0, i - size, size );
			}
		} finally {
			ig.dispose();
		}
		return new TexturePaint( img, new Rectangle( size, size ) );
	}
}
