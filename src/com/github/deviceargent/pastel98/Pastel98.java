package com.github.deviceargent.pastel98;

import com.formdev.flatlaf.FlatLightLaf;

public class Pastel98
	extends FlatLightLaf
{
	public static final String NAME = "Pastel98";

	public static boolean setup() {
		return setup( new Pastel98() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, Pastel98.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
