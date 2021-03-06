package fr.tommarx.hpgj2017;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;

public class Planet extends AbstractGameObject{

    Body body;
    Player player;
    float radius;

    public Planet(Transform transform, float radius) {
        super(transform);
        setTag("Planet");
        body = new CircleBody(this, radius, BodyDef.BodyType.KinematicBody, false);
        addComponent(body);
        addComponent(new SpriteRenderer(this, Gdx.files.internal("planet1.png"), 0, 0, radius * 2, radius * 2));
        setLayout(2);
        player = ((Player) Game.getCurrentScreen().getGameObjectByTag("Player"));
        this.radius = radius;
    }

    protected void update(float delta) {
        if (player != null) {
            float dst = body.getBody().getPosition().dst(player.body.getBody().getPosition());
            if (dst < 5) {
                player.body.getBody().applyForceToCenter(body.getBody().getPosition().sub(player.body.getBody().getPosition()).nor().scl((5 - dst) / 10 * radius), false);
            }
        }
        for (AbstractGameObject enemy : Game.getCurrentScreen().getGameObjectsByTag("Enemy")) {
            Enemy e = (Enemy) enemy;
            float dst = body.getBody().getPosition().dst(e.body.getBody().getPosition());
            if (dst < 5) {
                e.body.getBody().applyForceToCenter(body.getBody().getPosition().sub(e.body.getBody().getPosition()).nor().scl((5 - dst) / 10 * radius), false);
            }
        }
    }
}
