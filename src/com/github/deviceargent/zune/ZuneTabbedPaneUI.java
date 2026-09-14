package com.github.deviceargent.zune;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

public class ZuneTabbedPaneUI extends FlatTabbedPaneUI {
	public static ZuneTabbedPaneUI createUI( JComponent c ) {
		return new ZuneTabbedPaneUI();
	}

	@Override
	protected void paintContentBorder( Graphics g, int tabPlacement, int selectedIndex ) {
		if( tabPane == null )
			return;
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			int top = calculateTabAreaHeight( tabPane.getTabPlacement(), tabPane.getTabCount() == 0 ? null : tabPane.getBoundsAt( selectedIndex < 0 ? 0 : selectedIndex ) );
			g2.setPaint( new GradientPaint( 0, top, new Color( 45, 59, 70, 210 ), 0, tabPane.getHeight(), new Color( 12, 17, 21, 245 ) ) );
			g2.fillRect( 0, top, tabPane.getWidth(), Math.max( 0, tabPane.getHeight() - top ) );
		} finally {
			g2.dispose();
		}
	}

	private int calculateTabAreaHeight( int placement, java.awt.Rectangle tabBounds ) {
		if( placement == JTabbedPane.TOP )
			return tabBounds == null ? 0 : tabBounds.y + tabBounds.height;
		return 0;
	}
}
