package com.github.deviceargent.sunsettape;

import com.formdev.flatlaf.ui.FlatTitlePane;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JRootPane;

public class SunsetTapeTitlePane
	extends FlatTitlePane
{
	private int titleFontSize;
	private static final Color PURPLE = new Color( 0x4e, 0x28, 0x6c );
	private static final Color MAGENTA = new Color( 0xc9, 0x2f, 0x61 );
	private static final Color ORANGE = new Color( 0xe5, 0x69, 0x34 );
	private static final Color GOLD = new Color( 0xf4, 0xb3, 0x2a );

	public SunsetTapeTitlePane( JRootPane rootPane ) {
		super( rootPane );
		if( titleLabel != null )
			titleFontSize = titleLabel.getFont().getSize() + 2;
		styleTitleLabel();
	}

	@Override
	protected void activeChanged( boolean active ) {
		super.activeChanged( active );
		styleTitleLabel();
	}

	@Override
	protected void titleBarColorsChanged() {
		super.titleBarColorsChanged();
		styleTitleLabel();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		styleTitleLabel();
	}

	private void styleTitleLabel() {
		if( titleLabel != null ) {
			if( titleFontSize <= 0 )
				titleFontSize = Math.max( 1, titleLabel.getFont().getSize() ) + 2;
			titleLabel.setFont( new Font( "Segoe Print", Font.BOLD, titleFontSize ) );
			titleLabel.setForeground( new Color( 0x1d, 0x4e, 0x89 ) );
		}
	}

	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		if( !(g instanceof Graphics2D) )
			return;
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			int w = getWidth();
			int h = getHeight();

			int band = 3;
			int x1 = (int) (w * 0.30);
			int x2 = (int) (w * 0.62);
			g2.setPaint( new GradientPaint( 0, 0, PURPLE, x1, 0, MAGENTA ) );
			g2.fillRect( 0, h - band, x1, band );
			g2.setPaint( new GradientPaint( x1, 0, MAGENTA, x2, 0, ORANGE ) );
			g2.fillRect( x1, h - band, x2 - x1, band );
			g2.setPaint( new GradientPaint( x2, 0, ORANGE, w, 0, GOLD ) );
			g2.fillRect( x2, h - band, w - x2, band );

			{
				int count = 4;
				int thickness = 30;
				int period = thickness;
				int yTop = 2;
				int yBottom = h - band;
				int dx = Math.max( 1, (yBottom - yTop) / 3 );
				Rectangle titleBounds = getTitleTextBounds();
				int groupWidth = 2 * period + dx;
				int center = w / 2;
				int leftStart = center - groupWidth;
				int rightStart = center;
				if( titleBounds != null ) {
					leftStart = titleBounds.x - groupWidth;
					rightStart = titleBounds.x + titleBounds.width;
				}

				Color[] cols = { PURPLE, MAGENTA, ORANGE, GOLD };
				for( int i = 0; i < count; i++ ) {
					int sx = i < 2
						? leftStart + i * period
						: rightStart + (i - 2) * period;
					drawDiagStripe( g2, sx, yTop, thickness, yBottom, cols[i] );
				}

				// Mask the complete title area after the decoration, then let the label
				// paint its text over a small cassette-label plaque.
				if( titleBounds != null ) {
					RoundRectangle2D titlePlaque = new RoundRectangle2D.Float(
						titleBounds.x, titleBounds.y, titleBounds.width, titleBounds.height, 5, 5 );
					g2.setColor( Color.WHITE );
					g2.fill( titlePlaque );
					g2.setColor( new Color( PURPLE.getRed(), PURPLE.getGreen(), PURPLE.getBlue(), 75 ) );
					g2.draw( titlePlaque );
				}
			}
		} finally {
			g2.dispose();
		}
	}

	private Rectangle getTitleTextBounds() {
		if( titleLabel == null || !titleLabel.isVisible() || titleLabel.getText() == null )
			return null;
		FontMetrics metrics = titleLabel.getFontMetrics( titleLabel.getFont() );
		int textWidth = metrics.stringWidth( titleLabel.getText() );
		int textHeight = metrics.getAscent() + metrics.getDescent();
		int padding = 3;
		return new Rectangle(
			(getWidth() - textWidth) / 2 - padding,
			(getHeight() - textHeight) / 2 - padding,
			textWidth + padding * 2,
			textHeight + padding * 2 );
	}

	private static void drawDiagStripe( Graphics2D g2, int x, int yTop, int thickness, int yBottom, Color color ) {
		int height = yBottom - yTop;
		if( height <= 0 )
			return;
		int dx = height / 3;
		int[] xPoints = { x, x + thickness, x + thickness + dx, x + dx };
		int[] yPoints = { yTop, yTop, yBottom, yBottom };
		Polygon stripe = new Polygon( xPoints, yPoints, 4 );
		g2.setColor( new Color( color.getRed(), color.getGreen(), color.getBlue(), 235 ) );
		g2.fillPolygon( stripe );
	}
}
