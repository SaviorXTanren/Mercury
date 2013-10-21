package com.wessles.MERCury.opengl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

/**
 * An object version of shaders. Does all of the tedius stuff for you and lets you use the shader easily.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class Shader {
	public static final int DEFAULT_SHADER = 0;

	private int programobject;

	public Shader(int programobject) {
		this.programobject = programobject;
	}

	public int getProgramObject() {
		return programobject;
	}

	public void use() {
		ARBShaderObjects.glUseProgramObjectARB(programobject);
	}

	public void release() {
		ARBShaderObjects.glUseProgramObjectARB(DEFAULT_SHADER);
	}
	
	public void setUniformf(String name, float...values) {
		int location = ARBShaderObjects.glGetUniformLocationARB(programobject, name);
		
		if(values.length == 1)
			ARBShaderObjects.glUniform1fARB(location, values[0]);
		else if(values.length == 2)
			ARBShaderObjects.glUniform2fARB(location, values[0], values[1]);
		else if(values.length == 3)
			ARBShaderObjects.glUniform3fARB(location, values[0], values[1], values[2]);
		else if(values.length == 4)
			ARBShaderObjects.glUniform4fARB(location, values[0], values[1], values[2], values[3]);
	}
	
	public void setUniformi(String name, int...values) {
		int location = ARBShaderObjects.glGetUniformLocationARB(programobject, name);
		
		if(values.length == 1)
			ARBShaderObjects.glUniform1iARB(location, values[0]);
		else if(values.length == 2)
			ARBShaderObjects.glUniform2iARB(location, values[0], values[1]);
		else if(values.length == 3)
			ARBShaderObjects.glUniform3iARB(location, values[0], values[1], values[2]);
		else if(values.length == 4)
			ARBShaderObjects.glUniform4iARB(location, values[0], values[1], values[2], values[3]);
	}

	public static void useShader(Shader shader) {
		shader.use();
	}

	public static void releaseShaders() {
		ARBShaderObjects.glUseProgramObjectARB(DEFAULT_SHADER);
	}

	public static Shader getShader(int vert, int frag) {
		int program = 0;

		program = ARBShaderObjects.glCreateProgramObjectARB();

		if (program == 0)
			return null;

		ARBShaderObjects.glAttachObjectARB(program, vert);
		ARBShaderObjects.glAttachObjectARB(program, frag);

		ARBShaderObjects.glLinkProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
			return null;
		}

		ARBShaderObjects.glValidateProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
			return null;
		}

		return new Shader(program);
	}

	public static Shader getShader(String locationvert, String locationfrag) {
		int vertShader = 0;
		int fragShader = 0;
		int program = 0;

		try {
			vertShader = createVertexShader(locationvert);
			fragShader = createFragmentShader(locationfrag);
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		} finally {
			if (vertShader == 0 || fragShader == 0)
				return null;
		}

		program = ARBShaderObjects.glCreateProgramObjectARB();

		if (program == 0)
			return null;

		ARBShaderObjects.glAttachObjectARB(program, vertShader);
		ARBShaderObjects.glAttachObjectARB(program, fragShader);

		ARBShaderObjects.glLinkProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
			return null;
		}

		ARBShaderObjects.glValidateProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
			return null;
		}

		return new Shader(program);
	}

	private static int createVertexShader(String filename) throws Exception {
		return createShader(filename, ARBVertexShader.GL_VERTEX_SHADER_ARB);
	}

	private static int createFragmentShader(String filename) throws Exception {
		return createShader(filename, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
	}

	private static int createShader(String filename, int shaderType) throws Exception {
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

			if (shader == 0)
				return 0;

			ARBShaderObjects.glShaderSourceARB(shader, readShader(filename));
			ARBShaderObjects.glCompileShaderARB(shader);

			if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
				throw new RuntimeException("Error creating shader: " + ARBShaderObjects.glGetInfoLogARB(shader, ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));

			return shader;
		} catch (Exception exc) {
			ARBShaderObjects.glDeleteObjectARB(shader);
			throw exc;
		}
	}

	private static String readShader(String filename) throws Exception {
		StringBuilder source = new StringBuilder();

		FileInputStream in = new FileInputStream(filename);

		Exception exception = null;

		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			Exception innerExc = null;
			try {
				String line;
				while ((line = reader.readLine()) != null)
					source.append(line).append('\n');
			} catch (Exception exc) {
				exception = exc;
			} finally {
				try {
					reader.close();
				} catch (Exception exc) {
					if (innerExc == null)
						innerExc = exc;
					else
						exc.printStackTrace();
				}
			}

			if (innerExc != null)
				throw innerExc;
		} catch (Exception exc) {
			exception = exc;
		} finally {
			try {
				in.close();
			} catch (Exception exc) {
				if (exception == null)
					exception = exc;
				else
					exc.printStackTrace();
			}

			if (exception != null)
				throw exception;
		}

		return source.toString();
	}
	
	public static Shader getEmptyShader() {
		return new Shader(DEFAULT_SHADER);
	}
}
