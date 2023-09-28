/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The GameEngine is responsible for holding the game logic and game updating for the entire game.
 * 		- We see that this class has the most dependencies on Classes and Interfaces as it is responsible for
 * 		  initialising the game. Note it ONLY has 3 Class Dependencies (AlienController, Player and Config Classes)
 *
 * Interfaces/Dependencies:
 *          - GameObject: Enables us to hold the GameObjects and call their update methods
 *          - Renderable: Enables us to hold the Renderable objects and check logic
 *          - Moveable: Enables us to call upon the Moveable interface methods
 *          - Damageable: Enables us to call upon the Damageable interface methods
 *          - StateObject: Enables us to call upon the StateObject interface methods
 *          - Shooter: Enables us to call upon the Shooter Interface methods
 *          - AlienController: Allows us to control the Alien movement of the horde
 *          - ConfigAliens, ConfigBunkers, ConfigPlayer --> Objects required for initialising objects
 *          - Player: Player Class reference to get the Player Object
 *          - BunkerState: To allow us to implement the Strategy Design Pattern for the Bunkers
 */

package invaders.engine;

import java.util.*;

import invaders.GameObject;
import invaders.entities.Player;
import invaders.json.ConfigAliens;
import invaders.json.ConfigBunkers;

import invaders.json.ConfigPlayer;
import invaders.logic.Damagable;
import invaders.logic.Shooter;
import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.rendering.Renderable;
import invaders.statePattern.*;

public class GameEngine {

	/** Holds the list of gameObjects to call update() on. */
	private List<GameObject> gameobjects;
	/** Holds the list of renderables which is retrieved from the GameWindow class for creating EntityView Objects */
	private List<Renderable> renderables;
	/** Holds list of multi-direction objects in the game (aliens and player). */
	private List<Moveable> moveables;
	/** Enables us to have a reference to only the aliens in the game without the requirement of a dependency on the
	 * AlienProduct Class. */
	private List<Renderable> aliens;
	/** Stores the list of bunkers in the game without creating a dependency on BunkerProduct Class.*/
	private List<Renderable> bunkers;
	/** Holds all damageable objects in the game (Player, Bunkers, Aliens, Bullets). */
	private List<Damagable> damagables;
	/** Holds the player --> Note: this is created to uphold the Open/Close principle as it enables us to just add
	 * to this list rather than having the create multiple player objects. */
	private List<Renderable> playerList;
	/** Holds list of StateObjects which enables us to transition the state of objects by calling the methods
	 * contained within the StateObject Interface. */
	private List<StateObject> stateObjects;
	/** Holds a list of Shooter Interface objects allowing us to call upon the shoot() method for the aliens.
	 *		- Note: We do not have Player implement this as Player already contains a pre-defined shoot method. */
	private List<Shooter> shooterables;
	/** Holds reference to the AlienController Class which contains the logic in order to determine the front most
	 * Aliens of each column in the Alien Matrix. */
	private AlienController ac;
	private final String config;
	private ConfigPlayer cp;
	/** Holds reference to the Player Class Object instantiated */
	private Player player;
	/** Holds reference to the Random Java Import which is utilised to generate the intervals between shots for
	 * the Aliens. */
	private Random random;
	/** Enables us to hold reference to the singular projectile fired by the Player to then check whether it still
	*   remains within the renderables list to the determine whether another projectile can be fired. */
	private Renderable playerBullet;
	/** Holds reference to the alienBullets, so we are able to handle the collisions of the projectiles in addition
	*   to determining whether 3 or fewer bullets are on the screen at one time. */
	private List<Renderable> alienBullets;
	/** Enables us to cycle through the states of the Bunkers Based on the health of each bunker */
	private Map<Integer, BunkerState> stateMap = new HashMap<>();
	/** Utilised to call upon on the left movement of the player when pressed */
	private boolean left;
	/** Utilised to call upon on the right movement of the player when pressed */
	private boolean right;
	/** Holds reference to the Window Width of the Game passed through the GameEngine Constructor */
	private final int wWidth;
	/** Holds reference to the Window Height of the Game passed through the GameEngine Constructor */
	private final int wHeight;
	/** Holds reference to whether the player has a bullet currently on the screen. Could have alternatively
	*   checked whether the playerBullet exists within renderables however this requires another for-loop iteration */
	private boolean fired;
	/** Used to store the random Long generates by the random.nextInt() function to randomly separate the firing
	*   sequence of the Aliens. */
	private long timer;

