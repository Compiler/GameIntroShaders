package com.intro.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/*
*@author Luke Roche
*/

public class ColorCore extends ApplicationAdapter {
	
	//Batch for Sprites
	private SpriteBatch batch;
	
	//Shaders for passthrough, merging, and other effects
	private ShaderProgram program, def, vig;
	
	//Sprites for showing intro and being combined
	private Sprite background, foreground, logo;
	
	
	private OrthographicCamera cam;
	private int width = 640, height = 480;
	
	Sound droplet;
	Music ten;
	
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
		
		
		droplet = Gdx.audio.newSound(Gdx.files.internal("water drop.mp3"));
		long d = droplet.play();
		droplet.setVolume(d, .5f);
		droplet.loop();
		
		ten= Gdx.audio.newMusic(Gdx.files.internal("Tension.mp3"));
		ten.setPosition(3);
		ten.play();
		
		ShaderProgram.pedantic = false;
		if (vig.getLog().length()!=0)
	        System.out.println(vig.getLog());
		
	}
	float elapsedTime = 0;
	boolean pop = false;
	@Override
	public void render () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		elapsedTime += Gdx.graphics.getDeltaTime() / 5;
		if(elapsedTime == .2f){
			droplet.play();
			
		}
		if(elapsedTime > 1 && !pop){
			pop = true;
			elapsedTime = 0;
		}
		//clear
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		batch.setShader(vig);
		
		vig.setUniformf(vig.getUniformLocation("u_resolution"), width, height);
		vig.setUniformf(vig.getUniformLocation("time"), !pop ? 0 : elapsedTime / 10);
		
		
		//batch.draw(foreground.getTexture(), 0, -50, width, height + 50);
		
		foreground.draw(batch);
		batch.setShader(program);
		program.setUniformf(program.getUniformLocation("time"), !pop ? 0 : elapsedTime);
		logo.draw(batch);
		
		//batch.draw(logo.getTexture(), width / 4, 0, width / 2, 480);
		
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
		//int paddingX = width / 6;
		//logo.setBounds(paddingX / 2, height / 2.5f, width - paddingX, height / 1.5f);
		logo.setBounds(width / 4, height / 4, width / 2, height / 2);
		//logo.getTexture().bind(2);
	}
}
