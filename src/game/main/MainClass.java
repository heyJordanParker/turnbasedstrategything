package game.main;

import game.Interface.Screen;
import game.building.Building;
import game.core.Path;
import game.core.PathNode;
import game.field.Field;
import game.graphic.CombatView;
import game.item.Item;
import game.player.Player;
import game.player.Players;
import game.resource.Resource;
import game.unit.Hero;
import game.unit.TestUnits;
//import game.unit.Unit;
//import javax.swing.*;
import java.awt.*;
//import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

//import javax.swing.event.MouseInputListener;

@SuppressWarnings("unused")
public class MainClass implements KeyListener,MouseMotionListener,MouseListener {
	


	private Screen s;
	private boolean loaded;
	private static Field field;
	private static Player currentPlayer;
	private static MainClass mc;
	private boolean running;
	private boolean exited;
	private int screenWidth;
	private int screenHeight;
	private boolean turnEnded = false;
	private Robot robot;
	private static Players players;
	
	
	//for testing
	private static Hero h;
//	private Image bg;
//	private Image face1;
//	private Animation a;
//	private Sprite sprite;
	private static Path path;
	private static Item pathNode;
	private static CombatView combatView;
	
	public static void main(String args[]){
		field = new Field(500,500);
		// Visibility :: will be calculated every move depending on owned buildings of the player, owned heroes, their visibility and side effects
		//ArrayList<Player> playerList = new ArrayList<Player>(12);
		players = new Players(2);
		currentPlayer = new Player();
		h = new Hero();
		Hero h2 = new Hero();
		currentPlayer.setCurrentPlayer(true);
		
		combatView = new CombatView();
		currentPlayer.getGold().setAmount(1000);
		currentPlayer.newHero(h,480, 480, field);
		currentPlayer.newHero(h2,481, 480, field);
		Building building = new Building();
		building.setImage(Toolkit.getDefaultToolkit().getImage("src/game/images/terrain/Grass1.jpg"));
		field.getSquare(5, 5).setBuilding(building);
		field.getSquare(5, 5).setPassable(false);
		//field.getSquare(498, 498).setHero(h);
		currentPlayer.setCurrentView(field.getSquare(2, 2));
		currentPlayer.setCurrentViewAbsX(0);
		currentPlayer.setCurrentViewAbsY(0);
		TestUnits testUnits = new TestUnits();
		h.addUnit(testUnits.getArcher());
		h.addUnit(testUnits.getMage());
		h.addUnit(testUnits.getWarrior());
		
		
		path = new Path();
		path = path.findPath(field, field.getSquare(4, 5), field.getSquare(14, 5));
		pathNode = new Item();
		pathNode.setImage(Toolkit.getDefaultToolkit().getImage("src/game/images/test/pathSquare.png"));
		
/*		playerRed.Activate();
		if(playerRed.isActive()){
			playerList.add(playerRed);
		}
		TurnSystem turnSystem = new TurnSystem(playerList);*/
		mc = new MainClass();
		// this will be the main graphics class
		// it will get the current player from the turn system
		mc.run();
	}

	private static final DisplayMode modes[] = {
		new DisplayMode(1280,800,32,0),
		//new DisplayMode(1280,800,24,0),
		//new DisplayMode(1280,800,16,0),		
		//new DisplayMode(1024,720,32,0),
		//new DisplayMode(1024,720,24,0),
		//new DisplayMode(1024,720,16,0),
	};
	
	public void run() {
		s = new Screen();
		try {
			DisplayMode dm = s.findCompatibleMode(modes);
			s.setFullScreen(dm);
			Window w = s.getFullScreenWindow();
			w.setFocusTraversalKeysEnabled(false);
			w.addKeyListener(this);
			w.addMouseListener(this);
			w.addMouseMotionListener(this);
			loaded = false;
			running = true;
			loadimages();
			screenWidth = 33;
			screenHeight = 22;
			if(loaded){
				while(!exited){
					if(turnEnded){
						endedTurn();
					}
					movieLoop();
				}
			}
		} finally {
			s.restoreScreen();
		}
	}
	
