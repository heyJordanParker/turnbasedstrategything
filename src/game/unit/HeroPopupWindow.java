package game.unit;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import game.Interface.PopupWindow;
import game.main.MainClass;

public class HeroPopupWindow extends PopupWindow {
	
	private Hero hero;
	private int invIndex;

	public HeroPopupWindow(MainClass mc, Hero hero) {
		super(mc);
		invIndex = 0;
		this.hero = hero;
		this.newChoice(" ","close", 700, 500, Toolkit.getDefaultToolkit().getImage("Buttons/ok/button_ok.jpg"), Toolkit.getDefaultToolkit().getImage("Buttons/ok/button_ok_pressed.jpg"), Toolkit.getDefaultToolkit().getImage("Buttons/ok/button_ok_hovered.jpg"));
		Image img = Toolkit.getDefaultToolkit().getImage("Images/heroes/hero.jpg").getScaledInstance(60, 60, 1);
		this.newImg(img, 33, 33);
		img = hero.getIcon().getScaledInstance(56, 56, 1);
		this.newImg(img, 35, 35);
		String s = hero.getName();
		this.newText(s, 100, 50);
		s = new String("XP : " + hero.getExperience().x + " / " + hero.getExperience().y);
		this.newText(s, 100, 80);
	}
	
	public void draw(Graphics g){
		super.draw(g);
		drawInventory(g);
	}
	
	public void drawInventory(Graphics g){
		g.drawImage(Toolkit.getDefaultToolkit().getImage("Buttons/inventory/left.jpg"), 1, 1, null);
		for(int i = invIndex; i < (invIndex + 8);i++){
			if(hero.getInventory().get(i) != null){
				g.drawImage(hero.getInventory().get(i).getImage(),40 + i*40, 400, null);
			} else {
				g.drawImage(Toolkit.getDefaultToolkit().getImage("Buttons/inventory/default.jpg"),40 + i*40, 400, null);
			}
		}
	}
	
}
