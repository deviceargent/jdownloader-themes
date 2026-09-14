package com.github.deviceargent.xproyale;

import com.formdev.flatlaf.ui.FlatRootPaneUI;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class XpRoyaleRootPaneUI
	extends FlatRootPaneUI
{
	public static ComponentUI createUI( JComponent c ) {
		return new XpRoyaleRootPaneUI();
	}

	@Override
	protected com.formdev.flatlaf.ui.FlatTitlePane createTitlePane() {
		return new XpRoyaleTitlePane( rootPane );
	}
}
