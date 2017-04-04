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

    public GameScreen(Game game, String level) {
        super(game);
        this.level = level;
    }

    Player player;
    String level;
    boolean isUnzoomed = false, zooming = false;

    public void show() {
        world.setGravity(new Vector2(0, 0));
        //Game.debugging = true;
        fadeIn(1);
        player = new Player(new Transform(Game.center));
        Game.debug(3, Game.center);
        add(player);
        loadLevel();
        /*add(new Planet(new Transform(new Vector2(2, 5)), 1));
        add(new Planet(new Transform(new Vector2(6, 2)), 0.7f));
        add(new Planet(new Transform(new Vector2(3, 1)), 1.1f));
        add(new Planet(new Transform(new Vector2(6, 6)), 0.5f));
        add(new Planet(new Transform(new Vector2(10, 2)), 0.8f));
        add(new Planet(new Transform(new Vector2(10, 8)), 2.1f));
        add(new Planet(new Transform(new Vector2(-1, 10)), 2.3f));
        add(new Planet(new Transform(new Vector2(-5, -2)), 1.3f));
        Planet p = new Planet(new Transform(new Vector2(-2, 0)), 1.3f);
        add(p);
        add(new Flag(p));*/

        GameObject bg = new GameObject(new Transform(Game.center));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), -15, -10));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), -15, 10));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), 5, 10));
        bg.addComponent(new SpriteRenderer(bg, Gdx.files.internal("background.png"), 5, -10));
        bg.setLayout(-1);
        bg.setScrollingSpeed(0.8f);
        add(bg);

        GameObject text = new GameObject(new Transform(Game.center));
        //text.addComponent(new SpriteRenderer(text, Gdx.files.internal("pressX.png")));
        text.addComponent(new Text(text, Gdx.files.internal("font.ttf"), 30, "Hold X to dezoom", Color.WHITE));
        text.setLayout(0);
        text.setScrollingSpeed(0.1f);
        add(text);

    }

    public void loadLevel() {
        String content = Gdx.files.internal(level).readString();
        int c = 0;
        for (String line : content.split("\n")) {
            String[] props = line.split(",");
            Vector2 pos = new Vector2(Float.parseFloat(props[0]), Float.parseFloat(props[0]));
            float radius = Float.parseFloat(props[2]);
            Planet p = new Planet(new Transform(pos), radius);
            add(p);
            c++;
            if (props[3].equals("true")) {
                add(new Flag(p));
            }
        }
        System.out.println(c);
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