	/** Constructor takes in the JSON file path, Window Width and Window Height passed from GameWindow. */
	public GameEngine(String config, int wWidth, int wHeight) {
		this.wWidth = wWidth;
		this.wHeight = wHeight;
		this.config = config;

		/* Creating the respective lists to hold the interface objects as specified above. */
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();
		stateObjects = new ArrayList<StateObject>();
		shooterables = new ArrayList<Shooter>();
		alienBullets = new ArrayList<Renderable>();
		aliens = new ArrayList<Renderable>();
		moveables = new ArrayList<Moveable>();
		damagables = new ArrayList<Damagable>();
		playerList = new ArrayList<Renderable>();
		bunkers = new ArrayList<Renderable>();

		/* Passing the difference States into the game to be called upon the Bunkers. */
		stateMap.put(1, new HeavyDamageBunker());
		stateMap.put(2, new LightDamageBunker());
		stateMap.put(3, new NoDamageBunker());

		random = new Random(); // Initialising the Random Function for the Alien Shooting

		ConfigAliens.configGameItem(config,aliens);

		/* Creating instance of the AlienController to return the frontMostRow of the Aliens */
		ac = new AlienController(aliens);

		/* Adding Aliens to the respective lists in order to iterate through the objects call the interface defined
		 * methods without creating an AlienProduct Class dependency */
		for (Renderable ro: aliens){
			damagables.add((Damagable) ro);
			gameobjects.add((GameObject) ro);
			renderables.add(ro);
			moveables.add((Moveable) ro);
			shooterables.add((Shooter) ro);
		}

		ConfigBunkers.configGameItem(config, bunkers);

		/* Adding Bunkers to the respective lists in order to iterate through the objects call the interface defined
		 *  methods without creating an BunkerProduct Class dependency */
		for (Renderable ro : bunkers){
			damagables.add((Damagable) ro);
			gameobjects.add((GameObject) ro);
			renderables.add(ro);
			stateObjects.add((StateObject) ro);
		}

		player = ConfigPlayer.configGameItem(config); // Initialisation of the Player Object

		/* Adding Player to respective lists in order to no have to class methods directly but rather through
		 *  interface defined methods. */
		playerList.add((Renderable) player);
		renderables.add((Renderable) player);
		damagables.add((Damagable) player);

		timer = 0; // Initialisation of the Timer;
	}
	/**
	 * Updates the game/simulation
	 */
	public void update() {
		//counter++;
		if(!renderables.contains(playerBullet)){
			fired = false;
		}

		movePlayer(); // Checks if KeyEvent has occured an updates player accordingly
		alienBullets.removeIf(alienbullet -> !(alienbullet).getMarked()); // Removes Bullet if getMarked() is false

		/* Iterating through AlienBullet List:
		  		- Checks if less than 3 Alien bullets on screen & the timer has been passed
		  		- If there exists a possible a shooter then it calls upon an alien to shoot
		  		- Creates bullet and adds to respective list
		 */
		if(alienBullets.size() < 3 && System.currentTimeMillis() > timer) {
			List<Renderable> shootersRen = ac.getFrontMostAliens(aliens);
			if (shootersRen.size() > 0){
				int randomNum = random.nextInt(shootersRen.size());
				Shooter shootingAlien = ac.getShooter(shooterables, shootersRen.get(randomNum));
				Renderable shot = shootingAlien.shoot();
				alienBullets.add(shot);
				damagables.add((Damagable) shot);
				renderables.add(shot);
				gameobjects.add((GameObject) shot);
				timer = System.currentTimeMillis() + random.nextLong(2000);
			}
		}

		gameobjects.removeIf(go -> !((Renderable)go).getMarked()); //Checks if gameObject is marked if so then deletes

		for (GameObject go : gameobjects){
			go.update();  // Iterates through gameObjects and calls update on each GameObject instance
		}

		moveables.removeIf(mo -> !((Renderable)mo).getMarked());  // Checks if Moveable is marked, if so then deletes

		/* Iterating through Damageable List:
				- Checks Collision of Renderable Objects and Damageable
				- If Collision occurs make Damageable object take damage
				- If Damageable object has 0 health, remove from Damageable List
				- If Bunker collides with Alien, bunker is destroyed alien isn't damaged
		 */
		Iterator<Damagable> dmgIterator = damagables.iterator();
		while (dmgIterator.hasNext()) {
			Damagable dg = dmgIterator.next();
			for (Renderable renderInner : renderables) {
				if ((Renderable) dg != renderInner && ((Collider) dg).isColliding((Collider) renderInner) && dg.getHealth() > 0) {
					handleCollision(dg, renderInner); //Collision handling function
				}
			}
			if (dg.getHealth() == 0) dmgIterator.remove(); // Removes object from damageables if it has 0 health
		}

        aliens.removeIf(renderable -> !renderable.getMarked()); // Checks if Alien is marked, if so then deletes

		// State Change Check Based on Bunker Health;
		Iterator<StateObject> bunker = stateObjects.iterator();
		while (bunker.hasNext()){
			StateObject so = bunker.next();
			BunkerState updatedState = stateMap.get(((Damagable)so).getHealth()); //Retrives State based on Health
			if(updatedState != null){  			// Requires Null check as when Bunker destroyed health  = 0 so we skip it
				updatedState.handle(so); 		// Changes State of Bunker on health change
			}
		}

		stateObjects.removeIf(so -> !((Renderable)so).getMarked()); // Checks if StateObject is marked, if so then deletes
		bunkers.removeIf(bunkerObj -> !(bunkerObj).getMarked()); // Checks if Bunker is marked, if so then deletes

		// ensure that renderable foreground objects don't go off-screen
		for (Renderable ro : renderables) {
			if (!ro.getLayer().equals(Renderable.Layer.FOREGROUND)) {
				continue;
			}
			if (ro.getPosition().getX() + ro.getWidth() >= wWidth) {
				ro.getPosition().setX(wWidth- ro.getWidth());
				if(checkIfAlien(ro)){ 	// If alien collides with Border, calls aliens to move down
					moveAliensDown();
					break;
				}
			}

			if (ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(0);
				if(checkIfAlien(ro)){	// If alien collides with Border, calls aliens to move down
					moveAliensDown();
					break;
				}
			}

			if (ro.getPosition().getY() + ro.getHeight() >= wHeight) {
				ro.getPosition().setY((wHeight-1) - ro.getHeight());
			}

			if (ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(1);
			}
		}
	}

