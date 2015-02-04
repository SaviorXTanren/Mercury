package com.radirius.mercury.graphics.wip.gui;

import com.radirius.mercury.graphics.Color;

/**
 * A panel component.
 *
 * @author wessles
 */
public class Panel extends Component {
	public Panel() {
		width = 356;
		setPadding(10f);

		backgroundColor = Color.WHITE.duplicate().multiply(.8f);
		textColor = Color.BLACK;
	}
}
