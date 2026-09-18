package com.github.deviceargent.vaporgrid;

import com.formdev.flatlaf.FlatDarkLaf;

public class VaporGrid
	extends FlatDarkLaf
{
	public static final String NAME = "VaporGrid";

	public static boolean setup() {
		return setup( new VaporGrid() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, VaporGrid.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
