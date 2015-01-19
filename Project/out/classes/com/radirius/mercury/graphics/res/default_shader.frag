#version 330 core

uniform sampler2D u_texture;

in vec4 v_color;
in vec2 v_texcoord;

out vec4 fragColor;

void main() {
	vec4 tex = texture(u_texture, v_texcoord);
	fragColor = vec4(min(tex.rgb + v_color.rgb, vec3(1.0)), tex.a * v_color.a);
}
