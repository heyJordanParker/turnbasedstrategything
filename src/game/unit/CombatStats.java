package game.unit;

public class CombatStats {

	private double strenght; //defines damage and 0.5x defense
	private double agility;  // defines ranged damage
	private double stamina;  // defines health and 0.5x defense
	private double intelligence; // defines mana and spell damage 
	private double defense;
	private double speed;
	private double initiative;
	private double morale; // from items , building , spells
	private double luck;  // from items , buildings , spells
	private double health;
	private double mana;
	
	public void setStrenght(double strenght) {
		this.strenght = strenght;
	}
	
	public double getStrenght() {
		return strenght;
	}
	
	public void setAgility(double agility) {
		this.agility = agility;
	}
	
	public double getAgility() {
		return agility;
	}
	
	public void setStamina(double stamina) {
		this.stamina = stamina;
	}
	
	public double getStamina() {
		return stamina;
	}
	
	public void setIntelligence(double intelligence) {
		this.intelligence = intelligence;
	}
	
	public double getIntelligence() {
		return intelligence;
	}
	
	public void setDefense(double defense) {
		this.defense = defense;
	}

	public double getDefense() {
		return defense;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}

	public void setInitiative(double initiative) {
		this.initiative = initiative;
	}

	public double getInitiative() {
		return initiative;
	}

	public void setMorale(double morale) {
		this.morale = morale;
	}

	public double getMorale() {
		return morale;
	}

	public void setLuck(double luck) {
		this.luck = luck;
	}

	public double getLuck() {
		return luck;
	}

	public void setHealth(double health) {
		this.health = health;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setMana(double mana) {
		this.mana = mana;
	}
	
	public double getMana() {
		return mana;
	}
	
}