	/** Retrieves the Renderables list which is utilised in GameWindow for Creating and Deleting EntityView Objects. */
	public List<Renderable> getRenderables() {
		return renderables;
	}

	/** On Left Key release, changes the boolean left to false indicating the player to stop moving. */
	public void leftReleased() {
		this.left = false;
	}
	/** On Right Key release, changes the boolean right to false indicating the player to stop moving. */
	public void rightReleased() {
		this.right = false;
	}
	/** On Left Key press, changes the boolean left to true indicating the player to move left. */
	public void leftPressed() {
		this.left = true;
	}
	/** On Right Key press, changes the boolean right to true indicating the player to move right. */
	public void rightPressed() {
		this.right = true;
	}
	/** When SpaceBar is pressed, function is called and fires projectile if 'fired' is false. */
	public boolean shootPressed() {
		if(!fired){ 		// Check to determine whether bullet already exists on the screen
			// Adds created bullet to respective classes
			playerBullet = player.shoot();
			renderables.add(playerBullet);
			gameobjects.add((GameObject)playerBullet);
			damagables.add((Damagable) playerBullet);
			fired = true;	// Fired boolean 'true' indicating PlayerBullet on screen allowing only single fire
			return true;
		}
		return false; 		// If bullet already in Renderables, don't allow another fire
	}
	/** Called within each update() loop which then if updates the players movement based on boolean values passed through. */
	private void movePlayer() {
		if (left) {
			player.left();
		}
		if (right) {
			player.right();
		}
	}


