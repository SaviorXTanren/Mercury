package radirius.merc.utilities.easing;

/**
 * A class for easing utilities.
 * 
 * @authors wessles, Jeviny, Robert Penner
 */
public class EasingUtils {
	/** Different easing types. */
	public static final int LINEAR_TWEEN 		  = -1,
							BOUNCING_LINEAR_TWEEN =  0,
							EASE_QUAD 			  =  1,
							BOUNCING_EASE_QUAD    =  2,
							EASE_CUBIC 			  =  3,
							BOUNCING_EASE_CUBIC   =  4,
							EASE_QUINT			  =  5,
							BOUNCING_EASE_QUINT   =  6,
							EASE_SINE 			  =  7,
							BOUNCING_EASE_SINE 	  =  8, 
							EASE_EXPO 			  =  9,
							BOUNCING_EASE_EXPO	  = 10,
							EASE_CIRCLE			  = 11,
							BOUNCING_EASE_CIRCLE  = 12;

	public static float linearTween(float time, float startVal, float endVal, float duration) {
		float change = endVal - startVal;
		
		return change * time / duration + startVal;
	}

	public static float bouncingLinearTween(float time, float startVal, float endVal, float duration) {
		if (time < duration / 2)
			return linearTween(time, startVal, endVal, duration / 2);
		else
			return linearTween(time - duration / 2, endVal, startVal, duration / 2);
	}

	public static float easeQuad(float time, float startVal, float endVal, float duration) {
		float change = endVal - startVal;
		
		time /= duration;
		
		return change * time * time + startVal;
	}

	public static float bouncingEaseQuad(float time, float startVal, float endVal, float duration) {
		if (time < duration / 2)
			return easeQuad(time, startVal, endVal, duration / 2);
		else
			return easeQuad(time - duration / 2, endVal, startVal, duration / 2);
	}

	public static float easeCubic(float time, float startVal, float endVal, float duration) {
		float change = endVal - startVal;
		
		time /= duration;
		
		return change * time * time * time + startVal;
	}

	public static float bouncingEaseCubic(float time, float startVal, float endVal, float duration) {
		if (time < duration / 2)
			return easeCubic(time, startVal, endVal, duration / 2);
		else
			return easeCubic(time - duration / 2, endVal, startVal, duration / 2);
	}

	public static float easeQuint(float time, float startVal, float endVal, float duration) {
		float change = endVal - startVal;
		
		time /= duration;
		
		return change * time * time * time * time * time + startVal;
	}

	public static float bouncingEaseQuint(float time, float startVal, float endVal, float duration) {
		if (time < duration / 2)
			return easeQuint(time, startVal, endVal, duration / 2);
		else
			return easeQuint(time - duration / 2, endVal, startVal, duration / 2);
	}

	public static float easeSine(float time, float startVal, float endVal, float duration) {
		float change = endVal - startVal;
		
		return -change * (float) Math.cos(time / duration * (Math.PI / 2)) + change + startVal;
	}

	public static float bouncingEaseSine(float time, float startVal, float endVal, float duration) {
		if (time < duration / 2)
			return easeSine(time, startVal, endVal, duration / 2);
		else
			return easeSine(time - duration / 2, endVal, startVal, duration / 2);
	}

	public static float easeExpo(float time, float startVal, float endVal, float duration) {
		float change = endVal - startVal;
		
		return change * (float) Math.pow(2, 10 * (time / duration - 1)) + startVal;
	}

	public static float bouncingEaseExpo(float time, float startVal, float endVal, float duration) {
		if (time < duration / 2)
			return easeExpo(time, startVal, endVal, duration / 2);
		else
			return easeExpo(time - duration / 2, endVal, startVal, duration / 2);
	}

	public static float easeCircle(float time, float startVal, float endVal, float duration) {
		float change = endVal - startVal;
		
		time /= duration;
		
		return -change * ((float) Math.sqrt(1 - time * time) - 1) + startVal;
	}

	public static float bouncingEaseCircle(float time, float startVal, float endVal, float duration) {
		if (time < duration / 2)
			return easeCircle(time, startVal, endVal, duration / 2);
		else
			return easeCircle(time - duration / 2, endVal, startVal, duration / 2);
	}
}
