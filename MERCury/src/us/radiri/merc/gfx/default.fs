#version 130

uniform sampler2D u_texture;

in vec4 v_color;
in vec2 v_texcoord;

void main()
{
    gl_FragColor = texture2D(u_texture, v_texcoord);
    gl_FragColor.rgb += v_color.rgb;
}