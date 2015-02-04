package com.radirius.mercury.graphics.wip.gui.themes;

import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.wip.gui.*;

/**
 * The default GUI theme for Mercury, applied to all components initially.
 *
 * @author wessles
 */
public class DefaultTheme extends Theme {
	public DefaultTheme() {
		addNode(com.radirius.mercury.graphics.wip.gui.TextField.class, new ThemeNode() {
			public void apply(Component component) {
				component.textColor = Color.BLACK;
				component.background = Component.BackgroundType.coloredBackground;
				component.backgroundColor = new Color(0.7f, 0.7f, 0.7f);

				component.marginUp = component.marginDown = component.marginLeft = component.marginRight = component.paddingLeft = component.paddingRight = 1f;
			}
		});

		addNode(com.radirius.mercury.graphics.wip.gui.TextBar.class, new ThemeNode() {
			public void apply(Component component) {
				component.border = Component.BorderType.border;
				component.borderColor = new Color(0.3f, 0.3f, 0.3f);
				component.focusedBackgroundColor = new Color(0.9f, 0.9f, 0.9f);

				component.paddingUp = component.paddingDown = 0;
			}
		});

		addNode(com.radirius.mercury.graphics.wip.gui.Button.class, new ThemeNode() {
			@Override
			public void apply(Component component) {
				component.border = Component.BorderType.border;
				component.borderColor = new Color(0.3f, 0.3f, 0.5f);
				component.backgroundColor = new Color(0.7f, 0.7f, 0.8f);
				component.hoveredBackgroundColor = component.backgroundColor.duplicate().multiply(1.1f);
				component.clickedBackgroundColor = component.borderColor;
				component.clickedBorderColor = component.backgroundColor;
				component.clickedTextColor = component.borderColor;
			}
		});

		addNode(com.radirius.mercury.graphics.wip.gui.Checkbox.class, new ThemeNode() {
			@Override
			public void apply(Component component) {
				Checkbox checkbox = (Checkbox) component;
				checkbox.textColor = Color.BLACK.duplicate().add(0.4f);
				checkbox.focusedTextColor = Color.BLACK;
			}
		});
	}
}
