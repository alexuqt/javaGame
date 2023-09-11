import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CandyDrop {

	public static void main(String[] args) {

		// Create instances to run the game
		Model model = new Model();
		JLabel player = new JLabel();
		JLabel startInfo1 = new JLabel("Candy Drop", SwingConstants.CENTER);
		JLabel startInfo2 = new JLabel("", SwingConstants.CENTER);
		JLabel deadInfo = new JLabel("", SwingConstants.CENTER);
		int widthInfoLabel = (int) (model.frameWidth * 0.7);
		int heightInfoLabel = 40;
		Color infoLabelColor = new Color(242, 175, 92); // 84C1D9
		Color playerColor = new Color(242, 129, 119);

		IngameVar playingInfo = new IngameVar();
		playingInfo.xLoc = (model.frameWidth + model.playerWidth) / 2;
		playingInfo.yLoc = model.frameHeight - (int) (1.5 * model.playerHeight);

		ObjectManager manager = new ObjectManager(playingInfo);
		MyFrame frm = new MyFrame(playingInfo, player);

		// Generate Player Label
		player.setBounds(playingInfo.xLoc, playingInfo.yLoc, model.playerWidth, model.playerHeight); // x,y,w,h
		player.setBackground(playerColor);
		player.setOpaque(true);
		frm.add(player);

		// Generate Information Labels
		startInfo1.setBounds((int) (model.frameWidth - widthInfoLabel) / 2,
				(int) (model.frameHeight - heightInfoLabel) / 2, widthInfoLabel, heightInfoLabel); // x,y,w,h
		startInfo1.setBackground(infoLabelColor);
		startInfo1.setOpaque(true);
		frm.add(startInfo1);

		startInfo2.setBounds((int) (model.frameWidth - widthInfoLabel) / 2,
				(int) (model.frameHeight - heightInfoLabel) / 2 + 40, widthInfoLabel, heightInfoLabel); // x,y,w,h
		startInfo2.setBackground(infoLabelColor);
		startInfo2.setText(
				"Press s to start or q to quit | Move left and right to get Chocolates for scores and Lemon Candies for extra life!");
		startInfo2.setOpaque(true);
		frm.add(startInfo2);

		deadInfo.setBounds(-100, -100, widthInfoLabel, heightInfoLabel); // x,y,w,h
		deadInfo.setBackground(infoLabelColor);
		deadInfo.setText("GameOver -------- Press r to restart or q to quit");
		deadInfo.setOpaque(true);
		frm.add(deadInfo);

		// Add objects labels
		for (int i = 0; i < 20; i++) {
			frm.add(manager.objects[i]);
		}

		// Add a scoreboard label
		JLabel info = new JLabel();
		int infoWidth = 150;
		info.setBounds(model.frameWidth - infoWidth, 0, infoWidth, 30); // x,y,w,h
		info.setBackground(frm.skyblueColor);
		info.setOpaque(true);
		frm.add(info);

		frm.setLayout(null);

		/*
		 * The phase distinguishes the moments of the game.
		 * phase -1 : quit
		 * phase 0 : ready to start
		 * phase 1 : the game in progress
		 * phase 2 : game over, ready to restart
		 */
		while (playingInfo.phase != -1) {
			while (playingInfo.phase != -1 && playingInfo.life > 0) {

				// Objects are dropped every 500ms.
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				playingInfo = frm.getPlayer();
				if (playingInfo.phase == 1) {
					// If the game starts, the information labels are removed.
					if (frm.startInfoFlag == 1) {
						startInfo1.setBounds(-100, -200, 0, 0);
						startInfo2.setBounds(-100, -200, 0, 0);
						frm.startInfoFlag = -1;
					}

					// Generate a new object
					if (playingInfo.objNum < 20) {
						int objType = model.pickNewObj();
						if (objType == 1) {
							LemonCandy tmpLemon = manager.addLemon();
						} else if (objType == 2) {
							Chocolate tmpChoco = manager.addChoco();
						} else {
							Rock tmpRock = manager.addRock();
						}
					}
					playingInfo = manager.updateObj();
				}

				// update scoreboard
				info.setText("      Life: " + playingInfo.life + " | Score: " + playingInfo.score);
			}

			// detecting if the player pressed q to quit
			playingInfo = frm.getPlayer();
			if (playingInfo.phase == -1)
				break;

			// in case of the game is over
			playingInfo.phase = 2;
			deadInfo.setBounds((int) (model.frameWidth - widthInfoLabel / 2) / 2,
					(int) (model.frameHeight - heightInfoLabel) / 2, widthInfoLabel / 2, heightInfoLabel);

			// in case of the player decided to restart the game, reinitialize all values
			if (frm.initFlag == 1) {
				playingInfo.initializer();
				manager.initializer();
				frm.initFlag = 0;
				player.setBounds(playingInfo.xLoc, playingInfo.yLoc, model.playerWidth, model.playerHeight);
				deadInfo.setBounds(-100, -100, widthInfoLabel, heightInfoLabel);
			}
		}
		System.exit(0);
	}
}
