package com.radirius.mercury.utilities;

import com.radirius.mercury.math.geometry.Matrix4f;

import java.nio.*;
import java.util.Stack;

/**
 * A simple Utility class for the Graphics engine which contains some utility OpenGL rendering functions which are
 * required by the classes in this graphics package. <p/> This class also emulates Matrix-Stack, to simplify the access
 * to Modern OpenGL methods, mainly for Batcher, Camera and the Graphics classes.
 *
 * @author Sri Harsha Chilakapati
 */
public class GraphicsUtils {

	private static Stack<Matrix4f> matrixStack = new Stack<Matrix4f>();
	private static Matrix4f projection = new Matrix4f();

	public static void pushMatrix() {
		matrixStack.push(new Matrix4f());
	}

	public static void pushMatrix(Matrix4f m) {
		matrixStack.push(m);
	}

	public static Matrix4f popMatrix() {
		return matrixStack.pop();
	}

	public static Matrix4f getCurrentMatrix() {
		return matrixStack.peek();
	}

	public static FloatBuffer allocateFloatBuffer(int size) {
		return ByteBuffer.allocateDirect(size << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}

	public static Matrix4f getProjectionMatrix() {
		return projection;
	}
}
