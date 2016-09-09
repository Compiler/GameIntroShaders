
varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
uniform mat4 u_projTrans;
uniform float time;
void main() {
//dreamer = R: 225, G: 255, B: 221
	vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
	//color.rgb = vec3(color.r * time, .05f, .05f) * color.rgb;
	color.rgb = ((color.brg * color.rrr)* 1.15f)-time;
	//color.rgb = vec3(0.54, 1.0f, 0.54) / 1.2 * color.rgb;
	gl_FragColor = color;
}