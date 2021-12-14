package FiveDimentionChess;

import java.util.ArrayList;
import java.util.List;

public class FiveBoard {
    
    private List<List<Board[]>> state;
    private int blackSideTimeline;

    public FiveBoard() {
        Board initBoard = NotationParser.parse(NotationParser.standardFen);
        state = new ArrayList<>();
        for (int l = 0; l < 1; l++) {
            state.add(new ArrayList<>());
            for (int t = 0; t < 1; t++) {
                state.get(l).add(new Board[2]);
                for (int turn = 0; turn < 1; turn++) {
                    state.get(l).get(t)[turn] = initBoard;
                }
            }
        }
        blackSideTimeline = 0;
    }

    public FiveBoard(Board board) {

    }

    public void setBLlackSideTimeLine(int i) {
        blackSideTimeline = i;
    }

    public int getTimelineTop() {
        return -blackSideTimeline;
    }

    public int getTimelineBottom() {
        return state.size() - blackSideTimeline - 1;
    }

    public int getTimeRight() {
        int timeRight = 0;
        for (List<Board[]> s : state) {
            if (s.size() - 1 > timeRight) timeRight = s.size() - 1;
        }

        return timeRight;
    }

    public Board getBoard(int l, int t, int turn) {
        if (l + blackSideTimeline < 0 || l + blackSideTimeline >= state.size()
            || t < 0 || t >= state.get(l + blackSideTimeline).size() || turn < 0 || turn >=2)
            return null;
        return state.get(l + blackSideTimeline).get(t)[turn];
    }

    public void setBoard(Board board, int l, int t, int turn) {
        if (l + blackSideTimeline == -1) {
            state.add(0, new ArrayList<>());
            blackSideTimeline++;
        } else if (l + blackSideTimeline == state.size()) {
            state.add(new ArrayList<>());
        }
        if (t >= state.get(l + blackSideTimeline).size()) {
            int tSize = state.get(l + blackSideTimeline).size();
            for (int i = 0; i < t - tSize + 1; i++) {
                state.get(l + blackSideTimeline).add(new Board[2]);
            }
        }
        state.get(l + blackSideTimeline).get(t)[turn] = board;
    }

    public void setBoard(Board board, Coord coord) {
        setBoard(board, coord.getTimeline(), coord.getTime(), coord.getTurn());
    }

    public Piece getPiece(int l, int t, int turn, int y, int x) {
        Board board = getBoard(l, t, turn);
        if (board == null) return null;
        return board.getPiece(y, x);
    }

    public Piece getPiece(Coord coord) {
        return getPiece(coord.getTimeline(), coord.getTime(), coord.getTurn(), 
                 coord.getY(), coord.getX());
    }

    public void setPiece(Piece piece, int l, int t, int turn, int y, int x) {
        Board board = getBoard(l, t, turn);
        if (board == null) return;
        board.setPiece(piece, y, x);
    }

    public void setPiece(Piece piece, Coord coord) {
        setPiece(piece, coord.getTimeline(), coord.getTime(), 
                 coord.getTurn(), coord.getY(), coord.getX());
    }

    public boolean isEmpty(int l, int t, int turn, int y, int x) {
        Piece piece = getPiece(l, t, turn, y, x);
        if (piece == null) return false;
        return piece.getType() == Type.Empty;
    }

    public boolean isEmpty(Coord coord) {
        return isEmpty(coord);
    }

    public boolean isTeamPiece(Team team, int l, int t, int turn, int y, int x) {
        Piece piece = getPiece(l, t, turn, y, x);
        if (piece == null) return false;
        return piece.getTeam() == team;
    }

    public boolean isTeamPiece(Team team, Coord coord) {
        Piece piece = getPiece(coord);
        if (piece == null) return false;
        return piece.getTeam() == team;
    }

    public boolean canMoveTo(Team team, int l, int t, int turn, int y, int x) {
        return isEmpty(l, t, turn, y, x) || isTeamPiece(team.getOpponent(), l, t, turn, y, x);
    }

    public boolean canMoveTo(Team team, Coord coord) {
        return canMoveTo(team, coord.getTimeline(), coord.getTime(), coord.getTurn(), coord.getY(), coord.getX());
    }
    
    public void movePiece(Move move) {
        Coord frCoord = move.getFr();
        System.out.println(move.toString());

        if (move.isPhysical()) {
            Board toBoard = getBoard(frCoord.getTimeline(), frCoord.getTime(), frCoord.getTurn()).clone();

            toBoard.movePiece(move);
            setBoard(toBoard, frCoord.getTimeline(), 
                     (frCoord.getTurn() == 0) ? frCoord.getTime() : frCoord.getTime() + 1, 
                     (frCoord.getTurn() + 1) % 2);
        } else {
            Piece piece = getPiece(frCoord);
            Coord toCoord = move.getTo();
            Board frBoard = getBoard(frCoord.getTimeline(), frCoord.getTime(), frCoord.getTurn());
            Board toBoard = getBoard(toCoord.getTimeline(), toCoord.getTime(), toCoord.getTurn()).clone();

            frBoard.setPiece(new Piece(), frCoord.getY(), frCoord.getX());
            toBoard.setPiece(piece, toCoord.getY(), toCoord.getX());
            setBoard(toBoard, (frCoord.getTurn() == 0) ? getTimelineBottom() + 1 : getTimelineTop() - 1, 
                     (frCoord.getTurn() == 0) ? toCoord.getTime() : toCoord.getTime() + 1, 
                     (frCoord.getTurn() + 1) % 2);
        }
    }
}
