package com.github.deviceargent.pastel98;

import com.formdev.flatlaf.ui.FlatPanelUI;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

public class Pastel98PanelUI
	extends FlatPanelUI
{
	static final float SCANLINE_ALPHA = 0.18f;
	static final Color SCANLINE_CREAM = new Color( 0xf7, 0xf0, 0xe3 );
	static final Color SCANLINE_ROSE = new Color( 0xd9, 0x8a, 0xa8 );
	static final Color PETAL_COLOR = new Color( 0xf4, 0xd0, 0xd4 );
	private static volatile TexturePaint sharedPaint;
	private static volatile TexturePaint scanlinePaint;
	private static volatile TexturePaint petalPaint;

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
					TexturePaint s = getScanlinePaint();
					if( s != null ) {
						g2.setPaint( s );
						g2.fill( r );
					}
					TexturePaint t = getPetalPaint();
					if( t != null ) {
						g2.setPaint( t );
						g2.fill( r );
					}
				} finally {
					g2.dispose();
				}
				paint( g, c );
				return;
			}
		}
		super.update( g, c );
	}

	private static TexturePaint getScanlinePaint() {
		TexturePaint p = scanlinePaint;
		if( p == null ) {
			synchronized( Pastel98PanelUI.class ) {
				if( scanlinePaint == null )
					scanlinePaint = buildScanlinePaint();
				p = scanlinePaint;
			}
		}
		return p;
	}

	static TexturePaint buildScanlinePaint() {
		int[] rowColor = {
			1, 1, 1, 0,              // 3px cream, 1px rose-repeated
			1, 1, 1, 0
		};
		return buildScanlineTexture( SCANLINE_CREAM, SCANLINE_ROSE, rowColor, SCANLINE_ALPHA );
	}

	static TexturePaint buildScanlineTexture( Color main, Color thin, int[] rowColor, float alpha ) {
		int h = rowColor.length;
		BufferedImage img = new BufferedImage( 1, h, BufferedImage.TYPE_INT_ARGB );
		int a = Math.max( 0, Math.min( 255, Math.round( 255 * alpha ) ) );
		for( int y = 0; y < h; y++ ) {
			Color c = rowColor[y] == 1 ? main : thin;
			img.setRGB( 0, y, new Color( c.getRed(), c.getGreen(), c.getBlue(), a ).getRGB() );
		}
		return new TexturePaint( img, new Rectangle( 1, h ) );
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

	private static TexturePaint getPetalPaint() {
		TexturePaint p = petalPaint;
		if( p == null ) {
			synchronized( Pastel98PanelUI.class ) {
				if( petalPaint == null )
					petalPaint = buildPetalPaint( 0.05f, 36 );
				p = petalPaint;
			}
		}
		return p;
	}

	static TexturePaint buildPetalPaint( float alpha, int density ) {
		int size = 64;
		BufferedImage img = new BufferedImage( size, size, BufferedImage.TYPE_INT_ARGB );
		Graphics2D ig = img.createGraphics();
		try {
			ig.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			Random rnd = new Random( 98 );
			for( int i = 0; i < density; i++ ) {
				int cx = 4 + rnd.nextInt( size - 8 );
				int cy = 4 + rnd.nextInt( size - 8 );
				double ang = rnd.nextDouble() * Math.PI * 2;
				double len = 5 + rnd.nextDouble() * 5;
				Color c = withAlpha( PETAL_COLOR, (float) (alpha * (0.65 + rnd.nextDouble() * 0.35)) );
				drawPetal( ig, cx, cy, len, ang, c );
			}
		} finally {
			ig.dispose();
		}
		return new TexturePaint( img, new Rectangle( size, size ) );
	}

	private static void drawPetal( Graphics2D g, double cx, double cy, double len, double ang, Color c ) {
		AffineTransform old = g.getTransform();
		g.setColor( c );
		g.translate( cx, cy );
		g.rotate( ang );
		Path2D.Double p = new Path2D.Double();
		double w = len * 0.6;
		p.moveTo( 0, -len );
		p.curveTo( w, -len, w, len * 0.2, 0, len );
		p.curveTo( -w, len * 0.2, -w, -len, 0, -len );
		g.fill( p );
		g.setTransform( old );
	}

	private static Color withAlpha( Color c, float a ) {
		return new Color( c.getRed(), c.getGreen(), c.getBlue(),
			Math.round( 255 * Math.max( 0f, Math.min( 1f, a ) ) ) );
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
