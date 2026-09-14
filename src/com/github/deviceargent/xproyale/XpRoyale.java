package com.github.deviceargent.xproyale;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

public class XpRoyale
	extends FlatLightLaf
{
	public static final String NAME = "XpRoyale";

	public static boolean setup() {
		boolean installed = setup( new XpRoyale() );
		UIManager.put( "TabbedPaneUI", XpRoyaleTabbedPaneUI.class.getName() );
		return installed;
	}

	public static void installLafInfo() {
		installLafInfo( NAME, XpRoyale.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
