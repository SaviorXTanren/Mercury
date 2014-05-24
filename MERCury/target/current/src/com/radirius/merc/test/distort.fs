uniform sampler2D u_texture;
varying vec4 v_texcoord;
const float PI = 3.1415926535;
uniform float BarrelPower=3;

vec2 Distort(vec2 p)
{
    float theta  = atan(p.y, p.x);
    float radius = length(p);
    radius = pow(radius, BarrelPower);
    p.x = radius * cos(theta);
    p.y = radius * sin(theta);
    return 0.5 * (p + 1.0);
}

void main()
{
  vec2 xy = 2.0 * v_texcoord.xy - 1.0;
  vec2 uv;
  float d = length(xy);
  if (d < 1.0)
  {
    uv = Distort(xy);
  }
  else
  {
    uv = v_texcoord.xy;
  }
  vec4 c = texture2D(u_texture, uv);
  gl_FragColor = c;
}