	/** moveAliensDown responsible for moving all aliens down once an Alien collides within boarder. */
	public void moveAliensDown() {
		for (Moveable mo : moveables) {
			if (checkIfAlien((Renderable) mo)) {
				mo.down();
			}
		}
	}
	/** checkIfAlien iterates through Alien list to determine whether passed Renderable Object is an Alien.
	 * Prevents the dependency on the AlienProduct Class. */
	public boolean checkIfAlien(Renderable ro){
		for (Renderable alien : aliens){
			if (ro == alien){
				return true;
			}
		}
		return false;
	}
	/** checkIfBunker iterates through Bunker list to determine whether passed Renderable Object is a Bunker.
	 * Prevents the dependency on the BunkerProduct Class. */
	public boolean checkIfBunker(Renderable ro){
		for (Renderable bunk : bunkers){
			if (ro == bunk){
				return true;
			}
		}
		return false;
	}
	/** checkIfPlayer iterates through PlayerList to determine whether passed Renderable Object is a Player. */
	public boolean checkIfPlayer(Renderable ro){
		for (Renderable play : playerList){
			if (ro == play){
				return true;
			}
		}
		return false;
	}
	/** checkIfAlienBullet iterates through AlienBullet list to determine whether passed Renderable Object is an Alien Bullet.
	 * Prevents the dependency on the AlienBullet Class. */
	public boolean checkIfAlienBullet(Renderable ro) {
		for (Renderable bull : alienBullets) {
			if (ro == bull) {
				return true;
			}
		}
		return false;
	}
	/** alienSpeedUp increases the speed for all aliens on the screen. It is called upon the death of an Alien, whereby the
	 * speed (xVel) is increased by a multiplier set in the AlienProduct Class (Which could be passed via the config). */
	public void alienSpeedUp(){
		for (Renderable ro : aliens){
			((Moveable)ro).up();
		}

	}
	/** getLowestAlien retrieves the largest y-coordinate of all alive Aliens in order to determine whether Aliens
	 * are at Player level. If so GameOver process is initiated. */
	public boolean getLowestAlien(){
		for (Renderable ro : aliens){
			if(ro.getPosition().getY() >= player.getPosition().getY()){
				return true;
			}
		}
		return false;
	}
	/** getLives retrieves player lives for calling in the GameWindow Class, whereby if getLives returns 0, GameOver
	 * sequence is initiated. */
	public int getLives(){
		return player.getHealth();
	}
	/** getAliens retrieves no. of alive Aliens remaining in the game. Called by GameWindow as part of logic for initialising
	 * the gameWin sequence. */
	public int getAliens(){
		return this.aliens.size();
	}
	/** handleCollision is responsible for applying the correct logic for damaging the correct Damageable Object,
	 * due to the several conditions of the game which specified which Objects inflict which damage*/
	private void handleCollision(Damagable dg, Renderable renderInner) {
		if (checkIfBunker((Renderable) dg) && checkIfAlien(renderInner)) {
			// If Damageable is a Bunker and Collides with an Alien, Bunker is destroyed.
			dg.takeDamage(dg.getHealth());
		} else if (checkIfAlien((Renderable) dg) && checkIfBunker(renderInner)) {
			// If Bunker Collides with Alien, Alien does not take damage as Bunker is destroyed.
			return;
		} else if (checkIfPlayer((Renderable) dg) && checkIfAlien(renderInner)) {
			//  If Damageable is Player and collides with Alien, Player is killed.
			dg.takeDamage(dg.getHealth());
		} else if (checkIfAlien((Renderable) dg) && checkIfPlayer(renderInner)) {
			// If Damageable is an Alien and collides with Player, Alien is not affected.
			return;
		} else if ((checkIfAlienBullet((Renderable) dg) && checkIfAlien(renderInner)) ||
				(checkIfAlien((Renderable) dg) && checkIfAlienBullet(renderInner))) {
			// If Alien Bullet collides with Alien during Down call, neither is damaged. Bug Fix in even of bullet fired during aliens moving down.
			return;
		} else {
			dg.takeDamage(1);
			if (checkIfAlien((Renderable) dg)) {
				// If alien is shot, speed up the remaining aliens.
				alienSpeedUp();
			}
		}
	}
	/** reset function called upon game reset. Note: we are required to re-initialse all objects do to removing them
	 * once a Renderable getMarked() returns false. Re-initialising all objects was significantly more efficient then
	 * keeping the objects within the repsective lists throughout the whole game regardless if they were no longer
	 * being displayed (i.e. were destroyed). */
	public void reset(){
		listClear();
		ConfigAliens.configGameItem(config,aliens);
		ac = new AlienController(aliens);

		for (Renderable ro: aliens){
			damagables.add((Damagable) ro);
			gameobjects.add((GameObject) ro);
			renderables.add(ro);
			moveables.add((Moveable) ro);
			shooterables.add((Shooter) ro);
		}
		ConfigBunkers.configGameItem(config, bunkers);

		for (Renderable ro : bunkers){
			damagables.add((Damagable) ro);
			gameobjects.add((GameObject) ro);
			renderables.add(ro);
			stateObjects.add((StateObject) ro);
		}
		player = ConfigPlayer.configGameItem(config);
		playerList.add((Renderable) player);
		renderables.add((Renderable) player);
		damagables.add((Damagable) player);
		gameobjects.add((GameObject) player);
		timer = 0;
	}
	/** listClear is responsible for emptying the lists of all objects prior to resetting the game. */
	public void listClear(){
		gameobjects.clear();
		renderables.clear();
		moveables.clear();
		stateObjects.clear();
		damagables.clear();
		shooterables.clear();
		playerList.clear();
		alienBullets.clear();
		aliens.clear();
		bunkers.clear();
	}
}
