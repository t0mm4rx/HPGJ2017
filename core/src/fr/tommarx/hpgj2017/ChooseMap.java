package fr.tommarx.hpgj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import fr.tommarx.gameengine.Components.Text;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.Screen;
import fr.tommarx.gameengine.Util.Keys;

public class ChooseMap extends Screen {
    public ChooseMap(Game game) {
        super(game);
    }

    Table table;
    Stage stage;
    TextField textArea;
    Button button;

    public void show() {

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        textArea = new TextField("", skin);
        textArea.setMessageText("Path to the .map file");
        textArea.setSize(400, 30);
        textArea.setPosition(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 15);
        button = new TextButton("Load", skin);
        table.center();
        button.setPosition(Gdx.graphics.getWidth() / 2 + 220, Gdx.graphics.getHeight() / 2 - 15);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                setScreen(new GameScreen(game, textArea.getText(), true));
            }
        });
        table.addActor(textArea);
        table.addActor(button);
        Gdx.input.setInputProcessor(stage);
    }

    public void update() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (Keys.isKeyJustPressed(Input.Keys.ESCAPE)) {
            setScreen(new MenuScreen(game));
        }
    }

}
