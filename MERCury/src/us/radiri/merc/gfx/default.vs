#version 130

out vec4 v_color;
out vec2 v_texcoord;

void main()
{
    v_color = gl_Color;
	v_texcoord = gl_MultiTexCoord0.xy;
	gl_Position = ftransform(); 
}