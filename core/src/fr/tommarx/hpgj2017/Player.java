package fr.tommarx.hpgj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.tommarx.gameengine.Collisions.CollisionsListener;
import fr.tommarx.gameengine.Collisions.CollisionsManager;
import fr.tommarx.gameengine.Components.Body;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.AbstractGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Util.Keys;

public class Player extends AbstractGameObject {

    Body body;
    boolean canJump = false;
    public final static float JUMP = 10f, SPEED = 0.5f;

    public Player(Transform transform) {
        super(transform);
        setTag("Player");
        body = new CircleBody(this, 0.1f, BodyDef.BodyType.DynamicBody, false);
        addComponent(body);
        addComponent(new SpriteRenderer(this, Gdx.files.internal("player.png"), 0, 0, 0.2f, 0.2f));
        body.getBody().setLinearVelocity(new Vector2(-0.3f, 0.9f).scl(10));
        new CollisionsManager(new CollisionsListener() {
            public void collisionEnter(AbstractGameObject a, AbstractGameObject b, Contact contact) {
                canJump = true;
                if ((a.getTag().equals("Player") && b.getTag().equals("Flag")) || (b.getTag().equals("Player") && a.getTag().equals("Flag"))) {
                    Game.getCurrentScreen().game.setScreen(new GameScreen(Game.getCurrentScreen().game, "levels/level1.map"));
                }
            }

            public void collisionEnd(AbstractGameObject a, AbstractGameObject b, Contact contact) {
                canJump = false;
            }
        });
    }

    protected void update(float delta) {
        body.getBody().setAwake(true);
        if (Keys.isKeyJustPressed(Input.Keys.SPACE) && canJump) {
            body.getBody().applyForceToCenter(new Vector2(body.getBody().getPosition().sub(getClosestPlanet().body.getBody().getPosition())).nor().scl(JUMP), false);
        }
        if (Keys.isKeyPressed(Input.Keys.LEFT)) {
            Vector2 a = new Vector2(body.getBody().getPosition().sub(getClosestPlanet().body.getBody().getPosition())).rotate(90).nor().scl(SPEED);
            body.getBody().applyForceToCenter(a, false);
        }
        if (Keys.isKeyPressed(Input.Keys.RIGHT)) {
            Vector2 a = new Vector2(body.getBody().getPosition().sub(getClosestPlanet().body.getBody().getPosition())).rotate(-90).nor().scl(SPEED);
            body.getBody().applyForceToCenter(a, false);
        }
    }

    private Planet getClosestPlanet() {
        Planet p = null;
        float lastDst = 100;
        for (AbstractGameObject planet : Game.getCurrentScreen().getGameObjectsByTag("Planet")) {
            if (body.getBody().getPosition().dst(planet.getTransform().getPosition()) < lastDst) {
                lastDst = body.getBody().getPosition().dst(planet.getTransform().getPosition());
                p = (Planet) planet;
            }
        }
        return p;
    }

}
