package com.radirius.mercury.input;

import java.util.HashMap;

import static com.radirius.mercury.input.Input.*;

/**
 * Used for alternate layouts or control schemes.
 *
 * @author wessles
 */
public class Mapper {
	/**
	 * A mapper for dvorak layouts. Temporary until we implement LWJGL3, which will recognize mechanical locations as
	 * opposed to virtual ones. We can't upgrade to LWJGL3 until it is further developed.
	 */
	public static Mapper LAYOUT_DVORAK = new Mapper();

	static {
		LAYOUT_DVORAK.add(KEY_MINUS, KEY_APOSTROPHE);
		LAYOUT_DVORAK.add(KEY_EQUALS, KEY_RBRACKET);
		LAYOUT_DVORAK.add(KEY_Q, KEY_APOSTROPHE);
		LAYOUT_DVORAK.add(KEY_W, KEY_COMMA);
		LAYOUT_DVORAK.add(KEY_E, KEY_PERIOD);
		LAYOUT_DVORAK.add(KEY_R, KEY_P);
		LAYOUT_DVORAK.add(KEY_T, KEY_Y);
		LAYOUT_DVORAK.add(KEY_Y, KEY_F);
		LAYOUT_DVORAK.add(KEY_U, KEY_G);
		LAYOUT_DVORAK.add(KEY_I, KEY_C);
		LAYOUT_DVORAK.add(KEY_O, KEY_R);
		LAYOUT_DVORAK.add(KEY_P, KEY_L);
		LAYOUT_DVORAK.add(KEY_LBRACKET, KEY_SLASH);
		LAYOUT_DVORAK.add(KEY_RBRACKET, KEY_EQUALS);
		LAYOUT_DVORAK.add(KEY_S, KEY_O);
		LAYOUT_DVORAK.add(KEY_D, KEY_E);
		LAYOUT_DVORAK.add(KEY_F, KEY_U);
		LAYOUT_DVORAK.add(KEY_G, KEY_I);
		LAYOUT_DVORAK.add(KEY_H, KEY_D);
		LAYOUT_DVORAK.add(KEY_J, KEY_H);
		LAYOUT_DVORAK.add(KEY_K, KEY_T);
		LAYOUT_DVORAK.add(KEY_L, KEY_N);
		LAYOUT_DVORAK.add(KEY_SEMICOLON, KEY_S);
		LAYOUT_DVORAK.add(KEY_APOSTROPHE, KEY_MINUS);
		LAYOUT_DVORAK.add(KEY_Z, KEY_SEMICOLON);
		LAYOUT_DVORAK.add(KEY_X, KEY_Q);
		LAYOUT_DVORAK.add(KEY_C, KEY_J);
		LAYOUT_DVORAK.add(KEY_V, KEY_K);
		LAYOUT_DVORAK.add(KEY_B, KEY_X);
		LAYOUT_DVORAK.add(KEY_N, KEY_B);
		LAYOUT_DVORAK.add(KEY_COMMA, KEY_W);
		LAYOUT_DVORAK.add(KEY_PERIOD, KEY_V);
		LAYOUT_DVORAK.add(KEY_SLASH, KEY_Z);
	}

	public static final int NO_VALUE = -1;

	private HashMap<Integer, Integer> map = new HashMap<>();

	public Mapper add(int oldKey, int newKey) {
		map.put(oldKey, newKey);
		return this;
	}

	public int get(int oldKey) {
		if (map.keySet().contains(oldKey))
			return map.get(oldKey);
		else
			return NO_VALUE;
	}
}
