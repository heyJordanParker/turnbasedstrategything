package game.item;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Item {

	private Image image;
	private String type;
	private String name;
	private int cost;
	private ArrayList<String> bonuses;
	
	public Item(String type, Image img){
		setCost(2000);
		name = new String("?");
		this.type = type;
		this.image = img;
		if(type == null){
			type = "armor";
		}
		if(image == null){
			image = Toolkit.getDefaultToolkit().getImage("Images/items/unknown_item.jpg");
		}
		bonuses = new ArrayList<String>();
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCost() {
		return cost;
	}

	public void setBonuses(ArrayList<String> bonuses) {
		this.bonuses = bonuses;
	}

	public ArrayList<String> getBonuses() {
		return bonuses;
	}

}
