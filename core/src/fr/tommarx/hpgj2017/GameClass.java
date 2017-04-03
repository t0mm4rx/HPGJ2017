package fr.tommarx.hpgj2017;

import fr.tommarx.gameengine.Game.Game;

public class GameClass extends Game {

	public void init() {
		setScreen(new MenuScreen(this));
	}

}
