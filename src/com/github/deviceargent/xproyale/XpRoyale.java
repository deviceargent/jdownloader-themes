package com.github.deviceargent.xproyale;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import javax.swing.UIDefaults;

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
	public UIDefaults getDefaults() {
		UIDefaults defaults = super.getDefaults();
		defaults.put( "TabbedPaneUI", XpRoyaleTabbedPaneUI.class.getName() );
		return defaults;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
