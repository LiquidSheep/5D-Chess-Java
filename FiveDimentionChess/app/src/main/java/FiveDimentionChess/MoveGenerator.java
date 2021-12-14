package FiveDimentionChess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveGenerator {
    
    public static List<Move> generateMoves(FiveBoard fBoard, Coord frCoord) {
        Piece piece = fBoard.getPiece(frCoord);
        Type type = piece.getType();

        switch (type) {
            case Pawn:
                return generatePawnMoves(fBoard, frCoord);
            case Knight:
                return generateKnightMoves(fBoard, frCoord);
            case Bishop:
                return generateBishopMoves(fBoard, frCoord);
            case Rook:
                return generateRookMoves(fBoard, frCoord);
            case Queen:
                return generateQueenMoves(fBoard, frCoord);
            case King:
                return generateKingMoves(fBoard, frCoord);
            case Empty:
                return new ArrayList<>();
        }

        return new ArrayList<>();
    }

    private static List<Move> generatePawnMoves(FiveBoard fBoard, Coord frCoord) {
        List<Move> moves = new ArrayList<>();

        int frL = frCoord.getTimeline();
        int frT = frCoord.getTime();
        int frTurn = frCoord.getTurn();
        int frY = frCoord.getY();
        int frX = frCoord.getX();
        Piece piece = fBoard.getPiece(frCoord);
        Team team = piece.getTeam();
        int dir = (team == Team.White) ? -1 : 1;

        // ONE IN FRONT
        // y-axis
        if (fBoard.isEmpty(frL, frT, frTurn, frY + dir, frX)) {
            moves.add(new Move(frCoord, frL, frT, frTurn, frY + dir, frX));
        }
        // l-axis
        if (fBoard.isEmpty(frL + dir, frT, frTurn, frY, frX)) {
            moves.add(new Move(frCoord, frL + dir, frT, frTurn, frY, frX));
        }


        // TWO IN FRONT
        if (frY == ((team == Team.White) ? 6 : 1)) {
            // y-axis
            if (fBoard.isEmpty(frL, frT, frTurn, frY + dir * 2, frX)) {
                moves.add(new Move(frCoord, frL, frT, frTurn, frY + dir * 2, frX));
            }
            // l-axis
            if (fBoard.isEmpty(frL + dir * 2, frT, frTurn, frY, frX)) {
                moves.add(new Move(frCoord, frL + dir * 2, frT, frTurn, frY, frX));
            }
        }


        // CAPTURE
        // y-axis and x-axis
        if (fBoard.isTeamPiece(team.getOpponent(), frL, frT, frTurn, frY + dir, frX - 1)) {
            moves.add(new Move(frCoord, frL, frT, frTurn, frY + dir, frX - 1));
        }
        if (fBoard.isTeamPiece(team.getOpponent(), frL, frT, frTurn, frY + dir, frX + 1)) {
            moves.add(new Move(frCoord, frL, frT, frTurn, frY + dir, frX + 1));
        }
        // y-axis and t-axis
        if (fBoard.isTeamPiece(team.getOpponent(), frL, frT - 1, frTurn, frY + dir, frX)) {
            moves.add(new Move(frCoord, frL, frT - 1, frTurn, frY + dir, frX));
        }
        if (fBoard.isTeamPiece(team.getOpponent(), frL, frT + 1, frTurn, frY + dir, frX)) {
            moves.add(new Move(frCoord, frL, frT + 1, frTurn, frY + dir, frX));
        }
        // l-axis and x-axis
        if (fBoard.isTeamPiece(team.getOpponent(), frL + dir, frT, frTurn, frY, frX - 1)) {
            moves.add(new Move(frCoord, frL + dir, frT, frTurn, frY, frX - 1));
        }
        if (fBoard.isTeamPiece(team.getOpponent(), frL + dir, frT, frTurn, frY, frX + 1)) {
            moves.add(new Move(frCoord, frL + dir, frT, frTurn, frY, frX + 1));
        }
        // l-axis and t-axis
        if (fBoard.isTeamPiece(team.getOpponent(), frL + dir, frT - 1, frTurn, frY, frX)) {
            moves.add(new Move(frCoord, frL + dir, frT - 1, frTurn, frY, frX));
        }
        if (fBoard.isTeamPiece(team.getOpponent(), frL + dir, frT + 1, frTurn, frY, frX)) {
            moves.add(new Move(frCoord, frL + dir, frT + 1, frTurn, frY, frX));
        }


        // EN PASSANT
        int[] ep = fBoard.getBoard(frL, frT, frTurn).ep;
        if (ep[0] != -1 && ep[1] != -1) {
            if (ep[0] == frY + dir) {
                if (ep[1] == frX - 1) {
                    moves.add(new  Move(frCoord, frL, frT, frTurn, frY + dir, frX - 1));
                } else if (ep[1] == frX + 1) {
                    moves.add(new  Move(frCoord, frL, frT, frTurn, frY + dir, frX + 1));
                }
            }
        }

        return moves;
    }

    private static List<Move> generateKnightMoves(FiveBoard fBoard, Coord frCoord) {
        List<Move> moves = new ArrayList<>();

        int frL = frCoord.getTimeline();
        int frT = frCoord.getTime();
        int frTurn = frCoord.getTurn();
        int frY = frCoord.getY();
        int frX = frCoord.getX();
        Piece piece = fBoard.getPiece(frCoord);
        Team team = piece.getTeam();

        List<List<Integer>> axes = new ArrayList<>(){
            {
                add(Arrays.asList(1, 0, 0, 0, 0));
                add(Arrays.asList(0, 1, 0, 0, 0));
                add(Arrays.asList(0, 0, 0, 1, 0));
                add(Arrays.asList(0, 0, 0, 0, 1));
            }
        };

        for (int a = 0; a < axes.size() - 1; a++) {
            List<Integer> axis1 = axes.get(a);
            for (int b = 1; b < axes.size(); b++) {
                List<Integer> axis2 = axes.get(b);
                if (axis1.indexOf(1) == axis2.indexOf(1)) continue;
                for (int i = -1; i < 2; i += 2) {
                    for (int j = -2; j < 3; j += 4) {
                        if (fBoard.canMoveTo(team,
                                        frL + axis1.get(0) * i  + axis2.get(0) * j, 
                                        frT + axis1.get(1) * i + axis2.get(1) * j, 
                                        frTurn + axis1.get(2) * i + axis2.get(2) * j, 
                                        frY + axis1.get(3) * i + axis2.get(3) * j, 
                                        frX + axis1.get(4) * i + axis2.get(4) * j)) {
                            moves.add(new Move(frCoord, frL + axis1.get(0) * i + axis2.get(0) * j, 
                                                        frT + axis1.get(1) * i + axis2.get(1) * j, 
                                                        frTurn + axis1.get(2) * i + axis2.get(2) * j, 
                                                        frY + axis1.get(3) * i + axis2.get(3) * j, 
                                                        frX + axis1.get(4) * i + axis2.get(4) * j));
                        }
                        if (fBoard.canMoveTo(team,
                                        frL + axis1.get(0) * j  + axis2.get(0) * i, 
                                        frT + axis1.get(1) * j + axis2.get(1) * i, 
                                        frTurn + axis1.get(2) * j + axis2.get(2) * i, 
                                        frY + axis1.get(3) * j + axis2.get(3) * i, 
                                        frX + axis1.get(4) * j + axis2.get(4) * i)) {
                            moves.add(new Move(frCoord, frL + axis1.get(0) * j + axis2.get(0) * i, 
                                                        frT + axis1.get(1) * j + axis2.get(1) * i, 
                                                        frTurn + axis1.get(2) * j + axis2.get(2) * i, 
                                                        frY + axis1.get(3) * j + axis2.get(3) * i, 
                                                        frX + axis1.get(4) * j + axis2.get(4) * i));
                        }
                    }
                }
            }
        }

        return moves;
    }

    private static List<Move> generateBishopMoves(FiveBoard fBoard, Coord frCoord) {
        List<Move> moves = new ArrayList<>();

        int frL = frCoord.getTimeline();
        int frT = frCoord.getTime();
        int frTurn = frCoord.getTurn();
        int frY = frCoord.getY();
        int frX = frCoord.getX();
        Piece piece = fBoard.getPiece(frCoord);
        Team team = piece.getTeam();

        List<List<Integer>> axes = new ArrayList<>(){
            {
                add(Arrays.asList(1, 0, 0, 0, 0));
                add(Arrays.asList(0, 1, 0, 0, 0));
                add(Arrays.asList(0, 0, 0, 1, 0));
                add(Arrays.asList(0, 0, 0, 0, 1));
            }
        };

        for (int a = 0; a < axes.size() - 1; a++) {
            List<Integer> axis1 = axes.get(a);
            for (int b = 1; b < axes.size(); b++) {
                List<Integer> axis2 = axes.get(b);
                if (axis1.indexOf(1) == axis2.indexOf(1)) continue;
                for (int i = -1; i < 2; i += 2) {
                    for (int j = -1; j < 2; j += 2) {
                        for (int k = 1; ; k++) {
                            Coord toCoord = new Coord(frL + axis1.get(0) * i * k + axis2.get(0) * j * k, 
                                                    frT + axis1.get(1) * i * k + axis2.get(1) * j * k, 
                                                    frTurn + axis1.get(2) * i * k + axis2.get(2) * j * k, 
                                                    frY + axis1.get(3) * i * k + axis2.get(3) * j * k, 
                                                    frX + axis1.get(4) * i * k + axis2.get(4) * j * k);

                            if (toCoord.getTimeline() < fBoard.getTimelineTop()
                                || toCoord.getTimeline() > fBoard.getTimelineBottom()) break;
                            if (toCoord.getTime() < 0 || toCoord.getTime() > fBoard.getTimeRight()) break;
                            if (toCoord.getY() < 0 || toCoord.getY() > 7) break;
                            if (toCoord.getX() < 0 || toCoord.getX() > 7) break;
                            if (fBoard.isTeamPiece(team, toCoord)) break;

                            if (fBoard.canMoveTo(team, toCoord)) {
                                moves.add(new Move(frCoord, toCoord));
                            }

                            if (fBoard.isTeamPiece(team.getOpponent(), toCoord)) break;
                        }
                    }
                }
            }
        }

        return moves;
    }

    private static List<Move> generateRookMoves(FiveBoard fBoard, Coord frCoord) {
        List<Move> moves = new ArrayList<>();

        int frL = frCoord.getTimeline();
        int frT = frCoord.getTime();
        int frTurn = frCoord.getTurn();
        int frY = frCoord.getY();
        int frX = frCoord.getX();
        Piece piece = fBoard.getPiece(frCoord);
        Team team = piece.getTeam();

        List<List<Integer>> axes = new ArrayList<>(){
            {
                add(Arrays.asList(1, 0, 0, 0, 0));
                add(Arrays.asList(0, 1, 0, 0, 0));
                add(Arrays.asList(0, 0, 0, 1, 0));
                add(Arrays.asList(0, 0, 0, 0, 1));
            }
        };

        for (int a = 0; a < axes.size(); a++) {
            List<Integer> axis1 = axes.get(a);
            for (int i = -1; i < 2; i += 2) {
                for (int k = 1; ; k++) {
                    Coord toCoord = new Coord(frL + axis1.get(0) * i * k, 
                                            frT + axis1.get(1) * i * k, 
                                            frTurn + axis1.get(2) * i * k, 
                                            frY + axis1.get(3) * i * k, 
                                            frX + axis1.get(4) * i * k);

                    if (toCoord.getTimeline() < fBoard.getTimelineTop()
                        || toCoord.getTimeline() > fBoard.getTimelineBottom()) break;
                    if (toCoord.getTime() < 0 || toCoord.getTime() > fBoard.getTimeRight()) break;
                    if (toCoord.getY() < 0 || toCoord.getY() > 7) break;
                    if (toCoord.getX() < 0 || toCoord.getX() > 7) break;
                    if (fBoard.isTeamPiece(team, toCoord)) break;

                    if (fBoard.canMoveTo(team, toCoord)) {
                        moves.add(new Move(frCoord, toCoord));
                    }

                    if (fBoard.isTeamPiece(team.getOpponent(), toCoord)) break;
                }
            }
        }

        return moves;
    }

    private static List<Move> generateQueenMoves(FiveBoard fBoard, Coord frCoord) {
        List<Move> moves = new ArrayList<>();

        for (Move m : generateBishopMoves(fBoard, frCoord)) {
            moves.add(m);
        }

        for (Move m : generateRookMoves(fBoard, frCoord)) {
            moves.add(m);
        }

        return moves;
    }

    private static List<Move> generateKingMoves(FiveBoard fBoard, Coord frCoord) {
        List<Move> moves = new ArrayList<>();

        int frL = frCoord.getTimeline();
        int frT = frCoord.getTime();
        int frTurn = frCoord.getTurn();
        int frY = frCoord.getY();
        int frX = frCoord.getX();
        Piece piece = fBoard.getPiece(frCoord);
        Team team = piece.getTeam();

        List<List<Integer>> axes = new ArrayList<>(){
            {
                add(Arrays.asList(1, 0, 0, 0, 0));
                add(Arrays.asList(0, 1, 0, 0, 0));
                add(Arrays.asList(0, 0, 0, 1, 0));
                add(Arrays.asList(0, 0, 0, 0, 1));
            }
        };

        for (int a = 0; a < axes.size(); a++) {
            List<Integer> axis1 = axes.get(a);
            for (int i = -1; i < 2; i += 2) {
                Coord toCoord = new Coord(frL + axis1.get(0) * i, 
                                        frT + axis1.get(1) * i, 
                                        frTurn + axis1.get(2) * i, 
                                        frY + axis1.get(3) * i, 
                                        frX + axis1.get(4) * i);

                if (toCoord.getTimeline() < fBoard.getTimelineTop()
                    || toCoord.getTimeline() > fBoard.getTimelineBottom()) break;
                if (toCoord.getTime() < 0 || toCoord.getTime() > fBoard.getTimeRight()) break;
                if (toCoord.getY() < 0 || toCoord.getY() > 7) break;
                if (toCoord.getX() < 0 || toCoord.getX() > 7) break;
                if (fBoard.isTeamPiece(team, toCoord)) break;

                if (fBoard.canMoveTo(team, toCoord)) {
                    moves.add(new Move(frCoord, toCoord));
                }

                if (fBoard.isTeamPiece(team.getOpponent(), toCoord)) break;
            }
        }

        for (int a = 0; a < axes.size() - 1; a++) {
            List<Integer> axis1 = axes.get(a);
            for (int b = 1; b < axes.size(); b++) {
                List<Integer> axis2 = axes.get(b);
                if (axis1.indexOf(1) == axis2.indexOf(1)) continue;
                for (int i = -1; i < 2; i += 2) {
                    for (int j = -1; j < 2; j += 2) {
                        Coord toCoord = new Coord(frL + axis1.get(0) * i + axis2.get(0) * j, 
                                                frT + axis1.get(1) * i + axis2.get(1) * j, 
                                                frTurn + axis1.get(2) * i + axis2.get(2) * j, 
                                                frY + axis1.get(3) * i + axis2.get(3) * j, 
                                                frX + axis1.get(4) * i + axis2.get(4) * j);

                        if (toCoord.getTimeline() < fBoard.getTimelineTop()
                            || toCoord.getTimeline() > fBoard.getTimelineBottom()) break;
                        if (toCoord.getTime() < 0 || toCoord.getTime() > fBoard.getTimeRight()) break;
                        if (toCoord.getY() < 0 || toCoord.getY() > 7) break;
                        if (toCoord.getX() < 0 || toCoord.getX() > 7) break;
                        if (fBoard.isTeamPiece(team, toCoord)) break;

                        if (fBoard.canMoveTo(team, toCoord)) {
                            moves.add(new Move(frCoord, toCoord));
                        }

                        if (fBoard.isTeamPiece(team.getOpponent(), toCoord)) break;
                    }
                }
            }
        }

        // CASTLING
        boolean[] castling = fBoard.getBoard(frL, frT, frTurn).castling;
        if (castling[0]) {
            moves.add(new Move(frCoord, frL, frT, frTurn, 7, 6));
        }
        if (castling[1]) {
            moves.add(new Move(frCoord, frL, frT, frTurn, 7, 2));
        }
        if (castling[2]) {
            moves.add(new Move(frCoord, frL, frT, frTurn, 0, 6));
        }
        if (castling[3]) {
            moves.add(new Move(frCoord, frL, frT, frTurn, 0, 2));
        }

        return moves;
    }
}
