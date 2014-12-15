package orig2011.v4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A view Component suitable for inclusion in an AWT Frame. Paints itself by
 * consulting its model.
 */
public class GameView extends JComponent {
	private static String[][] keyMap = {{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "light", "lock", null, null, "mute"},
			{null, "esc", null, "f1", "f2", "f3", "f4", null, "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "prtscn", "scroll", "pause", "stop", "prev", "play", "next"},
			{null, "grave", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "minus", "equal", "bspace", "bspace", "ins", "home", "pgup", "numlock", "numslash", "numstar", "numminus"},
			{null, "tab", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "lbrace", "rbrace", "enter", "enter", "del", "end", "pgdn", "num7", "num8", "num9", "numplus"},
			{null, "caps", "a", "s", "d", "f", "g", "h", "j", "k", "l", "colon", "quote", null, "enter", "enter", null, null, null, "num4", "num5", "num6", "numplus"},
			{"lshift", null, "z", "x", "c", "v", "b", "n", "m", "comma", "dot", "slash", "rshift", "rshift", "rshift", null, null,   "up",   null, "num1", "num2", "num3", "numenter"},
			{"lctrl", "lwin", "lalt", "space", "space", "space", "space", "space", "space", "space", "ralt", "rwin", "rmenu", "rctrl", "rctrl", null, "left", "down", "right", "num0", "num0", "numdot", "numenter"}};

	/** Size of game model */
	private final Dimension modelSize;

	/** Size of every tile in the model */
	private final Dimension tileSize;

	/** The game model which is drawn */
	private GameModel model;

	/** The offscreen buffer */
	private Graphics offscreenGraphics;

	/** Image representing the offscreen graphics */
	private Image offscreenImage;

	/**
	 * Creates a view where each GameObject has side length 40 pixels..
	 */
	public GameView() {
		this(40);
	}

	/**
	 * Creates a view where each GameObject has a given size.
	 *
	 * @param tileSide
	 *            side length in pixels of each GameObject.
	 */
	public GameView(final int tileSide) {
		this.tileSize = new Dimension(tileSide, tileSide);
		this.modelSize = Constants.getGameSize();
		Dimension preferredSize =
				new Dimension(this.modelSize.width * tileSide,
						this.modelSize.height * tileSide);
		setPreferredSize(preferredSize);
	}

	/**
	 * Updates the view with a new model.
	 */
	public void setModel(final GameModel model) {
		this.model = model;
		repaint();
	}

	/**
	 * This method ensures that the painting is performed double-buffered. This
	 * means there won't be any flicker when repainting all the time.
	 */
	@Override
	public void update(final Graphics g) {
		// Create an offscreen buffer (if we don't have one)
		if (this.offscreenImage == null) {
			Dimension size = getSize();

			this.offscreenImage = createImage(size.width, size.height);
			this.offscreenGraphics = this.offscreenImage.getGraphics();
		}

		// This will invoke painting correctly on the offscreen buffer
		super.update(this.offscreenGraphics);

		// Draw the contents of the offscreen buffer to screen.
		g.drawImage(this.offscreenImage, 0, 0, this);
	}

	/**
	 * Consults the model to paint the game matrix. If model is null, draws a
	 * default text.
	 */
	@Override
	public void paintComponent(final Graphics g) {
		// Check if we have a running game
		super.paintComponent(g);
		g.setColor(this.getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());

		if (this.model != null) {

			// Draw all tiles by going over them x-wise and y-wise.
			for (int i = 0; i < this.modelSize.width; i++) {
				for (int j = 0; j < this.modelSize.height; j++) {
					GameTile tile = this.model.getGameboardState(i, j);
					tile.draw(g, i * this.tileSize.width, j
							* this.tileSize.height,
							this.tileSize);
					setKey(keyMap[j][i], "00ff00");
				}
			}
		} else {
			g.setFont(new Font("Sans", Font.BOLD, 24));
			g.setColor(Color.BLACK);
			final char[] message = "No model chosen.".toCharArray(); 
			g.drawChars(message, 0, message.length, 50, 50);
		}


	}

	public static void setKey(String keyCode, String color){
		try {
			Runtime run = Runtime.getRuntime();

			Process proc = run.exec(new String[]{"/bin/sh", "-c", "echo rgb " + keyCode + ":" + color + " > /dev/input/ckb1/cmd"});
			proc.waitFor();
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while (br.ready())
				System.out.println(br.readLine());
		}catch (IOException e){}
		catch (InterruptedException ex){}
	}
}
