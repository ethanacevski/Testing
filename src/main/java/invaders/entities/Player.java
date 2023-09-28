/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Player Class was given from the Sample Code. The purpose of this class is to define the Player
 * Object whereby we define its constructor the methods it must implement based on the classes it implements
 *
 * Interfaces:
 *          - Moveable: As the player is a moveable object
 *          - Damageable: As the player can be damaged via the Alien Projectiles
 *          - Renderable: Player is rendered on the screen.
 *          - Collider: Player can collide with Projectiles and Aliens.
 *          - GameObject: Player is a GameObject
 * Dependencies:
 *          - PlayerBulletFactory: Utilised to create projectiles for the Player
 *          - PixelReaderAdj: Utilised to change the colour of the Player to the specified colour in the Config
 */

package invaders.entities;

import invaders.GameObject;
import invaders.engine.PixelReaderAdj;
import invaders.factoryPattern.bullets.Bullet;
import invaders.factoryPattern.bullets.PlayerBullet;
import invaders.factoryPattern.factories.PlayerBulletFactory;
import invaders.logic.Damagable;
import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Player implements Moveable, Damagable, Renderable, Collider {
    /** Holds ref to position of player */
    private final Vector2D position;
    /** Holds ref to the Factory Method used for creating player bullets. */
    private PlayerBulletFactory pBulletFact;
    private final Animator anim = null;
    /** Holds ref to player health. */
    private int health;
    //TODO: CHANGE TO ADD IN CONFIG
    private final double width = 25;
    private final double height = 30;
    /** Holds ref to player being alive or kiled. */
    private boolean alive;
    /** holds ref for the sprite of the player. */
    private final Image image;
    /** Holds ref for player speed. */
    private final int speed;
    /** Holds ref for player bullet Width. */
    private final int bulletWidth;
    /** Holds ref for player starting Health. */
    private final int bulletHeight;
    private final int bulletStrength;
    private final double bulletVel;

    /** Constructor takes in Position of Alien from Config, The Bullet Factor for the player Bullets, Health from config,
     * String from config which specifies the colour to be passed through the PixelReaderAdj. */
    public Player(Vector2D position, PlayerBulletFactory pBulletFact, int health, String colour,
                  int speed, int bulletWidth, int bulletHeight, int bulletStrength, double bulletVel){

        PixelReaderAdj pixelReaderAdj = new PixelReaderAdj(new Image(new File("src/main/resources/player/CoreCannon.png")
                .toURI().toString(), width, height, true, true),
                colour);
        this.image = pixelReaderAdj.spriteColorChange();
        this.speed = speed;
        this.position = position;
        this.pBulletFact = pBulletFact;
        this.alive = true;
        this.health = health;
        this.bulletWidth = bulletWidth;
        this.bulletHeight = bulletHeight;
        this.bulletStrength = bulletStrength;
        this.bulletVel = bulletVel;
    }

    // Below are standard getter and implemented methods from Interfaces. Note: Majority have similar functions so only significantly different methods are described.
    /** takeDamge specifies damage handling. */
    @Override
    public void takeDamage(int amount) {
        if(this.health > 0){
            this.health = health - amount;
        }else {
            this.health = 0;
            this.alive =false;
        }
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - speed);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + speed);
    }

    /** Specifies shoot method of player. */
    public Renderable shoot(){

        //Holders for the bullet attributes --> todo: need to see what to do with this
        //int bulletHeight = 15;
        //int bulletWidth = 5;
        //double bulletYVel = 10;
        //int bulletHealth = 1;


        //Creating the Position of the bullet
        Vector2D bullpos = new Vector2D(this.getPosition().getX()+(this.getWidth()/2)-this.bulletWidth,
                this.getPosition().getY()-this.bulletHeight);

        //Creating the Bullet
        Renderable bullet = pBulletFact.createBullet(bullpos,this.bulletWidth,this.bulletHeight,
                new Image(new File("src/main/resources/" + "playerProjectile/ProjectileTravel1.png").toURI().toString()),
                Layer.FOREGROUND,this.bulletVel,true,this.bulletStrength);
        return bullet;
    }


    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    @Override
    public boolean getMarked() {
        return this.alive;
    }

}
