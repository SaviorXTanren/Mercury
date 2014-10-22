package com.radirius.mercury.utilities.easing;

import static com.radirius.mercury.utilities.easing.EasingUtils.BOUNCING_EASE_CIRCLE;
import static com.radirius.mercury.utilities.easing.EasingUtils.BOUNCING_EASE_CUBIC;
import static com.radirius.mercury.utilities.easing.EasingUtils.BOUNCING_EASE_EXPO;
import static com.radirius.mercury.utilities.easing.EasingUtils.BOUNCING_EASE_QUAD;
import static com.radirius.mercury.utilities.easing.EasingUtils.BOUNCING_EASE_QUINT;
import static com.radirius.mercury.utilities.easing.EasingUtils.BOUNCING_EASE_SINE;
import static com.radirius.mercury.utilities.easing.EasingUtils.BOUNCING_LINEAR_TWEEN;
import static com.radirius.mercury.utilities.easing.EasingUtils.EASE_CIRCLE;
import static com.radirius.mercury.utilities.easing.EasingUtils.EASE_CUBIC;
import static com.radirius.mercury.utilities.easing.EasingUtils.EASE_EXPO;
import static com.radirius.mercury.utilities.easing.EasingUtils.EASE_QUAD;
import static com.radirius.mercury.utilities.easing.EasingUtils.EASE_QUINT;
import static com.radirius.mercury.utilities.easing.EasingUtils.EASE_SINE;
import static com.radirius.mercury.utilities.easing.EasingUtils.LINEAR_TWEEN;
import static com.radirius.mercury.utilities.easing.EasingUtils.bouncingEaseCircle;
import static com.radirius.mercury.utilities.easing.EasingUtils.bouncingEaseCubic;
import static com.radirius.mercury.utilities.easing.EasingUtils.bouncingEaseExpo;
import static com.radirius.mercury.utilities.easing.EasingUtils.bouncingEaseQuad;
import static com.radirius.mercury.utilities.easing.EasingUtils.bouncingEaseQuint;
import static com.radirius.mercury.utilities.easing.EasingUtils.bouncingEaseSine;
import static com.radirius.mercury.utilities.easing.EasingUtils.bouncingLinearTween;
import static com.radirius.mercury.utilities.easing.EasingUtils.easeCircle;
import static com.radirius.mercury.utilities.easing.EasingUtils.easeCubic;
import static com.radirius.mercury.utilities.easing.EasingUtils.easeExpo;
import static com.radirius.mercury.utilities.easing.EasingUtils.easeQuad;
import static com.radirius.mercury.utilities.easing.EasingUtils.easeQuint;
import static com.radirius.mercury.utilities.easing.EasingUtils.easeSine;
import static com.radirius.mercury.utilities.easing.EasingUtils.linearTween;

/**
 * An object that makes easing easier. (No Pun Intended).
 *
 * @author wessles, Jeviny
 */
public class EasingValue {
	public int easeType;
	public float startVal, endVal, easeValue;
	public long startMillis, durationMillis;

	public EasingValue(int easeType, float startVal, float endVal, long durationMillis) {
		this(easeType, System.currentTimeMillis(), startVal, endVal, durationMillis);
	}

	public EasingValue(int easeType, long startMillis, float startVal, float endVal, long durationMillis) {
		this.easeType = easeType;
		this.startMillis = startMillis;
		this.startVal = startVal;
		this.endVal = endVal;
		easeValue = startVal;
		this.durationMillis = durationMillis;
	}

	public float get() {
		float easeValue = 0;

		switch (easeType) {
		case LINEAR_TWEEN:
			easeValue = linearTween(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case BOUNCING_LINEAR_TWEEN:
			easeValue = bouncingLinearTween(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case EASE_QUAD:
			easeValue = easeQuad(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case BOUNCING_EASE_QUAD:
			easeValue = bouncingEaseQuad(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case EASE_CUBIC:
			easeValue = easeCubic(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case BOUNCING_EASE_CUBIC:
			easeValue = bouncingEaseCubic(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case EASE_QUINT:
			easeValue = easeQuint(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case BOUNCING_EASE_QUINT:
			easeValue = bouncingEaseQuint(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case EASE_SINE:
			easeValue = easeSine(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case BOUNCING_EASE_SINE:
			easeValue = bouncingEaseSine(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case EASE_EXPO:
			easeValue = easeExpo(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case BOUNCING_EASE_EXPO:
			easeValue = bouncingEaseExpo(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case EASE_CIRCLE:
			easeValue = easeCircle(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		case BOUNCING_EASE_CIRCLE:
			easeValue = bouncingEaseCircle(System.currentTimeMillis() - startMillis, startVal, endVal, durationMillis);
		}

		return easeValue;
	}

	public void reset() {
		reset(0);
	}

	public void reset(int point) {
		startMillis = System.currentTimeMillis() - point;
	}
}
