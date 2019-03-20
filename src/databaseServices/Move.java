package databaseServices;

public class Move {
	public int moveID;
	public String name;
	public String description;
	public int power;
	public String type;
	public int pp;
	public int accuracy;

	public Move(int id, String name, String description, int power, String type, int pp, int accuracy) {
		this.moveID = id;
		this.name = name;
		this.description = description;
		this.power = power;
		this.type = type;
		this.pp = pp;
		this.accuracy = accuracy;
	}

	public String getValue(String propertyName) {
		switch (propertyName) {
		case "moveID":
			return this.moveID + "";
		case "name":
			return this.name;
		case "description":
			return this.description;
		case "power":
			return this.power + "";
		case "type":
			return this.type;
		case "pp":
			return this.pp + "";
		case "accuracy":
			return this.accuracy + "";
		default:
			break;
		}
		return null;
	}
}
