package com.github.deviceargent.xproyale;

import com.formdev.flatlaf.FlatLightLaf;

public class XpRoyale
	extends FlatLightLaf
{
	public static final String NAME = "XpRoyale";

	public static boolean setup() {
		return setup( new XpRoyale() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, XpRoyale.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
