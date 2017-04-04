package fr.tommarx.hpgj2017;

import fr.tommarx.gameengine.Game.Game;

public class GameClass extends Game {

	public static int lastLevel = 1;

	public void init() {
		setScreen(new GameScreen(this, "levels/level3.map"));
	}

}
