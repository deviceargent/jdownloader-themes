package com.github.deviceargent.sunsettape;

import com.formdev.flatlaf.FlatLightLaf;

public class SunsetTape
	extends FlatLightLaf
{
	public static final String NAME = "SunsetTape";

	public static boolean setup() {
		return setup( new SunsetTape() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, SunsetTape.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