	public void movieLoop() {
		long startingTime = System.currentTimeMillis();
		long cumTime = startingTime;
		while(running && !exited){
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;
			//a.update(timePassed);
			//sprite.update(timePassed);
			//update sprites and animations
			update(timePassed);
			
			
			Graphics2D g = s.getGraphics();

			draw(g);
			g.dispose();
			s.update();
			
			try {
				Thread.sleep(20);
			} catch(Exception ex){}
		}
	}
	
	private void update(long timePassed) {
//		ArrayList<Animation> a = h.getGraphicalData().getGraphicalData();
//		for(Animation anim : a) {
//				anim.update(timePassed);
//		}
		if(currentPlayer.getSelectedHero() != null){
			if(currentPlayer.getSelectedHero().getCurrentSprite() != null) {
				currentPlayer.getSelectedHero().getCurrentSprite().update(timePassed);
			}
			
			if(currentPlayer.getSelectedHero().getCurrentAnimation() != null) {
				currentPlayer.getSelectedHero().getCurrentAnimation().update(timePassed);
			}
		}
		
		combatView.update(timePassed);
		
		//a.update(timePassed);
		//sprite.update(timePassed);
		
	}

	public void draw(Graphics g){
		
		//different views : world view/city view/combat view
		//currently there is just 1 setup view - world view
		
		//draw buildings (maybe state 1 ,2 ,3 etc for animating)
		/* METHOD :: g.drawImage(sprite.getImage(),Math.round(sprite.getX()),Math.round(sprite.getY()), null); */
		//get square get building - if animations[] contains building.getanmation draw(animations.getbuildinganim.getImage()...) 
		// else draw a single image
		
		combatView.draw(g);
		combatView.setCombat(false);
		
		if(!combatView.isCombat()) {
			if(currentPlayer.getSelectedHero() != null){
				if(currentPlayer.getSelectedHero().isMoving()){
					movingHeroChecker();
				}
			}
			
			for(int x=-2;x<screenWidth;x++){
				for(int y=-2;y<screenHeight;y++){
					
					Image img = field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getImage();
					
					g.drawImage(img
							, Math.round((x)*img.getWidth(null) - currentPlayer.getCurrentViewAbsX()), Math.round((y)*img.getHeight(null) -
										currentPlayer.getCurrentViewAbsY()), null);
					
					if(field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getResource() != null){
						
						Resource r = field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getResource();
						g.drawImage(r.getImage(), Math.round((x)*img.getWidth(null) - currentPlayer.getCurrentViewAbsX()), Math.round((y)*img.getHeight(null) -
								currentPlayer.getCurrentViewAbsY()), null);
						
					}
					
					if(field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getBuilding() != null){
						
						Building building = field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getBuilding();
						g.drawImage(building.getImage(), Math.round((x)*img.getWidth(null) - currentPlayer.getCurrentViewAbsX()), Math.round((y)*img.getHeight(null) -
								currentPlayer.getCurrentViewAbsY()), null);
						
					}
					
					if(field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getItem() != null){
						
						Item r = field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getItem();
						g.drawImage(r.getImage(), Math.round((x)*img.getWidth(null) - currentPlayer.getCurrentViewAbsX()), Math.round((y)*img.getHeight(null) -
								currentPlayer.getCurrentViewAbsY()), null);
						
					}
			
				}
			}		
			
			for(int x=2;x<screenWidth;x++){
				for(int y=-2;y<screenHeight;y++){
					
					Image img = field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getImage();
					
					if(field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getHero() != null){
						
						Hero hero = field.getSquare((x+2+currentPlayer.getCurrentView().getX()),(y+2+currentPlayer.getCurrentView().getY())).getHero();
						if(hero == currentPlayer.getSelectedHero()){
							if(currentPlayer.getSelectedHero().isMoving()){
								
								g.drawImage(currentPlayer.getSelectedHero().getCurrentSprite().getImage() , Math.round((x)*img.getWidth(null) - currentPlayer.getCurrentViewAbsX()-10+currentPlayer.getSelectedHero().getCurrentSprite().getX()), Math.round((y)*img.getHeight(null) -
									currentPlayer.getCurrentViewAbsY()-10+currentPlayer.getSelectedHero().getCurrentSprite().getY()), null);
								
								if(Math.abs(currentPlayer.getSelectedHero().getCurrentSprite().getX())>=40 || Math.abs(currentPlayer.getSelectedHero().getCurrentSprite().getY())>=40) {
									currentPlayer.getSelectedHero().movedOneSquare(field);
								}
								
							} else {
								
								g.drawImage(currentPlayer.getSelectedHero().getStandAnimation().getImage() , Math.round((x)*img.getWidth(null) - currentPlayer.getCurrentViewAbsX()-10), Math.round((y)*img.getHeight(null) -
										currentPlayer.getCurrentViewAbsY()-10), null);
							}
						} else {
							g.drawImage(hero.getStandAnimation().getImage() , Math.round((x)*img.getWidth(null) - currentPlayer.getCurrentViewAbsX()-10), Math.round((y)*img.getHeight(null) -
									currentPlayer.getCurrentViewAbsY()-10), null);
						}
						
					}
					
				}
			}
			
			for(PathNode pn : path.getSquares()){
				field.getSquare(pn.getSquare().getX(), pn.getSquare().getY()).setItem(pathNode);
			}
			
			
			//g.drawImage(a.getImage(), 0, 0, null);
			//g.drawImage(sprite.getImage(),Math.round(sprite.getX()),Math.round(sprite.getY()), null);
			//g.drawImage(face1,1060,30,null);
			//g.drawImage(bg, 0, 0, null);
			// array list for sprite && animations - returns every animation and every sprite
	
			Color color = new Color(255, 255, 255);
			g.setColor(color);
			Font font = new Font(Font.SERIF, Font.BOLD, 18);
			g.setFont(font);
			
			g.drawImage(Toolkit.getDefaultToolkit().getImage("src/game/images/test/ResourceBar.jpg"),20,20,null);
			String string = "X: " + currentPlayer.getCurrentView().getX() + "   Y: " + currentPlayer.getCurrentView().getY();
			g.drawString(string,30,40);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("src/game/images/test/ResourceBar.jpg"),20,45,null);
			string = "Gold: " + currentPlayer.getGold().getAmount();
			g.drawString(string,30,65);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("src/game/images/test/ResourceBar.jpg"),20,70,null);
			string = "Wood: " + currentPlayer.getWood().getAmount();
			g.drawString(string,30,90);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("src/game/images/test/ResourceBar.jpg"),20,95,null);
			string = "Stone: " + currentPlayer.getStone().getAmount();
			g.drawString(string,30,115);
			
			g.drawImage(Toolkit.getDefaultToolkit().getImage("Images/heroes/hero.jpg"),208,18,null);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("Images/heroes/hero.jpg"),248,18,null);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("Images/heroes/hero.jpg"),288,18,null);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("Images/heroes/hero.jpg"),328,18,null);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("Images/heroes/hero.jpg"),368,18,null);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("Images/heroes/hero.jpg"),408,18,null);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("Images/heroes/hero.jpg"),448,18,null);
			
			{
				int h = 210;
				for(Hero hero : currentPlayer.getHeroes()){
					if(hero.isSelected()){
						g.drawImage(Toolkit.getDefaultToolkit().getImage("Images/heroes/hero_selected.jpg"),(h-2),18,null);
					}
					g.drawImage(hero.getIcon(),h,20,null);
					h += 40;
				}
			}
			
			
			currentViewChecker();
		}
	}

	public void loadimages() {
		//Image img1 = Toolkit.getDefaultToolkit().getImage("game/terrain/images/Grass1.jpg"); /
//		bg = Toolkit.getDefaultToolkit().getImage("src/game/images/test/bg.png"); 
//		face1 = Toolkit.getDefaultToolkit().getImage("src/game/images/test/pic 1.png");
//		ArrayList<Animation> a = h.getGraphicalData().getGraphicalData();
//		Image face2 =  Toolkit.getDefaultToolkit().getImage("src/game/images/test/pic 2.png");
//		Image a1 =  Toolkit.getDefaultToolkit().getImage("src/game/images/test/1.png");
//		Image a2 =  Toolkit.getDefaultToolkit().getImage("src/game/images/test/2.png");
//		Image a3 =  Toolkit.getDefaultToolkit().getImage("src/game/images/test/3.png");
//		Image a4 =  Toolkit.getDefaultToolkit().getImage("src/game/images/test/4.png");
//		Image a5 =  Toolkit.getDefaultToolkit().getImage("src/game/images/test/5.png");
//		Image a6 =  Toolkit.getDefaultToolkit().getImage("src/game/images/test/6.png");
//		Image a7 =  Toolkit.getDefaultToolkit().getImage("src/game/images/test/7.png");
		
		//test animation
//		a = new Animation();
//		a.addScene(a1,200);
//		a.addScene(a2,200);
//		a.addScene(a3,150);
//		a.addScene(a4,200);
//		a.addScene(a5,200);
//		a.addScene(a6,200);
//		a.addScene(a7,200); 
//		
//		sprite = new Sprite(a);
//		sprite.setVelocityOfX(-0.3f);
//		sprite.setVelocityOfY(0.3f);
//		sprite.setX(200);
//		sprite.setY(200);
//		sprite.setToMoveX(200f);
//		sprite.setToMoveY(0f); 
		loaded = true;
	}

	public void stop() {
		running = !running;
	}	
	
	public void exit() {
		exited = true;
	}	
	
	public void currentViewChecker(){
		if(currentPlayer.getCurrentViewAbsX()>=40) {
			currentPlayer.setCurrentViewAbsX(0);
			currentPlayer.setCurrentView(field.getSquare((currentPlayer.getCurrentView().getX()+1), currentPlayer.getCurrentView().getY()));
		}		
		if(currentPlayer.getCurrentViewAbsY()>=40) {
			currentPlayer.setCurrentViewAbsY(0);
			currentPlayer.setCurrentView(field.getSquare(currentPlayer.getCurrentView().getX(), (currentPlayer.getCurrentView().getY()+1)));
		}
		if(currentPlayer.getCurrentViewAbsX()<=-40) {
			currentPlayer.setCurrentViewAbsX(0);
			currentPlayer.setCurrentView(field.getSquare((currentPlayer.getCurrentView().getX()-1), currentPlayer.getCurrentView().getY()));
		}		
		if(currentPlayer.getCurrentViewAbsY()<=-40) {
			currentPlayer.setCurrentViewAbsY(0);
			currentPlayer.setCurrentView(field.getSquare(currentPlayer.getCurrentView().getX(), (currentPlayer.getCurrentView().getY()-1)));
		}
	}
	
	public void movingHeroChecker(){
		int x = currentPlayer.getSelectedHero().getCurrentSquare().getX() - Math.round(screenWidth / 2) - 1;
		int y = currentPlayer.getSelectedHero().getCurrentSquare().getY() - Math.round(screenHeight / 2) - 1;
		
		if(x < 0) {
			x = 0;
		} else if(x > (field.getWidth() - screenWidth - 2)) {
			x = field.getWidth() - screenWidth - 2;
		}
		if(y < 0) {
			y = 0;
		} else if(y > (field.getHeight() - screenHeight - 2)) {
			y = field.getHeight() - screenHeight - 2;
		}
		
		if(currentPlayer.getCurrentView().getX() > (x + 2)) {
			int tempY = currentPlayer.getCurrentView().getY();
			currentPlayer.setCurrentView(field.getSquare(x,tempY));
		}
		if(currentPlayer.getCurrentView().getX() < (x - 2)) {
			int tempY = currentPlayer.getCurrentView().getY();
			currentPlayer.setCurrentView(field.getSquare(x,tempY));
		}
		if(currentPlayer.getCurrentView().getY() > (y + 2)) {
			int tempX = currentPlayer.getCurrentView().getX();
			currentPlayer.setCurrentView(field.getSquare(tempX,y));
		}
		if(currentPlayer.getCurrentView().getY() < (y - 2)) {
			int tempX = currentPlayer.getCurrentView().getX();
			currentPlayer.setCurrentView(field.getSquare(tempX,y));
		}
		
		if(currentPlayer.getCurrentView().getX() < field.getSquare(x, y).getX()) {
			int i = currentPlayer.getCurrentViewAbsX();
			currentPlayer.setCurrentViewAbsX(i + 5);
		} else if(currentPlayer.getCurrentView().getX() > field.getSquare(x, y).getX()) {
			int i = currentPlayer.getCurrentViewAbsX();
			currentPlayer.setCurrentViewAbsX(i - 5);
		} else {}
		if(currentPlayer.getCurrentView().getY() < field.getSquare(x, y).getY()) {
			int i = currentPlayer.getCurrentViewAbsY();
			currentPlayer.setCurrentViewAbsY(i + 5);
		} else if(currentPlayer.getCurrentView().getY() > field.getSquare(x, y).getY()) {
			int i = currentPlayer.getCurrentViewAbsY();
			currentPlayer.setCurrentViewAbsY(i - 5);
		} else {}
		
		if(currentPlayer.getCurrentView() == field.getSquare(x,y)){
			currentPlayer.setCurrentView(field.getSquare(x,y));
		}

	}
	
	public void endedTurn(){
		players.nextPlayer();
		turnEnded = false;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		KeyMap km = new KeyMap(e,mc,1,field);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KeyMap km = new KeyMap(e,mc,2,field);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		KeyMap km = new KeyMap(e,mc,3,field);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		for(int i = 0; i < 280; i += 40) {
			long x = Math.round(e.getLocationOnScreen().getX());
			long y = Math.round(e.getLocationOnScreen().getY());
			int xi = 210 + i;
			int ximax = 210 + i + 39;
			if((x>xi) && (x<ximax) && (y>18) && (y<58) && (i/40 < currentPlayer.getHeroes().size())){
				ArrayList<Hero> heroes = currentPlayer.getHeroes();
				currentPlayer.selectHero(heroes.get(i/40));
				System.out.print("Selected hero" + i/40);
				movingHeroChecker();
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}


	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getLocationOnScreen().getX()<10){
			
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_LEFT);
			} catch (AWTException e1) {
				System.out.print("robot fail");
			}

		}
		if(e.getLocationOnScreen().getX()>(s.getScreenWidth()-10)){
			
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_RIGHT);
			} catch (AWTException e1) {
				System.out.print("robot fail");
			}

		}
		if(e.getLocationOnScreen().getY()<10){
			
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_UP);
			} catch (AWTException e1) {
				System.out.print("robot fail");
			}

		}
		if(e.getLocationOnScreen().getY()>(s.getScreenHeight()-10)){
			
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_DOWN);
			} catch (AWTException e1) {
				System.out.print("robot fail");
			}

		}
	}

	
}




/*	 
   			RenderingHints rh = new RenderingHints(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHints(rh);	
			
			String string = " asd " + currentPlayer.getName() + " asd ";
			
			g.setColor(w.getBackground());
			g.fillRect(0,0,s.getWidth(),s.getHeight());
			g.setColoer(w.getForeground());
			g.drawString(string,x,y); // draws background && string
			
			
 */
