package FiveDimentionChess;

public enum Team {
	
	None(0),
	White(1),
	Black(2);

	private final int id;

	private Team(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public Team getOpponent() {
		if (id == 0) return Team.None;
		else return (id == 1) ? Team.Black : Team.White;
	}

	@Override
	public String toString() {
		if (id == 1) {
			return "W";
		} else if (id == 2) {
			return "B";
		}

		return "-";
	}
}
