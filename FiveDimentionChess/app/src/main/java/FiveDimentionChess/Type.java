package FiveDimentionChess;

public enum Type {
	
	Empty(0),
	Pawn(1),
	Knight(2),
	Bishop(3),
	Rook(4),
	Queen(5),
	King(6);

	private final int id;

	private Type(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		switch (id) {
			case 0:
				return "-";
			case 1:
				return "P";
			case 2:
				return "N";
			case 3:
				return "B";
			case 4:
				return "R";
			case 5:
				return "Q";
			case 6:
				return "K";
		}

		return "-";
	}
}
