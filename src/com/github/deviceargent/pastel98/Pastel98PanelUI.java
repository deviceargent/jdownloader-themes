package com.github.deviceargent.pastel98;

import com.formdev.flatlaf.ui.FlatPanelUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

public class Pastel98PanelUI
	extends FlatPanelUI
{
	private static volatile TexturePaint sharedPaint;

	public Pastel98PanelUI() {
		super( false );
	}

	public static ComponentUI createUI( JComponent c ) {
		return new Pastel98PanelUI();
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

	static TexturePaint getSharedPaint() {
		TexturePaint p = sharedPaint;
		if( p == null ) {
			synchronized( Pastel98PanelUI.class ) {
				if( sharedPaint == null )
					sharedPaint = buildPaint( UIManager.getColor( "Panel.background" ), 0.08f, 0.50f );
				p = sharedPaint;
			}
		}
		return p;
	}

	static TexturePaint buildPaint( Color base, float shadeAmount, float highlightAmount ) {
		if( base == null )
			return null;

		int size = 4;
		int[] bayer = {
			0,  8,  2, 10,
			12,  4, 14,  6,
			 3, 11,  1,  9,
			15,  7, 13,  5
		};

		Color shade = mixTowardBlack( base, shadeAmount );
		Color highlight = mixTowardWhite( base, highlightAmount );

		BufferedImage img = new BufferedImage( size, size, BufferedImage.TYPE_INT_RGB );
		for( int y = 0; y < size; y++ ) {
			for( int x = 0; x < size; x++ ) {
				int level = bayer[y * size + x];
				Color c;
				if( level <= 2 )
					c = shade;
				else if( level >= 13 )
					c = highlight;
				else
					c = base;
				img.setRGB( x, y, c.getRGB() );
			}
		}
		return new TexturePaint( img, new Rectangle( size, size ) );
	}

	private static Color mixTowardBlack( Color a, float t ) {
		float k = 1f - t;
		return new Color(
			Math.round( a.getRed() * k ),
			Math.round( a.getGreen() * k ),
			Math.round( a.getBlue() * k ) );
	}

	private static Color mixTowardWhite( Color a, float t ) {
		return new Color(
			Math.round( a.getRed() + (255 - a.getRed()) * t ),
			Math.round( a.getGreen() + (255 - a.getGreen()) * t ),
			Math.round( a.getBlue() + (255 - a.getBlue()) * t ) );
	}
}
