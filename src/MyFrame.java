import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyFrame extends JFrame implements KeyListener {

	/*
	 * This class controls the JFrame during the whole game.
	 * We implements KeyListener since there will be keyboard inputs from the user
	 * to move(left arrow/right arrow), start game(s), restart game(r) and quit(q).
	 */

	JLabel player;
	IngameVar playingInfo;
	int initFlag = 0;
	int startInfoFlag = 0;

	static int frameWidth = 1200;
	static int frameHeight = 600;
	static int playerWidth = 40;
	static int playerHeight = 60;
	static int groundHeight = 30;
	static int pixels = 30;

	static Color skyblueColor = new Color(156, 227, 255); // 84C1D9
	static Color groundColor = new Color(140, 128, 112); // 8C8070

	MyFrame(IngameVar playingInfo, JLabel player) {

		this.playingInfo = playingInfo;
		this.player = player;

		// Generate Frame
		this.setSize(frameWidth, frameHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Candy Drop");
		this.setLayout(null);
		this.addKeyListener(this);

		// Drawing the ground
		JLabel ground = new JLabel();
		int groundLoc = frameHeight - 2 * groundHeight + 5;
		ground.setBounds(0, groundLoc, frameWidth, groundHeight);
		ground.setBackground(groundColor);
		ground.setOpaque(true);
		this.add(ground);

		this.setVisible(true);
	}

	public IngameVar getPlayer() {
		// Returns the values to update the Ingame variables
		return playingInfo;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Distinguish user's input
		switch (e.getKeyCode()) {

			case 39: // press right arrow to move right
				playingInfo = moveRight();
				break;
			case 37: // press left arrow to move left
				playingInfo = moveLeft();
				break;

			case 83: // press s to start the game
				playingInfo.phase = 1;
				if (startInfoFlag == 0)
					startInfoFlag = 1;
				break;

			case 82: // press r to restart after death
				if (playingInfo.phase == 2) {
					initFlag = 1;
				}
				break;

			case 81: // press q to quit
				playingInfo.phase = -1;
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public IngameVar moveRight() {
		if (player.getLocation().x + player.getWidth() < frameWidth) {
			player.setLocation(player.getLocation().x + pixels, player.getLocation().y);
			playingInfo.xLoc += pixels;
		}
		return playingInfo;
	}

	public IngameVar moveLeft() {
		if (player.getLocation().x > 0) {
			player.setLocation(player.getLocation().x - pixels, player.getLocation().y);
			playingInfo.xLoc -= pixels;
		}
		return playingInfo;
	}
}
