package org.radirius.merc.util.easing;

import static org.radirius.merc.util.easing.EasingUtils.BOUNCING_EASE_CIRC;
import static org.radirius.merc.util.easing.EasingUtils.BOUNCING_EASE_CUBIC;
import static org.radirius.merc.util.easing.EasingUtils.BOUNCING_EASE_EXPO;
import static org.radirius.merc.util.easing.EasingUtils.BOUNCING_EASE_QUAD;
import static org.radirius.merc.util.easing.EasingUtils.BOUNCING_EASE_QUINT;
import static org.radirius.merc.util.easing.EasingUtils.BOUNCING_EASE_SINE;
import static org.radirius.merc.util.easing.EasingUtils.BOUNCING_LINEAR_TWEEN;
import static org.radirius.merc.util.easing.EasingUtils.EASE_CIRC;
import static org.radirius.merc.util.easing.EasingUtils.EASE_CUBIC;
import static org.radirius.merc.util.easing.EasingUtils.EASE_EXPO;
import static org.radirius.merc.util.easing.EasingUtils.EASE_QUAD;
import static org.radirius.merc.util.easing.EasingUtils.EASE_QUINT;
import static org.radirius.merc.util.easing.EasingUtils.EASE_SINE;
import static org.radirius.merc.util.easing.EasingUtils.LINEAR_TWEEN;
import static org.radirius.merc.util.easing.EasingUtils.bouncingEaseCirc;
import static org.radirius.merc.util.easing.EasingUtils.bouncingEaseCubic;
import static org.radirius.merc.util.easing.EasingUtils.bouncingEaseExpo;
import static org.radirius.merc.util.easing.EasingUtils.bouncingEaseQuad;
import static org.radirius.merc.util.easing.EasingUtils.bouncingEaseQuint;
import static org.radirius.merc.util.easing.EasingUtils.bouncingEaseSine;
import static org.radirius.merc.util.easing.EasingUtils.bouncingLinearTween;
import static org.radirius.merc.util.easing.EasingUtils.easeCirc;
import static org.radirius.merc.util.easing.EasingUtils.easeCubic;
import static org.radirius.merc.util.easing.EasingUtils.easeExpo;
import static org.radirius.merc.util.easing.EasingUtils.easeQuad;
import static org.radirius.merc.util.easing.EasingUtils.easeQuint;
import static org.radirius.merc.util.easing.EasingUtils.easeSine;
import static org.radirius.merc.util.easing.EasingUtils.linearTween;

/**
 * An object that makes easing easier.
 * 
 * @author wessles
 */

public class EasingValue {
    public int type;
    
    public float start, end, value;
    public long start_millis, duration_millis;
    
    public EasingValue(int type, float start, float end, long duration_millis) {
        this(type, System.currentTimeMillis(), start, end, duration_millis);
    }
    
    public EasingValue(int type, long start_millis, float start, float end, long duration_millis) {
        this.type = type;
        this.start_millis = start_millis;
        this.start = start;
        this.end = end;
        value = start;
        this.duration_millis = duration_millis;
    }
    
    public float get() {
        float value = 0;
        switch (type) {
            case LINEAR_TWEEN:
                value = linearTween(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case BOUNCING_LINEAR_TWEEN:
                value = bouncingLinearTween(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case EASE_QUAD:
                value = easeQuad(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case BOUNCING_EASE_QUAD:
                value = bouncingEaseQuad(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case EASE_CUBIC:
                value = easeCubic(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case BOUNCING_EASE_CUBIC:
                value = bouncingEaseCubic(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case EASE_QUINT:
                value = easeQuint(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case BOUNCING_EASE_QUINT:
                value = bouncingEaseQuint(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case EASE_SINE:
                value = easeSine(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case BOUNCING_EASE_SINE:
                value = bouncingEaseSine(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case EASE_EXPO:
                value = easeExpo(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case BOUNCING_EASE_EXPO:
                value = bouncingEaseExpo(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case EASE_CIRC:
                value = easeCirc(System.currentTimeMillis() - start_millis, start, end, duration_millis);
            case BOUNCING_EASE_CIRC:
                value = bouncingEaseCirc(System.currentTimeMillis() - start_millis, start, end, duration_millis);
        }
        return value;
    }
    
    public void reset() {
        reset(0);
    }
    
    public void reset(int point) {
        start_millis = System.currentTimeMillis() - point;
    }
}
