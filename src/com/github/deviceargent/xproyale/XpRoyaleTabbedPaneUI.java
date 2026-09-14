package com.github.deviceargent.xproyale;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class XpRoyaleTabbedPaneUI
	extends FlatTabbedPaneUI
{
	private static final Color ORANGE = new Color( 0xe9, 0x7b, 0x2f );
	private static final Color GOLD = new Color( 0xf4, 0xc5, 0x4b );

	public static ComponentUI createUI( JComponent c ) {
		return new XpRoyaleTabbedPaneUI();
	}

	@Override
	protected void paintTabSelection( Graphics g, int tabPlacement, int x, int y, int w, int h, int selectedIndex ) {
		if( tabPlacement != javax.swing.JTabbedPane.TOP && tabPlacement != javax.swing.JTabbedPane.BOTTOM )
			return;
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			g2.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
			int lineHeight = Math.max( 2, tabSelectionHeight );
			int lineY = tabPlacement == javax.swing.JTabbedPane.TOP ? y + h - lineHeight : y;
			g2.setPaint( new GradientPaint( x, lineY, GOLD, x + w / 2f, lineY, ORANGE, true ) );
			g2.fillRect( x, lineY, Math.max( 1, w / 2 ), lineHeight );
			g2.setPaint( new GradientPaint( x + w / 2f, lineY, ORANGE, x + w, lineY, GOLD, true ) );
			g2.fillRect( x + w / 2, lineY, w - w / 2, lineHeight );
		} finally {
			g2.dispose();
		}
	}
}
