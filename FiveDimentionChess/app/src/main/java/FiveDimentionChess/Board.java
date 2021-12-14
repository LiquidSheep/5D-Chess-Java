package FiveDimentionChess;

public class Board {
    
    public static final int COUNT_X = 8;
    public static final int COUNT_Y = 8;

    private Piece[][] state;
    private Team turn;
    public boolean[] castling;
    public int[] ep;
    private int ply;
    private int moveNum;

    public Board(Piece[][] state, Team turn,
                 boolean[] castling, int[] ep, int ply, int moveNum) {
        this.state = state;
        this.turn = turn;
        this.castling = castling;
        this.ep = ep;
        this.ply = ply;
        this.moveNum = moveNum;
    }

    @Override
    public Board clone() {

        Piece[][] stateCopy = new Piece[COUNT_Y][COUNT_X];
        for (int y = 0; y < stateCopy.length; y++) {
            for (int x = 0; x < stateCopy[y].length; x++) {
                stateCopy[y][x] = new Piece(state[y][x].getTeam(), state[y][x].getType());
            }
        }

        boolean[] castlingCopy = new boolean[4];
        for (int i = 0; i < castlingCopy.length; i++) {
            castlingCopy[i] = castling[i];
        }

        int[] epCopy = new int[2];
        for (int i = 0; i < epCopy.length; i++) {
            epCopy[i] = ep[i];
        }

        return new Board(stateCopy, turn, castlingCopy, epCopy, ply, moveNum);
    }

    public Piece getPiece(int y, int x) {
        if (y < 0 || y >= COUNT_Y || x < 0 || x >= COUNT_X) return null;
        return state[y][x];
    }

    public void setPiece(Piece piece, int y, int x) {
        if (y < 0 || y >= COUNT_Y || x < 0 || x >= COUNT_X) return;
        state[y][x] = piece;
    }

    public void movePiece(Move move) {
        if (move.isPhysical()) {
            Coord frCoord = move.getFr();
            Coord toCoord = move.getTo();
            Piece piece = getPiece(frCoord.getY(), frCoord.getX());

            setPiece(new Piece(), frCoord.getY(), frCoord.getX());
            setPiece(piece, toCoord.getY(), toCoord.getX());
        }
    }

    @Override
    public String toString() {
        String s = "";
        s += "    a    b    c    d    e    f    g    h \n";
        s += "  ----------------------------------------\n";
        for (int y = 0; y < 8; y++) {
            for ( int x = 0; x < 8; x++) {
                if (x == 0) s += "" + (8 - y) + " |";
                if (state[y][x].getType() == Type.Empty) {
                    s += "    |";
                    continue;
                }
                s += " " + state[y][x].toString() + " |";
            }
            s += " " + (8 - y) + "\n";
            s += "  ----------------------------------------\n";
        }
        s += "    a    b    c    d    e    f    g    h \n";
        return s;
    }
}
