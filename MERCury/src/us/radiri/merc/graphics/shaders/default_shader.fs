#version 130

uniform sampler2D u_texture;

in vec4 v_color;
in vec2 v_texcoord;

void main() {
    vec4 tex = texture2D(u_texture, v_texcoord);
    gl_FragColor = vec4(min(tex.rgb + v_color.rgb, 255), tex.a * v_color.a);
}