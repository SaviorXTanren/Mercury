package org.radirius.merc.gfx;

import static org.lwjgl.opengl.GL20.glDeleteProgram;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.radirius.merc.res.Loader;
import org.radirius.merc.res.Resource;

/**
 * An object version of shaders. Does all of the tedious stuff for you and lets
 * you use the shader easily.
 * 
 * @author wessles, opiop65
 */

public class Shader implements Resource {
    /** The vertex shader type. */
    public static final int VERTEX_SHADER = 0;
    /** The fragment shader type. */
    public static final int FRAGMENT_SHADER = 1;
    
    private final int programobject;
    
    /**
     * @param programobject
     *            The id for the program object you wish to encapsulate.
     */
    public Shader(int programobject) {
        this.programobject = programobject;
    }
    
    /** @return The id for the encapsulated program object. */
    public int getProgramObject() {
        return programobject;
    }
    
    /** Uses the shader (only use if you know what you are doing). */
    public void use() {
        ARBShaderObjects.glUseProgramObjectARB(programobject);
    }
    
    /** Sets the shader to default. */
    public void release() {
        releaseShaders();
    }
    
    /**
     * Passes a uniform variable into the shader
     * 
     * @param name
     *            The name of the variable.
     * @param values
     *            The values you wish to pass in.
     */
    public void setUniformf(String name, float... values) {
        int location = ARBShaderObjects.glGetUniformLocationARB(programobject, name);
        
        if (values.length == 1)
            ARBShaderObjects.glUniform1fARB(location, values[0]);
        else if (values.length == 2)
            ARBShaderObjects.glUniform2fARB(location, values[0], values[1]);
        else if (values.length == 3)
            ARBShaderObjects.glUniform3fARB(location, values[0], values[1], values[2]);
        else if (values.length == 4)
            ARBShaderObjects.glUniform4fARB(location, values[0], values[1], values[2], values[3]);
    }
    
    /**
     * Passes a uniform variable into the shader
     * 
     * @param name
     *            The name of the variable.
     * @param values
     *            The values you wish to pass in.
     */
    public void setUniformi(String name, int... values) {
        int location = ARBShaderObjects.glGetUniformLocationARB(programobject, name);
        
        if (values.length == 1)
            ARBShaderObjects.glUniform1iARB(location, values[0]);
        else if (values.length == 2)
            ARBShaderObjects.glUniform2iARB(location, values[0], values[1]);
        else if (values.length == 3)
            ARBShaderObjects.glUniform3iARB(location, values[0], values[1], values[2]);
        else if (values.length == 4)
            ARBShaderObjects.glUniform4iARB(location, values[0], values[1], values[2], values[3]);
    }
    
    @Override
    public void clean() {
        glDeleteProgram(programobject);
    }
    
    /**
     * Staticly 'use().'
     */
    public static void useShader(Shader shader) {
        shader.use();
    }
    
    /**
     * Set the shader to default.
     */
    public static void releaseShaders() {
        ARBShaderObjects.glUseProgramObjectARB(DEFAULT_SHADER.programobject);
    }
    
    /**
     * @return A shader based off of the two program objects vert and frag.
     */
    public static Shader getShader(int vert, int frag) {
        int program = ARBShaderObjects.glCreateProgramObjectARB();
        
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
    
    /**
     * @param vin
     *            The vertex shader's file
     * @param fin
     *            The fragment shader's file.
     * @return A shader based off of the files in vin and fin.
     */
    public static Shader getShader(InputStream vin, InputStream fin) {
        return getShader(readShader(vin), readShader(fin));
    }
    
    /**
     * @param vsrc
     *            The source of the vertex shader.
     * @param fsrc
     *            The source of the fragment shader.
     * @return A shader based off of the sources vsrc and fsrc.
     */
    public static Shader getShader(String vsrc, String fsrc) {
        int vertShader = 0;
        int fragShader = 0;
        
        try {
            vertShader = createVertexShader(vsrc);
            fragShader = createFragmentShader(fsrc);
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        } finally {
            if (vertShader == 0 || fragShader == 0)
                return null;
        }
        
        int program = ARBShaderObjects.glCreateProgramObjectARB();
        
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
    
    /**
     * @param src
     *            The stream to the source file.
     * @param type
     *            The type of shader (the other half will use the MERCury
     *            default shader).
     * @return A shader based off of the stream src, of the type type.
     */
    public static Shader getShader(InputStream src, int type) {
        return getShader(readShader(src), type);
    }
    
    /**
     * @param src
     *            The source of the shader.
     * @param type
     *            The type of shader (the other half will use the MERCury
     *            default shader).
     * @return A shader based off of the src of the type type.
     */
    public static Shader getShader(String src, int type) {
        int vertShader = 0;
        int fragShader = 0;
        
        try {
            if (type == Shader.FRAGMENT_SHADER) {
                vertShader = createVertexShader(readShader(Loader.streamFromClasspath("org/radirius/merc/gfx/shaders/default_shader.vs")));
                fragShader = createFragmentShader(src);
            } else if (type == Shader.VERTEX_SHADER) {
                fragShader = createFragmentShader(readShader(Loader.streamFromClasspath("org/radirius/merc/gfx/shaders/default_shader.fs")));
                vertShader = createVertexShader(src);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        } finally {
            if (vertShader == 0 || fragShader == 0)
                return null;
        }
        
        int program = ARBShaderObjects.glCreateProgramObjectARB();
        
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
    
    private static int createVertexShader(String src) {
        return createShader(src, ARBVertexShader.GL_VERTEX_SHADER_ARB);
    }
    
    private static int createFragmentShader(String src) {
        return createShader(src, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
    }
    
    private static int createShader(String shadersource, int shadertype) {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shadertype);
            
            if (shader == 0)
                return 0;
            
            ARBShaderObjects.glShaderSourceARB(shader, shadersource);
            ARBShaderObjects.glCompileShaderARB(shader);
            
            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                throw new RuntimeException("Error creating shader: " + ARBShaderObjects.glGetInfoLogARB(shader, ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            
            return shader;
        } catch (Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exc;
        }
    }
    
    private static String readShader(InputStream in) {
        StringBuilder source = new StringBuilder();
        
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
                try {
                    throw exception;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        
        return source.toString();
    }
    
    public static Shader DEFAULT_SHADER;
    
    /** The default shader for MERCury (not to be confused with shader 0). */
    public static void loadDefaultShaders() {
        if (DEFAULT_SHADER == null)
            DEFAULT_SHADER = Shader.getShader(Loader.streamFromClasspath("org/radirius/merc/gfx/shaders/default_shader.vs"), Loader.streamFromClasspath("org/radirius/merc/gfx/shaders/default_shader.fs"));
    }
}