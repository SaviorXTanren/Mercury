#version 130

uniform sampler2D u_texture;

in vec4 v_color;
in vec2 v_texcoord;

void main() {
    gl_FragColor = vec4(1, 1, 1, texture2D(u_texture, v_texcoord).a) * v_color;
}