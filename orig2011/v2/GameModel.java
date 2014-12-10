package orig2011.v2;

/**
 * Common interface for all game model classes.
 * 
 * Constructors of subclasses should initiate matrix elements and additional,
 * game-dependent fields.
 */
public interface GameModel {

	/**
	 * Returns the GameTile in logical position (x,y) of the gameboard.
	 *
	 * @param pos
	 *            The position in the gameboard matrix.
	 */
	public GameTile getGameboardState(final Position pos);

	/**
	 * Returns the GameTile in logical position (x,y) of the gameboard.
	 *
	 * @param x
	 *            Coordinate in the gameboard matrix.
	 * @param y
	 *            Coordinate in the gameboard matrix.
	 */
	public GameTile getGameboardState(final int x, final int y);

	/**
	 * Set the tile on a specified position in the gameboard.
	 *
	 * @param pos
	 *            The position in the gameboard matrix.
	 * @param tile
	 *            The type of tile to paint in specified position
	 */
	public void setGameboardState(final Position pos, final GameTile tile);

	/**
	 * Set the tile on a specified position in the gameboard.
	 *
	 * @param x
	 *            Coordinate in the gameboard matrix.
	 * @param y
	 *            Coordinate in the gameboard matrix.
	 * @param tile
	 *            The type of tile to paint in specified position
	 */
	public void setGameboardState(final int x, final int y,
									 final GameTile tile);


	/**
	 * This method is called repeatedly so that the game can update it's state.
	 * 
	 * @param lastKey
	 *            The most recent keystroke.
	 */
	public void gameUpdate(int lastKey) throws GameOverException;
}
