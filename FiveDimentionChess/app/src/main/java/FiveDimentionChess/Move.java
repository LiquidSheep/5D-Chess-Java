package FiveDimentionChess;

public class Move {
    
    private Coord frCoord;
    private Coord toCoord;
    private boolean isPhysical;

    public Move(Coord frCoord, Coord toCoord) {
        this.frCoord = frCoord;
        this.toCoord = toCoord;
        
        if (frCoord.isSameBoard(toCoord)) {
            isPhysical = true;
        } else {
            isPhysical = false;
        }
    }

    public Move(Coord frCoord, int toL, int toT, int toTurn, int toY, int toX) {
        this.frCoord = frCoord;
        this.toCoord = new Coord(toL, toT, toTurn, toY, toX);
    }

    public Coord getFr() {
        return frCoord;
    }

    public Coord getTo() {
        return toCoord;
    }

    public boolean isPhysical() {
        return isPhysical;
    }

    @Override
    public String toString() {
        return frCoord.toString() + " => " + toCoord.toString();
    }
}
