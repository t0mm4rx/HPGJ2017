package fr.tommarx.hpgj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;

public class Enemy extends AbstractGameObject {

    Body body;
    Planet p;
    private float speed = 0.2f;

    public Enemy(Planet p) {
        super(new Transform(new Vector2(p.getTransform().getPosition().x, p.getTransform().getPosition().y - p.radius - 0.05f)));
        setTag("Enemy");
        this.p = p;
        body = new CircleBody(this, 0.1f, BodyDef.BodyType.DynamicBody, false);
        addComponent(body);
        setLayout(3);
        addComponent(new SpriteRenderer(this, Gdx.files.internal("enemy.png"), 0, 0, 0.2f, 0.2f));
    }

    protected void update(float delta) {
        Vector2 a = new Vector2(body.getBody().getPosition().sub(p.body.getBody().getPosition())).rotate(90).nor().scl(speed);
        body.getBody().applyForceToCenter(a, false);
        if (body.getBody().getLinearVelocity().len() < 0.1f) {
            speed += 0.02f;
        } else {
            if (speed > 0.2f) {
                speed -= 0.001f;
            }
        }
    }
}
