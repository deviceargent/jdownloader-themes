package com.github.deviceargent.airlock;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIDefaults;

public class Airlock extends FlatDarkLaf {
	public static final String NAME = "Airlock";

	public static boolean setup() {
		return setup( new Airlock() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, Airlock.class );
	}

	@Override
	public UIDefaults getDefaults() {
		return super.getDefaults();
	}

	@Override
	public String getName() {
		return NAME;
	}
}
