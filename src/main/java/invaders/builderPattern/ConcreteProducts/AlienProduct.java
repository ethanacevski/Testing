/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The AlienProduct class is the ConcreteProduct participant within the Builder Design Pattern for Aliens.
 *          Its responsibility to define the constructor and parameters which must be specified in order to create
 *          an AlienProduct Object
 *
 * Extension/Implementations:
 *      - Entity: Reduces code repetition across concrete classes
 *      - Moveable: Aliens are moveable objects in the game
 *      - Shooter: Aliens are responsible for firing projectiles throughout the game
 *
 * Dependencies:
 *      - ProjectileLogic: The Projectile of an Alien is dependent on the logic passed through the config file
 *              as it is part of the Strategy Design Pattern
 *      - AlienBulletFactory: The creation of bullets is through an AlienBullet Factory which is assigned to the alien
 *              as it enables the creation of the bullet to be contained within the creation of the alien. Additionally,
 *              it is part of the Factory Design Pattern requirement.
 *      - Vector2D: For the position of the Alien and Projectile
 */


package invaders.builderPattern.ConcreteProducts;

import invaders.factoryPattern.factories.AlienBulletFactory;
import invaders.json.ConfigGame;
import invaders.logic.Shooter;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategyPattern.ProjectileLogic;
import javafx.scene.image.Image;

import java.io.File;

public class AlienProduct extends Entity implements Moveable, Shooter {

    private int wWidth;
    private int wHeight;
    private double xVel;
    private double startXVel;
    private ProjectileLogic projectileLogic;
    private AlienBulletFactory aBulletFact;
    private final int bulletWidth;
    /** Holds ref for player starting Health. */
    private final int bulletHeight;
    private final int bulletStrength;

    public final String path = "src/main/resources/config.json";

    public AlienProduct(Vector2D position,
                        double width,
                        double height,
                        Image sprite,
                        Layer layer,
                        boolean alive,
                        int health,
                        double xVel,
                        ProjectileLogic projectileLogic,
                        int bulletWidth,
                        int bulletHeight,
                        int bulletStrength){
        super(position,width,height,sprite,layer,alive,health);
        this.xVel = xVel;
        this.projectileLogic = projectileLogic;
        this.bulletWidth = bulletWidth;
        this.bulletHeight = bulletHeight;
        this.bulletStrength = bulletStrength;
        this.wWidth = ConfigGame.configGameItem(path)[0];
        this.wHeight = ConfigGame.configGameItem(path)[1];
        this.aBulletFact = new AlienBulletFactory();
    }


    @Override
    public void start() {
    }

    @Override
    public void update() {
        this.getPosition().setX(this.getPosition().getX()+ this.getxVel());
    }

    @Override
    public void up() {
        this.xVel = this.xVel*1.2;
    }

    @Override
    public void down() {
        this.getPosition().setY(this.getPosition().getY()+this.getWidth());
        if(this.xVel > 0){
            this.setxVel(-Math.abs(this.xVel));
        }else {
            this.setxVel(Math.abs(this.xVel));
        }
    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public Image getImage() {
        return this.sprite;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return this.layer;
    }

    @Override
    public boolean getMarked() {
        return this.alive;
    }

    public double getxVel() {
        return xVel;
    }
    public void setxVel(double xVel){
        this.xVel = xVel;
    }

    @Override
    public void takeDamage(int amount) {
        if(this.health > amount) {
            this.health -= amount;
        } else {
            this.health = 0;
            this.alive = false;
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
    public Renderable shoot() {
        //Creating the Position of the bullet
        Vector2D bullpos = new Vector2D(this.getPosition().getX()+(this.getWidth()/2)-this.bulletWidth,
                this.getPosition().getY()+this.bulletHeight+10);

        //Creating the Bullet
        Renderable bullet = aBulletFact.createBullet(bullpos,this.bulletWidth,this.bulletHeight,
                new Image(new File("src/main/resources/" +
                        "playerProjectile/ProjectileTravel1.png")
                        .toURI().toString()),
                Layer.FOREGROUND,this.projectileLogic.projectileType()
                ,true,this.bulletStrength);
        return bullet;
    }
}
