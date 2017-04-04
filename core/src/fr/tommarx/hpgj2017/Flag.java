package fr.tommarx.hpgj2017;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;

public class Flag extends AbstractGameObject{

    Planet p;

    public Flag(Planet planet) {
        super(new Transform(new Vector2(planet.getTransform().getPosition().x, planet.getTransform().getPosition().y + planet.radius + 0.25f)));
        setTag("Flag");
        setLayout(1);
        this.p = planet;
        addComponent(new SpriteRenderer(this, Gdx.files.internal("flag.png"), 0, 0, 0.25f, 0.5f));
        addComponent(new BoxBody(this, 0.25f, 0.5f, BodyDef.BodyType.KinematicBody, true));
    }

    protected void update(float delta) {

    }
}
