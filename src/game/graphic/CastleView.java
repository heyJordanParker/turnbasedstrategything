package game.graphic;
/* IMAGES POSITIONING INFO
 * Background - 1280x800, coordinates 0x0
 * Town Hall - coordinates 504x0
 * Barracks - coordinates 206x417
 * Archery - coordinates 66x413
 * MageTower - coordinates 627x296
 * Market - coordinates 637x524
 * Tavern - coordinates 865x508
 * Keep - coordinates 855x208*/

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import game.castle.Castle;
import game.castle.CastleBuilding;
import game.main.MainClass;
import game.player.Player;
import game.player.Players;
import game.unit.Hero;

public class CastleView {
	
	private Castle castle; // index of castle in ArrayList<Castle> in player class
	private boolean inCastle; // ako e true - se risuva CastleView ako ne e true ne se risuva
	private MainClass mc;
	private int menuBuilding;
	private Image menuFrame;
	private MouseEvent mouseInput;
	private int selectedIndex;
	private int index;
	
	public CastleView (Castle castle, MainClass mc) {
		setInCastle(true);
		this.castle = castle;
		//this.castle.setOwner(mc.getCurrentPlayer());
		//this.castle = mc.getCurrentPlayer().getCastles().get(castle);
		menuBuilding = -1;
		menuFrame = Toolkit.getDefaultToolkit().getImage("Images/Castle/Frame.png");
	
	}
	
	public void update(long timePassed){
	}
	
	public void draw(Graphics g){
		// Draw background
		g.drawImage(castle.getBackground(),0,0,null);
//		g.drawImage(Toolkit.getDefaultToolkit().getImage("Images/castle/Background.png"), 0, 0, null);
		// Draw individual buildings
		for (CastleBuilding current : castle.getBuildings()) {
			if (current.isBuilt()) {
				g.drawImage(current.getImage(),current.getX(),current.getY(),null);
			}
		}
		// Hero/unit icons - 36x36px with 40x40px borders
		g.drawImage(menuFrame,1,1,null);
		//draw resource bars
		//System.out.print(castle.getOwner().getGold().getAmount());
		g.drawImage(Toolkit.getDefaultToolkit().getImage("src/game/images/test/ResourceBar.jpg"),20,45,null);
		g.drawString("Gold: " + castle.getOwner().getGold().getAmount(),30,65);
		g.drawImage(Toolkit.getDefaultToolkit().getImage("src/game/images/test/ResourceBar.jpg"),20,70,null);
		g.drawString("Wood: " + castle.getOwner().getWood().getAmount(),30,90);
		g.drawImage(Toolkit.getDefaultToolkit().getImage("src/game/images/test/ResourceBar.jpg"),20,95,null);
		g.drawString("Stone: " + castle.getOwner().getStone().getAmount(),30,115);
//		//draw hero/unit icons
		if(castle.getGarrisonSquare().getHero()!=null){
			g.drawImage(castle.getGarrisonSquare().getHero().getIcon(),901,11,null);
			for (int i=0;i<8;i++) {
				if (castle.getGarrisonSquare().getHero().getUnits().get(i) != null)
					g.drawImage(castle.getGarrisonSquare().getHero().getUnits().get(i).getIcon(),943 + (40*i),11,null);
			}
			
		} else {
			for (int i=0;i<8;i++) {
				if (castle.getGarrison().getUnits().get(i) != null) {
					g.drawImage(castle.getGarrison().getUnits().get(i).getIcon(),943 + (40*i),13,null);
				}
			}
		}
		if(castle.getCurrentSquare().getHero()!=null){
			g.drawImage(castle.getCurrentSquare().getHero().getIcon(),901,61,null);
			for (int i=0;i<8;i++) {
				if (castle.getCurrentSquare().getHero().getUnits().get(i) != null) {
					g.drawImage(castle.getCurrentSquare().getHero().getUnits().get(i).getIcon(),943 + (40*i),53,null);
				}
			}
		}
		//draw sub menus/handle input
		//if (menuBuilding != -1) {
		//	castle.getBuilding(menuBuilding).drawMenu(g,this);
		//} else if (mouseInput != null) {
		//			castle.handleInput(this,mc);
	}

	public void setInCastle(boolean isInCastle) {
		this.inCastle = isInCastle;
	}

	public boolean isInCastle() {
		return inCastle;
	}

	public void mouseClicked(MouseEvent e){
		mouseInput = e;
	}
	
	public int getSelected() {
		return selectedIndex;
	}
	
	public void setSelected(int index) {
		selectedIndex = index;	
	}
}
