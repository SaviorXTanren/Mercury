package radirius.merc.utils.easing;

/**
 * Mostly nabbed from here http://gizma.com/easing/. Bouncing by wessles.
 * 
 * @author wessles
 */

public class EasingUtils {
    public static final int LINEAR_TWEEN = -1, BOUNCING_LINEAR_TWEEN = 0, EASE_QUAD = 1, BOUNCING_EASE_QUAD = 2, EASE_CUBIC = 3, BOUNCING_EASE_CUBIC = 4, EASE_QUINT = 5, BOUNCING_EASE_QUINT = 6, EASE_SINE = 7, BOUNCING_EASE_SINE = 8, EASE_EXPO = 9, BOUNCING_EASE_EXPO = 10, EASE_CIRC = 11, BOUNCING_EASE_CIRC = 12;
    
    public static float linearTween(float time, float startval, float endval, float duration) {
        float change = endval - startval;
        return change * time / duration + startval;
    }
    
    public static float bouncingLinearTween(float time, float startval, float endval, float duration) {
        if (time < duration / 2)
            return linearTween(time, startval, endval, duration / 2);
        else
            return linearTween(time - duration / 2, endval, startval, duration / 2);
    }
    
    public static float easeQuad(float time, float startval, float endval, float duration) {
        float change = endval - startval;
        time /= duration;
        return change * time * time + startval;
    }
    
    public static float bouncingEaseQuad(float time, float startval, float endval, float duration) {
        if (time < duration / 2)
            return easeQuad(time, startval, endval, duration / 2);
        else
            return easeQuad(time - duration / 2, endval, startval, duration / 2);
    }
    
    public static float easeCubic(float time, float startval, float endval, float duration) {
        float change = endval - startval;
        time /= duration;
        return change * time * time * time + startval;
    }
    
    public static float bouncingEaseCubic(float time, float startval, float endval, float duration) {
        if (time < duration / 2)
            return easeCubic(time, startval, endval, duration / 2);
        else
            return easeCubic(time - duration / 2, endval, startval, duration / 2);
    }
    
    public static float easeQuint(float time, float startval, float endval, float duration) {
        float change = endval - startval;
        time /= duration;
        return change * time * time * time * time * time + startval;
    }
    
    public static float bouncingEaseQuint(float time, float startval, float endval, float duration) {
        if (time < duration / 2)
            return easeQuint(time, startval, endval, duration / 2);
        else
            return easeQuint(time - duration / 2, endval, startval, duration / 2);
    }
    
    public static float easeSine(float time, float startval, float endval, float duration) {
        float change = endval - startval;
        return -change * (float) Math.cos(time / duration * (Math.PI / 2)) + change + startval;
    }
    
    public static float bouncingEaseSine(float time, float startval, float endval, float duration) {
        if (time < duration / 2)
            return easeSine(time, startval, endval, duration / 2);
        else
            return easeSine(time - duration / 2, endval, startval, duration / 2);
    }
    
    public static float easeExpo(float time, float startval, float endval, float duration) {
        float change = endval - startval;
        return change * (float) Math.pow(2, 10 * (time / duration - 1)) + startval;
    }
    
    public static float bouncingEaseExpo(float time, float startval, float endval, float duration) {
        if (time < duration / 2)
            return easeExpo(time, startval, endval, duration / 2);
        else
            return easeExpo(time - duration / 2, endval, startval, duration / 2);
    }
    
    public static float easeCirc(float time, float startval, float endval, float duration) {
        float change = endval - startval;
        time /= duration;
        return -change * ((float) Math.sqrt(1 - time * time) - 1) + startval;
    }
    
    public static float bouncingEaseCirc(float time, float startval, float endval, float duration) {
        if (time < duration / 2)
            return easeCirc(time, startval, endval, duration / 2);
        else
            return easeCirc(time - duration / 2, endval, startval, duration / 2);
    }
}
