package radirius.merc.utils.easing;

import static radirius.merc.utils.easing.EasingUtils.*;

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
        this.value = start;
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
