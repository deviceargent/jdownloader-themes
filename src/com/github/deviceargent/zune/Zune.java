package com.github.deviceargent.zune;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIDefaults;

public class Zune
	extends FlatDarkLaf
{
	public static final String NAME = "Zune";

	public static boolean setup() {
		return setup( new Zune() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, Zune.class );
	}

	@Override
	public UIDefaults getDefaults() {
		UIDefaults defaults = super.getDefaults();
		return defaults;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
