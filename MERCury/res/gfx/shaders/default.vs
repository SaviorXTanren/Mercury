varying vec4 color;

void main()
{
	color = gl_Color.rgba;
	gl_Position = ftransform(); 
}