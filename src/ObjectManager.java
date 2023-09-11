import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;

public class ObjectManager {
	/*
	 * This class controls all the objects.
	 * It updates objects' location and detect collisions with the player.
	 * JLabel [] objects are used to show up on the screen,
	 * and arraylists are used to save the existing objects.
	 * 
	 * Since each objects are defined as different class,
	 * we needed different arraylist to store them separately
	 * unlike JLabel which are maximum 20 and use the same label to show up on the
	 * screen.
	 */

	JLabel[] objects = new JLabel[20];
	IngameVar playingInfo;

	ArrayList<LemonCandy> lemonList;
	ArrayList<Chocolate> chocoList;
	ArrayList<Rock> rockList;

	// Generate one instance for each object to get the value
	LemonCandy tmpLemon = new LemonCandy();
	Chocolate tmpChoco = new Chocolate();
	Rock tmpRock = new Rock();

	static Color emptyColor = new Color(255, 255, 255); // white
	static Color rockColor = new Color(58, 58, 58); // gray
	static Color chocoColor = new Color(164, 116, 73); // brown
	static Color lemonColor = new Color(242, 232, 92); // yellow

	ObjectManager(IngameVar playingInfo) {

		this.playingInfo = playingInfo;

		lemonList = new ArrayList<LemonCandy>();
		chocoList = new ArrayList<Chocolate>();
		rockList = new ArrayList<Rock>();

		for (int i = 0; i < 20; i++) {
			JLabel objectLabel = new JLabel();
			objects[i] = objectLabel;
			objects[i].setOpaque(true);
		}
		initializer();
	}

	public void initializer() {
		for (int i = 0; i < 20; i++) {
			objects[i].setBounds(-50, -50, 30, 30);
			objects[i].setBackground(emptyColor);
		}

		lemonList.clear();
		chocoList.clear();
		rockList.clear();

	}

	public LemonCandy addLemon() {
		// Generate new lemon candy to its array
		LemonCandy newLemon = new LemonCandy();
		lemonList.add(newLemon);
		// Bring up a free label to become a new lemon candy
		int position = findPosition();
		objects[position].setBounds(newLemon.xLoc, -tmpLemon.speed, newLemon.width, newLemon.height);
		objects[position].setBackground(lemonColor);
		return newLemon;
	}

	public Chocolate addChoco() {
		// Generate new chocolate to its array
		Chocolate newChoco = new Chocolate();
		chocoList.add(newChoco);
		// Bring up a free label to become a new chocolate
		int position = findPosition();
		objects[position].setBounds(newChoco.xLoc, -tmpChoco.speed, newChoco.width, newChoco.height);
		objects[position].setBackground(chocoColor);
		return newChoco;
	}

	public Rock addRock() {
		// Generate new rock to its array
		Rock newRock = new Rock();
		rockList.add(newRock);
		// Bring up a free label to become a new rock
		int position = findPosition();
		objects[position].setBounds(newRock.xLoc, -tmpRock.speed, newRock.width, newRock.height);
		objects[position].setBackground(rockColor);
		return newRock;
	}

	private int findPosition() {
		/*
		 * Find a free label and return its index
		 */
		int position;
		int i = 0;
		boolean found = false;
		while (i < 20 && !found) {
			if (objects[i].getBackground().equals(emptyColor)) {
				// The label is empty(never used yet)
				found = true;
			} else if (objects[i].getLocation().y > tmpRock.model.groundLoc) {
				// The label has arrived to the ground
				found = true;
			} else {
				// Move to the next to check if it is free
				i++;
			}
		}
		if (found) {
			position = i;
		} else {
			position = 0;
		}
		return position;
	}

	public IngameVar updateObj() {
		// Controls the location of the objects and detects the collision with the
		// player

		int i = 0;
		// update LemonCandies
		for (; i < lemonList.size(); i++) {

			boolean xlocLeftCheck = (lemonList.get(i).xLoc > playingInfo.xLoc - lemonList.get(i).model.objWidth);
			boolean xlocRightCheck = (lemonList.get(i).xLoc < playingInfo.xLoc + lemonList.get(i).model.playerWidth);
			boolean ylocCheck = (lemonList.get(i).yLoc + lemonList.get(i).model.objHeight >= playingInfo.yLoc);

			// collision detection
			if (xlocLeftCheck && xlocRightCheck && ylocCheck) {
				lemonList.get(i).effectLife(playingInfo);
				lemonList.get(i).effectScore(playingInfo);
				lemonList.remove(i);
			}
			// if there wasn't collision & touched the ground
			else if (lemonList.get(i).yLoc > lemonList.get(i).model.groundLoc) {
				lemonList.remove(i);
			}
			// Just continue dropping
			else
				lemonList.get(i).yLoc += lemonList.get(i).speed;
		}

		// update Chocolates, same process with lemon candy
		for (i = 0; i < chocoList.size(); i++) {

			boolean xlocLeftCheck = (chocoList.get(i).xLoc > playingInfo.xLoc - chocoList.get(i).model.objWidth);
			boolean xlocRightCheck = (chocoList.get(i).xLoc < playingInfo.xLoc + chocoList.get(i).model.playerWidth);
			boolean ylocCheck = (chocoList.get(i).yLoc + chocoList.get(i).model.objHeight >= playingInfo.yLoc);

			if (xlocLeftCheck && xlocRightCheck && ylocCheck) {
				chocoList.get(i).effectLife(playingInfo);
				chocoList.get(i).effectScore(playingInfo);
				chocoList.remove(i);
			} else if (chocoList.get(i).yLoc > chocoList.get(i).model.groundLoc) {
				chocoList.remove(i);
			} else
				chocoList.get(i).yLoc += chocoList.get(i).speed;
		}
		// update Rocks, same process with lemon candy
		for (i = 0; i < rockList.size(); i++) {

			boolean xlocLeftCheck = (rockList.get(i).xLoc > playingInfo.xLoc - rockList.get(i).model.objWidth);
			boolean xlocRightCheck = (rockList.get(i).xLoc < playingInfo.xLoc + rockList.get(i).model.playerWidth);
			boolean ylocCheck = (rockList.get(i).yLoc + rockList.get(i).model.objHeight >= playingInfo.yLoc);

			if (xlocLeftCheck && xlocRightCheck && ylocCheck) {
				rockList.get(i).effectLife(playingInfo);
				rockList.get(i).effectScore(playingInfo);
				rockList.remove(i);
			} else if (rockList.get(i).yLoc > rockList.get(i).model.groundLoc) {
				rockList.remove(i);
			} else
				rockList.get(i).yLoc += rockList.get(i).speed;
		}

		// update Labels, check the corresponding speed and apply them to drop
		for (i = 0; i < objects.length; i++) {
			int speed = 0;
			if (objects[i].getBackground().equals(rockColor)) {
				speed = tmpRock.speed;
			} else if (objects[i].getBackground().equals(chocoColor)) {
				speed = tmpChoco.speed;
			} else if (objects[i].getBackground().equals(lemonColor)) {
				speed = tmpLemon.speed;
			}
			objects[i].setBounds(objects[i].getLocation().x, objects[i].getLocation().y + speed, objects[i].getWidth(),
					objects[i].getHeight());
		}
		return playingInfo;
	}

}
