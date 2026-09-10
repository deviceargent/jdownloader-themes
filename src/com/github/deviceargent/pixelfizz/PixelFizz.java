package com.github.deviceargent.pixelfizz;

import com.formdev.flatlaf.FlatDarkLaf;

public class PixelFizz
	extends FlatDarkLaf
{
	public static final String NAME = "PixelFizz";

	public static boolean setup() {
		return setup( new PixelFizz() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, PixelFizz.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
