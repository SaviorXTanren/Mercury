#version 330 core

uniform mat4 proj; // Projection Matrix
uniform mat4 view; // View Matrix

layout(location=0) in vec2 position;
layout(location=1) in vec4 color;
layout(location=2) in vec2 texcoord;

out vec4 v_color;
out vec2 v_texcoord;

void main()
{
    v_color = color;
	v_texcoord = texcoord;
	gl_Position = proj * view * vec4(position, 0.0, 1.0);
}
