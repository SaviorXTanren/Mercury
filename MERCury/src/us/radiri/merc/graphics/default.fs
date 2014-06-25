#version 130

uniform sampler2D u_texture;
uniform int u_is_text;

in vec4 v_color;
in vec2 v_texcoord;

void main() {
    // If we are not rendering font...
    if(u_is_text == 0) {
        vec4 tex = texture2D(u_texture, v_texcoord);
        gl_FragColor = vec4(min(tex.rgb + v_color.rgb, 255), tex.a * v_color.a);
    // If we are rendering font...
    } else {
       gl_FragColor = vec4(1, 1, 1, texture2D(u_texture, v_texcoord).a)*v_color;
    }
}