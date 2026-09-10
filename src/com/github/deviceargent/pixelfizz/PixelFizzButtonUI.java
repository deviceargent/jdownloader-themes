package com.github.deviceargent.pixelfizz;

import com.formdev.flatlaf.ui.FlatButtonUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

public class PixelFizzButtonUI
	extends FlatButtonUI
{
	public static final String AURA_KEY = "PixelFizz.aura";

	public PixelFizzButtonUI() {
		super( false );
	}

	public static ComponentUI createUI( JComponent c ) {
		return new PixelFizzButtonUI();
	}

	@Override
	public void paint( Graphics g, JComponent c ) {
		super.paint( g, c );

		if( g instanceof Graphics2D
		    && !"off".equals( c.getClientProperty( AURA_KEY ) )
		    && c.isEnabled() )
		{
			AbstractButton b = (AbstractButton) c;
			Color core = null;

			String force = (String) c.getClientProperty( "PixelFizz.forceAura" );
			if( "focus".equals( force ) ) {
				core = UIManager.getColor( "Component.focusColor" );
			} else if( "hover".equals( force ) ) {
				core = UIManager.getColor( "ProgressBar.foreground" );
				if( core != null )
					core = new Color( core.getRed(), core.getGreen(), core.getBlue(), 160 );
			} else if( b.hasFocus() ) {
				core = UIManager.getColor( "Component.focusColor" );
			} else if( b.getModel().isRollover() ) {
				core = UIManager.getColor( "ProgressBar.foreground" );
				if( core != null )
					core = new Color( core.getRed(), core.getGreen(), core.getBlue(), 160 );
			} else if( b.isSelected() ) {
				core = UIManager.getColor( "Component.focusColor" );
				if( core != null )
					core = new Color( core.getRed(), core.getGreen(), core.getBlue(), 90 );
			}

			if( core != null )
				paintAura( (Graphics2D) g, c, core );
		}
	}

	private static void paintAura( Graphics2D g, JComponent c, Color core ) {
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

			int w = c.getWidth();
			int h = c.getHeight();
			int arc = UIManager.getInt( "Button.arc" );
			if( arc <= 0 )
				arc = 6;

			int r = core.getRed(), gr = core.getGreen(), bl = core.getBlue();
			int[] alphas = { 80, 48, 22 };
			for( int i = 0; i < 3; i++ ) {
				g2.setColor( new Color( r, gr, bl, alphas[i] ) );
				g2.drawRoundRect( i + 1, i + 1, w - 2 * (i + 1) - 1, h - 2 * (i + 1) - 1,
					arc + 4 - i, arc + 4 - i );
			}
		} finally {
			g2.dispose();
		}
	}
}
