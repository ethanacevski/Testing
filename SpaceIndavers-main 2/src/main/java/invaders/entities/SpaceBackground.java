/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The SpaceBackground Class was given from the Sample Code. The purpose of this class is to define the Establish
 * the background for the Game which is just a Black Screen with dimensions passed through GameWindow.
 * Interfaces:
 *          - Renderable: The SpaceBackground is a Renderable Object.
 */

package invaders.entities;

import invaders.engine.GameEngine;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;

public class SpaceBackground implements Renderable {
	private Rectangle space;

	public SpaceBackground(GameEngine engine, Pane pane){
		double width = pane.getWidth();
		double height = pane.getHeight();
		space = new Rectangle(0, 0, width, height);
		space.setFill(Paint.valueOf("BLACK"));
		space.setViewOrder(1000.0);

		pane.getChildren().add(space);
	}

	public Image getImage() {
		return null;
	}

	@Override
	public double getWidth() {
		return 0;
	}

	@Override
	public double getHeight() {
		return 0;
	}

	@Override
	public Vector2D getPosition() {
		return null;
	}

	@Override
	public Layer getLayer() {
		return Layer.BACKGROUND;
	}

	@Override
	public boolean getMarked() {
		return true;
	}
}
