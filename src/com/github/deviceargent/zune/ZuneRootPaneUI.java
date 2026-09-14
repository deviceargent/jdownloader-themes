package com.github.deviceargent.zune;

import com.formdev.flatlaf.ui.FlatRootPaneUI;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class ZuneRootPaneUI
	extends FlatRootPaneUI
{
	public static ComponentUI createUI( JComponent c ) {
		return new ZuneRootPaneUI();
	}

	@Override
	protected com.formdev.flatlaf.ui.FlatTitlePane createTitlePane() {
		return new ZuneTitlePane( rootPane );
	}
}
