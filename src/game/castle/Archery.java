package game.castle;

import java.awt.Image;
import java.awt.Toolkit;

@SuppressWarnings("unused")
public class Archery extends CastleBuilding {
	
	public Archery(Castle owner) {
		super(owner);
		x = 66;
		y = 413;
		w = 140;
		h = 67;
		goldcost = 1500;
		woodcost = 10;
		stonecost = 5;
		image = Toolkit.getDefaultToolkit().getImage("Images/castle/Archery.png");
		built = true;
	}
	
	@Override
	public void update() {
	}
	
}
