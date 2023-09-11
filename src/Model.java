import java.util.Random;
import java.awt.Color;

class Model {

	// This class contains the static values used in the game
	static int frameWidth = 1200;
	static int frameHeight = 600;
	static int playerWidth = 40;
	static int playerHeight = 60;
	static int groundHeight = 30;
	static int pixels = 30;
	static int maxLife = 3;

	static int objWidth = 30;
	static int objHeight = 30;

	int groundLoc;

	Model() {
		groundLoc = frameHeight - 2 * groundHeight + 5;
	}

	public int calcRandxLoc() {
		Random random = new Random();
		return random.nextInt(frameWidth - objWidth);
	}

	public int pickNewObj() {
		// Randomly pick a type of object that will appear on the screen
		Random random = new Random();
		int obj = random.nextInt(20);

		if (obj == 1)
			return 1; // lemonCandy, 5%
		else if (obj > 1 && obj < 5)
			return 2; // chocolate, 15%
		else
			return 3; // rock, 80%
	}

}

class IngameVar {
	/*
	 * This class contains the value used during the game.
	 * Values are initialized for every games.
	 */
	int life = 3;
	int score = 0;
	int objNum = 0;
	int phase = 0;

	int xLoc = (Model.frameWidth + Model.playerWidth) / 2;
	int yLoc = Model.frameHeight - (int) (1.5 * Model.playerHeight);

	public void phaseChanger(int value) {
		phase = value;
	}

	public void initializer() {
		life = 3;
		score = 0;
		objNum = 0;
		phase = 1;
		xLoc = (Model.frameWidth + Model.playerWidth) / 2;
	}
}

interface Object {
	/*
	 * This interface is the basic structure for rocks, chocolates, and
	 * lemoncandies.
	 * Variables here are all static which are only used for determining positions.
	 * 
	 * Each object has effect method for life and score depends on their types.
	 */
	int dropPixel = 10;
	int width = 30;
	int height = 30;
	Model model = new Model();

	public void effectLife(IngameVar playingInfo);

	public void effectScore(IngameVar playingInfo);

}

class Rock implements Object {

	int xLoc;
	int yLoc = 0;
	int speed = dropPixel * 3;
	Color rockColor = Color.gray;

	Rock() {
		// set random x location
		xLoc = model.calcRandxLoc();
	}

	@Override
	public void effectLife(IngameVar playingInfo) {
		// Rock removes one life of the player in case of collision
		playingInfo.life -= 1;
	}

	@Override
	public void effectScore(IngameVar playingInfo) {
		return;
	}

}

abstract class Candy implements Object {
	/*
	 * Since there are 2 types of candies; chocolates and lemon candies,
	 * we termined to define candy class as an abstract class.
	 * There are no candy instances, but chocolates and lemon candies.
	 */
	int xLoc;
	int yLoc = 0;
	int speed = dropPixel * 5; // candies are faster than rocks
	Color candyColor;

	Candy() {
		xLoc = model.calcRandxLoc();
	}

}

class LemonCandy extends Candy {

	LemonCandy() {
		candyColor = new Color(242, 232, 92);
		speed = dropPixel * 8; // lemons are the fastest items
	}

	@Override
	public void effectLife(IngameVar playingInfo) {
		// Lemon candy gives extra life in case of collision within the maximum 3.
		if (playingInfo.life < model.maxLife)
			playingInfo.life += 1;
		return;
	}

	@Override
	public void effectScore(IngameVar playingInfo) {
		return;
	}
}

class Chocolate extends Candy {

	Chocolate() {
		candyColor = new Color(84, 51, 45);
	}

	@Override
	public void effectLife(IngameVar playingInfo) {
		return;
	}

	@Override
	public void effectScore(IngameVar playingInfo) {
		// Chocolates give 10 scores in case of collision.
		playingInfo.score += 10;
	}
}