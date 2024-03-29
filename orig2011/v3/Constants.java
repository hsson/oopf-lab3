package orig2011.v3;

import java.awt.*;

/** Provides a fixed size for various games. */
public enum Constants {
	;
	// Safe Singleton pattern, prevent instantiation.
	/** An immutable Dimension object of constant size. */
	private static final Dimension SIZE = new Dimension(10, 5);
	
	/** @return an copy of the Dimension constant. */
	public static Dimension getGameSize() {
		// Dimension is a mutable class, copy to prevent mutation.
		return new Dimension(SIZE);
	}
}
