/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Bullet Abstract Class has the purpose of reducing code repetitiveness as the AlienBullet and PlayerBullet
 * have identical constructors with just the GameObject Methods being different, due to the traveling projectile direction.
 *
 * Participant: The Bullet acts as the Product (Abstract Class) participant. Note: I have utilised a Abstract Factory Implementation as allowed (Ed Post #406)
 * as it reduces to code repetitiveness as mentioned above.
 */

package invaders.factoryPattern.bullets;

import invaders.GameObject;
import invaders.json.ConfigGame;
import invaders.logic.Damagable;
import invaders.physics.Collider;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategyPattern.ProjectileLogic;
import javafx.scene.image.Image;

public abstract class Bullet implements GameObject, Renderable, Collider, Damagable {
    protected int wWidth;
    protected int wHeight;
    public final String path = "src/main/resources/config.json";

    protected Vector2D position;
    protected double width;
    protected double height;
    protected Image sprite;
    protected Layer layer;
    protected double yVel;
    protected boolean active;
    protected int health;
    protected ProjectileLogic projectileLogic;

    public Bullet(Vector2D position,
                       double width,
                       double height,
                       Image sprite,
                       Layer layer,
                       double yVel,
                       boolean active,
                       int health){
        this.position = position;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.layer = layer;
        this.yVel = yVel;
        this.active = active;
        this.health = health;
        this.wWidth = ConfigGame.configGameItem(path)[0];
        this.wHeight = ConfigGame.configGameItem(path)[1];
    }

    @Override
    public void start() {
        this.active = false;

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
    public int getHealth(){
        return this.health;
    }

    @Override
    public boolean isAlive(){
        return this.active;
    }

    @Override
    public void takeDamage(int amount){
        if(this.health > amount){
            this.health -= amount;
        }else{
            this.health = 0;
            this.active = false;
        }
    }

    public double getyVel(){
        return this.yVel;
    }
    public int getwWidth(){
        return this.wWidth;
    }
    public int getwHeight(){
        return this.wHeight;
    }
    public void setyVel(double yVel){
        this.yVel = yVel;
    }
}
