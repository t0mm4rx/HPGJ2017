package fr.tommarx.hpgj2017;


import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;

public class Planet extends AbstractGameObject{

    Body body;
    Player player;
    float radius;

    public Planet(Transform transform, float radius) {
        super(transform);
        setTag("Planet");
        body = new CircleBody(this, radius, BodyDef.BodyType.KinematicBody, false);
        addComponent(body);
        player = ((Player) Game.getCurrentScreen().getGameObjectByTag("Player"));
        this.radius = radius;

    }

    protected void update(float delta) {
        float dst = body.getBody().getPosition().dst(player.body.getBody().getPosition());
        if (dst < 5) {
            player.body.getBody().applyForceToCenter(body.getBody().getPosition().sub(player.body.getBody().getPosition()).nor().scl((5 - dst) / 10 * radius), false);
        }
    }
}
