package FiveDimentionChess;

public class Piece {
	
	private Team team;
	private Type type;
	public int imagePosition;

	public Piece(Team team, Type type) {
		this.team = team;
		this.type = type;
		switch (type) {
			case Knight:
				imagePosition = 960;
				break;
			case Bishop:
				imagePosition = 640;
				break;
			case Rook:
				imagePosition = 1280;
				break;
			default:
				imagePosition = (6 - type.getId()) * 320;
				break;
			
		}
	}

	public Piece() {
		team = Team.None;
		type = Type.Empty;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return team.toString() + type.toString();
	}
}
