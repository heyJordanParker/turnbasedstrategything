package game.unit;

import java.util.ArrayList;

import game.field.Field;
import game.field.Square;
import game.item.Item;
import game.main.Animation;
import game.main.Sprite;
import game.player.Player;
import game.race.Race;
import game.spells.Talent;

public class Hero extends Unit {

	//combat - left/right move,stand,attack,cast,die
	/*	private Sprite leftHeadingSprite;
	private Animation leftHeadingAnimation;
	private Sprite rightHeadingSprite;
	private Animation rightHeadingAnimation;*/
	@SuppressWarnings("unused")
	private ArrayList<Talent> taletTree = new ArrayList<Talent>(100);
	@SuppressWarnings("unused")
	private ArrayList<Item> inventory = new ArrayList<Item>(100);
	private int x; //upper left coordinate ON SCREEN
	private int y; //upper left coordinate ON SCREEN
	private Player owner;
	private Square currentSquare;
	private Race race;
	private int heading;
	// for heading 
	// 1 = up
	// 2 = down
	// 3 = left
	// 4 = right
	// 13 = upLeft
	// 14 = upRight
	// 23 = downLeft
	// 24 = downRight
	private boolean moving;
	private Sprite currentMovingSprite;
	private GraphicalData graphicalData;
	private Animation currentAnimation;
	// to include combat stats and graphical data as different classes
	
	public Hero(){
		heading = 1;
		graphicalData = new GraphicalData();
	}
	
	public int getHeroX(){
		return x;
	}
	
	public int getHeroY(){
		return y;
	}

	public void setHeroX(int x){
		this.x = x;
	}
	
	public void setHeroY(int y) {
		this.y = y;
	}
	
	public void setHeroLocation(int x, int y,Field f){
		if(this.x != 0 && this.y != 0){
			f.getSquare(this.x, this.y).setHero(null); //Occupied(false);
		}
		this.x = x;
		this.y = y;
		f.getSquare(x, y).setHero(this); //Occupied(true);
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public void setCurrentSquare(Square currentSquare) {
		this.currentSquare = currentSquare;
	}

	public Square getCurrentSquare() {
		return currentSquare;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Race getRace() {
		return race;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public int getHeading() {
		return heading;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setCurrentMovingSprite(Sprite currentMovingSprite) {
		this.currentMovingSprite = currentMovingSprite;
	}

	public Sprite getCurrentMovingSprite() {
		return currentMovingSprite;
	}

	public void setGraphicalData(GraphicalData graphicalData) {
		this.graphicalData = graphicalData;
	}

	public GraphicalData getGraphicalData() {
		return graphicalData;
	}
	
	public Animation getStandAnimation(){
		if(heading==1){
			return graphicalData.getwMMUpStand();
		}
		if(heading==2){
			return graphicalData.getwMMDownStand();
		}
		if(heading==3){
			return graphicalData.getwMMLeftStand();
		}
		if(heading==4){
			return graphicalData.getwMMRightStand();
		}
		if(heading==13){
			return graphicalData.getwMMUpLeftStand();
		}
		if(heading==14){
			return graphicalData.getwMMUpRightStand();
		}
		if(heading==23){
			return graphicalData.getwMMDownLeftStand();
		}
		if(heading==24){
			return graphicalData.getwMMDownRightStand();
		}
		return null;
	}
	
	public void moveOneSquare() {
		
		moving = true;
		
		if(heading==1){
			setCurrentAnimation(graphicalData.getWorldMapMovementUp());
			
			// to create sprites from moving animations!!
			
		}
		if(heading==2){
			setCurrentAnimation(graphicalData.getWorldMapMovementDown());
		}
		if(heading==3){
			setCurrentAnimation(graphicalData.getWorldMapMovementLeft());
		}
		if(heading==4){
			setCurrentAnimation(graphicalData.getWorldMapMovementRight());
		}
		if(heading==13){
			setCurrentAnimation(graphicalData.getWorldMapMovementUpLeft());
		}
		if(heading==14){
			setCurrentAnimation(graphicalData.getWorldMapMovementUpRight());
		}
		if(heading==23){
			setCurrentAnimation(graphicalData.getWorldMapMovementDownLeft());
		}
		if(heading==24){
			setCurrentAnimation(graphicalData.getWorldMapMovementDownRight());
		}
		setCurrentAnimation(graphicalData.getWorldMapMovementUp()); // default
		
	}
	
	
//	public getAnimations(){
//		return race.getHeroAnimations;
//	}

	
}
