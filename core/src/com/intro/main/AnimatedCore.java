package com.intro.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

public class AnimatedCore extends ApplicationAdapter {
	
	//Batch for Sprites
	private SpriteBatch batch;
	
	//Shaders for passthrough, merging, and other effects
	private ShaderProgram program, def, vig;
	
	//Sprites for showing intro and being combined
	private Sprite background, foreground, logo;
	
	
	private OrthographicCamera cam;
	private int width = 640, height = 480;
	float[] rand;
	@Override
	public void create () {
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		//Sets up all sprites
		initSprites();
		
		program = new ShaderProgram(Gdx.files.internal("shaders/vig.vert"), Gdx.files.internal("shaders/colorswap.frag"));
		vig = new ShaderProgram(Gdx.files.internal("shaders/vig.vert"), Gdx.files.internal("shaders/vig.frag"));
		//def = batch.createDefaultShader();
		
		ShaderProgram.pedantic = false;
		if (vig.getLog().length()!=0)
	        System.out.println(vig.getLog());
		
		rand = new float[3];
		for(int i = 0; i < rand.length; i++){
			rand[i] = MathUtils.random(1.0f);
			System.out.println("rand["+i+"] = " + rand[i]);
		}
	}
	float elapsedTime = 0;
	float anim = .1f;
	boolean popped = false;
	int cnt = 0;
	@Override
	public void render () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		//clear
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!popped){
			anim += 0.01f;
		}
			elapsedTime += Gdx.graphics.getDeltaTime();
		
		if(elapsedTime > 4){
			//anim = 0;
			popped = true;
			if(cnt == rand.length)
				cnt = 0;
			if(elapsedTime > 4 + rand[cnt]){
				anim = 4;
				elapsedTime = 4.001f;
				cnt++;
				System.out.println(true);
			}else{
				anim = 0;
				System.out.println(elapsedTime + " <= " + rand[cnt] + " | cnt = " + cnt);
				}
		}
			
		batch.begin();
		batch.setShader(vig);
		vig.setUniformf(vig.getUniformLocation("u_resolution"), width, height);
		vig.setUniformf(vig.getUniformLocation("time"), elapsedTime / 10);
		batch.draw(foreground.getTexture(), 0, -50, width, height + 50);
		batch.setShader(program);
		program.setUniformf(program.getUniformLocation("anim"), anim);
		batch.draw(logo.getTexture(), width / 4, 0, width / 2, 480);
		batch.end();
	}
	
	private void initSprites(){
		
		background = new Sprite(new Texture(Gdx.files.internal("DarkBackground.png")));
		background.setBounds(0, -50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//background.getTexture().bind(0);
		//-10 becuase artist left blackspace at bottom of the image
		foreground = new Sprite(new Texture(Gdx.files.internal("Foreground.png")));
		foreground.setBounds(0, -50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + 10);
		//foreground.getTexture().bind(1);
		
		logo = new Sprite(new Texture(Gdx.files.internal("Pariah_logo.png")));
		logo.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//logo.getTexture().bind(2);
	}
}
