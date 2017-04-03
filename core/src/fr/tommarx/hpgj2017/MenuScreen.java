package fr.tommarx.hpgj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.Text;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;
import fr.tommarx.gameengine.Game.Screen;
import fr.tommarx.gameengine.Util.Keys;

public class MenuScreen extends Screen{
    public MenuScreen(Game game) {
        super(game);
    }

    GameObject play;

    public void show() {
        world.setGravity(new Vector2());
        add(new Player(new Transform(new Vector2(8, Game.center.y + 2))));
        add(new Planet(new Transform(new Vector2(8, Game.center.y)), 1.5f));
        play = new GameObject(new Transform(new Vector2(1, 4)));
        play.addComponent(new Text(play, Gdx.files.internal("font.ttf"), 40, "Play !", Color.WHITE));
        add(play);
    }

    public void update() {}

}
