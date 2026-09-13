package com.github.deviceargent.sunsettape;

import com.formdev.flatlaf.ui.FlatRootPaneUI;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class SunsetTapeRootPaneUI
	extends FlatRootPaneUI
{
	public static ComponentUI createUI( JComponent c ) {
		return new SunsetTapeRootPaneUI();
	}

	@Override
	protected com.formdev.flatlaf.ui.FlatTitlePane createTitlePane() {
		return new SunsetTapeTitlePane( rootPane );
	}
}
