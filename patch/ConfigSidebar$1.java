package jd.gui.swing.jdgui.views.settings.sidebar;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.lang.reflect.Field;
import javax.swing.JList;
import javax.swing.UIManager;

class ConfigSidebar$1
	extends JList
{
	private static final long serialVersionUID = 1L;
	final ConfigSidebar this$0;
	private static Field mouseField;

	static {
		try {
			mouseField = ConfigSidebar.class.getDeclaredField( "mouse" );
			mouseField.setAccessible( true );
		} catch( Exception e ) {}
	}

	ConfigSidebar$1( ConfigSidebar c ) {
		super();
		this$0 = c;
	}

	private Point getMouse() {
		try {
			if( mouseField != null )
				return (Point) mouseField.get( this$0 );
		} catch( Exception e ) {}
		return null;
	}

	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		Point m = getMouse();
		if( m == null ) {
			this$0.revalidate();
			return;
		}
		int i = locationToIndex( m );
		if( i < 0 ) {
			this$0.revalidate();
			return;
		}
		Point pt = indexToLocation( i );
		if( pt == null ) {
			this$0.revalidate();
			return;
		}
		int h;
		if( getModel().getElementAt( i ) instanceof jd.gui.swing.jdgui.views.settings.panels.advanced.AdvancedSettings )
			h = TreeRenderer.SMALL_DIMENSION.height;
		else
			h = TreeRenderer.DIMENSION.height;
		Color hoverC = UIManager.getColor( "ConfigSidebar.hoverBackground" );
		if( hoverC == null )
			hoverC = new Color( 0xc4, 0xa0, 0xa0 );
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			g2.setComposite( AlphaComposite.getInstance( 3, 0.12f ) );
			g2.setColor( hoverC );
			g2.fillRect( 0, pt.y, getWidth(), h );
		} finally {
			g2.dispose();
		}
		this$0.revalidate();
	}
}