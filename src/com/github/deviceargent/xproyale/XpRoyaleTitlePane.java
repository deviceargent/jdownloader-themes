package com.github.deviceargent.xproyale;

import com.formdev.flatlaf.ui.FlatTitlePane;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JRootPane;

public class XpRoyaleTitlePane
	extends FlatTitlePane
{
	private static final Color ROYALE_TOP = new Color( 0x63, 0xa8, 0xe4 );
	private static final Color ROYALE_BOTTOM = new Color( 0x1c, 0x65, 0xb2 );
	private static final Color HIGHLIGHT = new Color( 0xff, 0xff, 0xff, 115 );

	public XpRoyaleTitlePane( JRootPane rootPane ) {
		super( rootPane );
	}

	@Override
	protected JButton createButton( String type, String accessibleName, ActionListener actionListener ) {
		JButton button = super.createButton( type, accessibleName, actionListener );
		button.setIcon( new XpWindowIcon( type ) );
		button.setPressedIcon( new XpWindowIcon( type, true ) );
		button.setContentAreaFilled( false );
		button.setBorderPainted( false );
		button.setFocusPainted( false );
		button.setOpaque( false );
		button.setMargin( new Insets( 0, 0, 0, 0 ) );
		return button;
	}

	@Override
	protected void paintComponent( Graphics g ) {
		if( !(g instanceof Graphics2D) ) {
			super.paintComponent( g );
			return;
		}
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			g2.setPaint( new GradientPaint( 0, 0, ROYALE_TOP, 0, getHeight(), ROYALE_BOTTOM ) );
			g2.fillRect( 0, 0, getWidth(), getHeight() );
			g2.setColor( HIGHLIGHT );
			g2.fillRect( 0, 1, getWidth(), 1 );
		} finally {
			g2.dispose();
		}
	}

	private static final class XpWindowIcon
		implements Icon
	{
		private final String type;
		private final boolean pressed;

		XpWindowIcon( String type ) {
			this( type, false );
		}

		XpWindowIcon( String type, boolean pressed ) {
			this.type = type == null ? "" : type.toLowerCase();
			this.pressed = pressed;
		}

		@Override
		public int getIconWidth() {
			return 14;
		}

		@Override
		public int getIconHeight() {
			return 14;
		}

		@Override
		public void paintIcon( java.awt.Component c, Graphics g, int x, int y ) {
			Graphics2D g2 = (Graphics2D) g.create();
			try {
				int offset = pressed ? 1 : 0;
				g2.translate( x + offset, y + offset );
				g2.setColor( new Color( 0x0d, 0x3e, 0x79, 180 ) );
				if( type.contains( "close" ) ) {
					g2.drawLine( 3, 3, 10, 10 );
					g2.drawLine( 10, 3, 3, 10 );
				} else if( type.contains( "iconify" ) || type.contains( "minimize" ) ) {
					g2.fillRect( 3, 9, 8, 2 );
				} else if( type.contains( "restore" ) ) {
					g2.drawRect( 3, 4, 7, 7 );
					g2.drawLine( 5, 2, 11, 2 );
					g2.drawLine( 11, 2, 11, 8 );
				} else {
					g2.drawRect( 3, 3, 8, 8 );
				}
			} finally {
				g2.dispose();
			}
		}
	}
}
