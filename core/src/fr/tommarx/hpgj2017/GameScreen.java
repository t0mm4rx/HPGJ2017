package fr.tommarx.hpgj2017;

import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.Screen;

public class GameScreen extends Screen {

    public GameScreen(Game game) {
        super(game);
    }

    Player player;

    public void show() {
        world.setGravity(new Vector2(0, 0));
        Game.debugging = true;
        camera.zoom = 3f;
        player = new Player(new Transform(Game.center));
        add(player);
        add(new Planet(new Transform(new Vector2(2, 5)), 1));
        add(new Planet(new Transform(new Vector2(6, 2)), 0.7f));
        add(new Planet(new Transform(new Vector2(3, 1)), 1.1f));
        add(new Planet(new Transform(new Vector2(6, 6)), 0.5f));
        add(new Planet(new Transform(new Vector2(10, 2)), 0.8f));
        add(new Planet(new Transform(new Vector2(10, 8)), 2.1f));
        add(new Planet(new Transform(new Vector2(-1, 10)), 2.3f));
        add(new Planet(new Transform(new Vector2(-5, -2)), 1.3f));
        add(new Planet(new Transform(new Vector2(-3, 0)), 1.3f));
    }

    public void update() {



    }
}
