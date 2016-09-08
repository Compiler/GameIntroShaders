
varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
uniform mat4 u_projTrans;
uniform float anim;
void main() {
	vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
	color.rgb = vec3(color.rr * (0.1f + anim), 0).rgb * color.rgb;
	gl_FragColor = color;
}