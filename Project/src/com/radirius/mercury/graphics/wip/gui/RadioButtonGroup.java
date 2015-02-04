package com.radirius.mercury.graphics.wip.gui;

/**
 * A parent component that hosts checkboxes to make sure only one can be selected at a time.
 *
 * @author wessles
 */
public class RadioButtonGroup extends Component {

	public RadioButtonGroup(Checkbox... checkboxes) {
		this(null, checkboxes);
	}

	public RadioButtonGroup(String message, Checkbox... checkboxes) {
		super(message);
		addChild(checkboxes);
	}

	protected Checkbox checkedChild = null;

	@Override
	public void update() {
		super.update();

		boolean unTickTheRest = false;

		if (checkedChild != null)
			if (!checkedChild.isTicked)
				checkedChild.tick();

		for (Object genericChild : children) {
			if (genericChild instanceof Checkbox) {
				Checkbox child = (Checkbox) genericChild;

				if (child != checkedChild)
					if (child.isTicked) {
						if (checkedChild != null)
							checkedChild.unTick();
						checkedChild = child;
					}

			}
		}
	}

	public void unTickAll() {
		if (checkedChild == null)
			return;

		checkedChild.unTick();
		checkedChild = null;
	}
}
