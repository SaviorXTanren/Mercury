uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texcoord;

void main() {
	vec4 tex = texture2D(u_texture, v_texcoord);
	gl_FragColor = vec4(min(tex.rgb + v_color.rgb, vec3(1.0)), tex.a * v_color.a);
}