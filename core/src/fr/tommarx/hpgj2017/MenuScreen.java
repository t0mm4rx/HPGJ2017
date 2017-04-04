package fr.tommarx.hpgj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.Text;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Easing.Tween;
import fr.tommarx.gameengine.Easing.TweenListener;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;
import fr.tommarx.gameengine.Game.Screen;
import fr.tommarx.gameengine.Util.Keys;

public class MenuScreen extends Screen{
    public MenuScreen(Game game) {
        super(game);
    }

    GameObject play, map, quit;
    int selector = 0;

    public void show() {
        world.setGravity(new Vector2());
        add(new Player(new Transform(new Vector2(8, Game.center.y + 2))));
        add(new Planet(new Transform(new Vector2(8, Game.center.y)), 1.5f));
        play = new GameObject(new Transform(new Vector2(1, 4.1f), new Vector2(1.3f, 1.3f), 0));
        play.addComponent(new Text(play, Gdx.files.internal("font.ttf"), 40, "Play !", Color.WHITE));
        add(play);
        map = new GameObject(new Transform(new Vector2(1, 3.1f)));
        map.addComponent(new Text(map, Gdx.files.internal("font.ttf"), 40, "Open custom map", Color.WHITE));
        add(map);
        quit = new GameObject(new Transform(new Vector2(1, 2.1f)));
        quit.addComponent(new Text(quit, Gdx.files.internal("font.ttf"), 40, "Quit", Color.WHITE));
        add(quit);

        Text a, b;
        GameObject keys = new GameObject(new Transform(new Vector2(6.3f, Game.center.y - 2)));
        a = new Text(keys, Gdx.files.internal("font.ttf"), 20, "Left and right to move around the planet", Color.WHITE);
        keys.addComponent(a);
        keys.setLayout(-1);
        add(keys);

        GameObject space = new GameObject(new Transform(new Vector2(7.5f, Game.center.y - 2.4f)));
        b = new Text(space, Gdx.files.internal("font.ttf"), 20, "Space to jump", Color.WHITE);
        space.addComponent(b);
        space.setLayout(-1);
        add(space);

        new Tween(Tween.LINEAR_EASE_NONE, 2, 1, false, new TweenListener() {
            public void onValueChanged(float v) {
                a.setColor(new Color(1, 1, 1, v));
                b.setColor(new Color(1, 1, 1, v));
            }
            public void onFinished() {}
        });
    }

    public void update() {
        if (Keys.isKeyJustPressed(Input.Keys.UP)) {
            if (selector > 0) {
                selector--;
                animateMenu(selector + 1);
            } else {
                selector = 2;
                animateMenu(0);
            }
        }
        if (Keys.isKeyJustPressed(Input.Keys.DOWN)) {
            if (selector < 2) {
                selector++;
                animateMenu(selector - 1);
            } else {
                selector = 0;
                animateMenu(2);
            }
        }
        if (Keys.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selector) {
                case 0:
                    fadeOut(1);
                    Game.waitAndDo(1, () -> {
                        game.setScreen(new GameScreen(game, "levels/level" + GameClass.lastLevel + ".map", false));
                        return null;
                    });
                    break;
                case 1:
                    fadeOut(1);
                    Game.waitAndDo(1, () -> {
                        game.setScreen(new ChooseMap(game));
                        return null;
                    });
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    public void animateMenu(int old) {
        GameObject a = null, b = null;
        switch (selector) {
            case 0:
                a = play;
                break;
            case 1:
                a = map;
                break;
            case 2:
                a = quit;
                break;
        }
        switch (old) {
            case 0:
                b = play;
                break;
            case 1:
                b = map;
                break;
            case 2:
                b = quit;
                break;
        }
        GameObject finalA = a;
        GameObject finalB = b;
        new Tween(Tween.CUBE_EASE_INOUT, .1f, 0, false, new TweenListener() {
            public void onValueChanged(float v) {
                finalA.getTransform().setScale(new Vector2(v * .3f + 1, v * .3f + 1));
                finalB.getTransform().setScale(new Vector2(.3f - (v * .3f) + 1, .3f - (v * .3f) + 1));
            }
            public void onFinished() {}
        });
    }

}
