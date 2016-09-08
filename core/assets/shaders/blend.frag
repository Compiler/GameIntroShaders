#version 130

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec2 pos;
varying float f_time;

uniform sampler2D u_texture;
uniform sampler2D u_texture1;
uniform mat4 u_projTrans;

void main() {
		vec4 oth = texture2D(u_texture1, v_texCoords);
		oth.a = .5f;
	    gl_FragColor = vec4(texture2D(u_texture, v_texCoords).rgb, f_time/10);
}