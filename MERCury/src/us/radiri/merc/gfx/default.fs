#version 130

uniform sampler2D u_texture;
uniform int u_is_text;

in vec4 v_color;
in vec2 v_texcoord;

void main() {
    if(u_is_text == 0) {
        vec4 tex = texture2D(u_texture, v_texcoord);
        gl_FragColor.rgb = min(v_color.rgb+tex.rgb,255);
        gl_FragColor.a = v_color.a*tex.a;
    } else {
       gl_FragColor = vec4(1, 1, 1, texture2D(u_texture, v_texcoord).a)*v_color;
    }
}