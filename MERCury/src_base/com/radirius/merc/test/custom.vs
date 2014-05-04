#version 130

varying vec4 v_color;
varying vec2 v_texcoord;

void main()
{
    v_color = gl_Color.rgba+vec4(0.1, 0.1, 0.1, 0.8);
    v_texcoord = gl_MultiTexCoord0.xy+vec2(0.5, 0.5);
    gl_Position = ftransform();
}