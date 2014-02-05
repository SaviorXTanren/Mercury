#version 130

varying vec4 v_color;
varying vec2 v_texcoord;

void main()
{
	v_color = gl_Color.rgba;
	v_texcoord = gl_MultiTexCoord0.xy;
	gl_Position = ftransform(); 
}