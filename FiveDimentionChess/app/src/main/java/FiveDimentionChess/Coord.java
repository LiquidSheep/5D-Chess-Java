package FiveDimentionChess;

public class Coord {
	
	private int l;
	private int t;
	private int turn;
	private int y;
	private int x;

	public Coord(int l, int t, int turn, int y, int x) {
		this.l = l;
		this.t = t;
		this.turn = turn;
		this.y = y;
		this.x = x;
	}

	public int getTimeline() {
		return l;
	}

	public void setTimeline(int l) {
		this.l = l;
	}

	public int getTime() {
		return t;
	}

	public void setTime(int t) {
		this.t = t;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public static char intToFile(int file) {
		return (char) (file + 97);
	}

	public boolean isSameBoard(Coord coord) {
		return l == coord.getTimeline() && t == coord.getTime() && turn == coord.getTurn();
	}

	@Override
	public String toString() {
		return "(" + l + "T" + t + ")" + intToFile(x) + (8 - y);
	}

	@Override
	public int hashCode() {
		int result = 17;
		final int prime = 31;

		result = prime * result + Integer.hashCode(l);
		result = prime * result + Integer.hashCode(t);
		result = prime * result + Integer.hashCode(turn);
		result = prime * result + Integer.hashCode(y);
		result = prime * result + Integer.hashCode(x);

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof Coord) {
			Coord a = (Coord) obj;
			if (l == a.l && t == a.t && turn == a.turn
				&& y == a.y && x == a.x) {
					return true;
			}
			return false;
		}

		return false;
	}
}
