package fr.tommarx.hpgj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Text;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Easing.Tween;
import fr.tommarx.gameengine.Easing.TweenListener;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;
import fr.tommarx.gameengine.Game.Screen;
import fr.tommarx.gameengine.Util.Keys;

public class GameScreen extends Screen {

    public GameScreen(Game game, String level, boolean customLevel) {
        super(game);
        this.level = level;
        this.customLevel = customLevel;
    }

    Player player;
    String level;
    boolean isUnzoomed = false, zooming = false, customLevel;

    public void show() {
        world.setGravity(new Vector2(0, 0));
        //Game.debugging = true;
        fadeIn(1);
        player = new Player(new Transform(Game.center));
        Game.debug(3, Game.center);
        add(player);
        loadLevel();

        GameObject bg = new GameObject(new Transform(Game.center));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), -15, -10));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), -15, 10));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), 5, 10));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), 5, -10));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), 20, 10));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), 20, -10));
        bg.setLayout(-1);
        bg.setScrollingSpeed(0.8f);
        add(bg);

        GameObject text = new GameObject(new Transform(Game.center));
        text.addComponent(new Text(text, Gdx.files.internal("font.ttf"), 30, "Hold X to dezoom", Color.WHITE));
        text.setLayout(0);
        text.setScrollingSpeed(0.1f);
        add(text);

        GameObject text2 = new GameObject(new Transform(new Vector2(Game.center.x, Game.center.y + 0.5f)));
        text2.addComponent(new Text(text2, Gdx.files.internal("font.ttf"), 30, "Press R to restart", Color.WHITE));
        text2.setLayout(0);
        text2.setScrollingSpeed(0.1f);
        add(text2);

    }

    public void loadLevel() {
        String content = Gdx.files.internal(level).readString();
        for (String line : content.split("\n")) {
            String[] props = line.split(",");
            Vector2 pos = new Vector2(Float.parseFloat(props[0]), Float.parseFloat(props[1]));
            float radius = Float.parseFloat(props[2]);
            Planet p = new Planet(new Transform(pos), radius);
            add(p);
            if (props[3].equals("true")) {
                add(new Flag(p));
            }
            if (props[4].equals("true")) {
                add(new Enemy(p));
            }
        }
    }

    public void update() {
        handleCamera();

        if (Keys.isKeyJustPressed(Input.Keys.X) && !isUnzoomed && !zooming) {
            zooming = true;
            new Tween(Tween.CUBE_EASE_INOUT, 1, 0, false, new TweenListener() {
                public void onValueChanged(float v) {
                    Game.getCurrentScreen().camera.zoom = v * 2 + 1;
                }

                public void onFinished() {
                    isUnzoomed = true;
                    zooming = false;
                }
            });
        }

        if (Keys.isKeyJustPressed(Input.Keys.R)) {
            if (customLevel) {
                game.setScreen(new GameScreen(game, level, true));
            } else {
                game.setScreen(new GameScreen(game, "levels/level" + GameClass.lastLevel + ".map", false));
            }
        }
        if (Keys.isKeyJustPressed(Input.Keys.ESCAPE)) {
            setScreen(new MenuScreen(game));
        }

        if (isUnzoomed && !Keys.isKeyPressed(Input.Keys.X) && !zooming) {
            isUnzoomed = false;
            zooming = true;
            new Tween(Tween.CUBE_EASE_INOUT, 1, 0, false, new TweenListener() {
                public void onValueChanged(float v) {
                    Game.getCurrentScreen().camera.zoom = (1 - v) * 2 + 1;
                }

                public void onFinished() {
                    zooming = false;
                }
            });
        }

        if (Keys.isKeyJustPressed(Input.Keys.D)) {
           Game.debugging = !Game.debugging;
        }

        Game.debug(2, "FPS : " + Gdx.graphics.getFramesPerSecond());
    }

    private void handleCamera() {
        Vector2 posA = new Vector2(Game.getCurrentScreen().camera.position.cpy().x, Game.getCurrentScreen().camera.position.cpy().y);
        Vector2 posB = new Vector2(player.body.getBody().getPosition());
        Vector2 vec = posB.sub(posA);
        Game.getCurrentScreen().camera.position.add(new Vector3(vec.scl(0.1f).x, vec.scl(0.1f).y, 0));
    }
}
