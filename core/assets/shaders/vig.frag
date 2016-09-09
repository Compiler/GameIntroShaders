
varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform sampler2D u_sampler2D;
uniform mat4 u_projTrans;
uniform float time;


const float outerRadius = .65f, innerRadius = .4f, intensity = .6f;

void main() {
	vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
	
	vec2 relativePosition = gl_FragCoord.xy / u_resolution - 0.5f;
	
	float len = length(relativePosition);
	float vignette = 0;
    vignette = smoothstep(.5f, .3f, len);
	color.rgb = mix(color.rgb, color.rgb * vignette, 0.95f);

	gl_FragColor = color;
}