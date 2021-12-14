package FiveDimentionChess;

public class NotationParser {

    public static String standardFen 
        = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    public static String standardFen2 
        = "rnbqkbnr/pppppppp/8/8/4Q3/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    public static FiveBoard fullParse(String notation) {

        String[] notationSplit = notation.split("\r\n\r\n");
        String header = notationSplit[0];
        String body = notationSplit[1];

        for (String notationByTurn : body.split("\n")) {
            String[] notationByTurnStrings = notationByTurn.split(". ");
            int turnNum = Integer.parseInt(notationByTurnStrings[0]);
            for (String notationBySubTurn : notationByTurnStrings[1].split("/ ")) {
                boolean isComment = false;
                for (String notationByMove : notationBySubTurn.split(" ")) {
                    if (notationByMove.charAt(0) == '{') {
                        isComment = true;
                    }
                    if (isComment) {
                        if (notationByMove.charAt(notationByMove.length() - 1) == '}') {
                            isComment = false;
                        }
                        continue;
                    }
                    
                }
            }
        }

        return null;
    }

    public static Board parse(String fen) {
        String[] fenList = fen.split(" ");

        Piece[][] state;
        Team turn;
        boolean[] castling = new boolean[4];
        int[] ep = new int[2];
        int ply;
        int moveNum;

        state = positionPieces(fenList[0]);

        turn = fenList[1].equals("w") ? Team.White : Team.Black;
        
        castling[0] = fenList[2].contains("K");
        castling[1] = fenList[2].contains("Q");
        castling[2] = fenList[2].contains("k");
        castling[3] = fenList[2].contains("q");

        if (fenList[3].length() == 1) {
            ep[0] = -1;
            ep[1] = -1;
        } else {
            ep[0] = 7 - (int) fenList[3].charAt(1) + 49;
            ep[1] = (int) fenList[3].charAt(0) - 97;
        }

        ply = Integer.parseInt(fenList[4]);

        moveNum = Integer.parseInt(fenList[5]);

        return new Board(state, turn, castling, ep, ply, moveNum);
    }

    public static String deparse(GameState gameState) {

        return null;
    }

    public static String deparse(Board board) {

        return null;
    }

    private static Piece[][] positionPieces(String pieces) {
        Piece[][] state = new Piece[Board.COUNT_Y][Board.COUNT_X];
        String[] pieceByY = pieces.split("/");

        for (int y = 0; y < Board.COUNT_Y; y++) {
            String pieceByX = pieceByY[y];
            int offset = 0;
            for (int x = 0; x < Board.COUNT_X; x++) {
                Team team = Team.None;
                Type type = Type.Empty;

                char pieceChar = pieceByX.charAt(x - offset);
                byte pieceCharByte = (byte) pieceChar;

                if (pieceCharByte >= 48 && pieceCharByte < 58) {
                    for (int i = 0; i < (int) pieceCharByte - 48; i++) {
                        state[y][x + i] = new Piece();
                    }
                    x += (int) pieceCharByte - 49;
                    offset += (int) pieceCharByte - 49;
                    continue;
                } else if (pieceCharByte >= 65 && pieceCharByte < 91) {
                    team = Team.White;
                } else if (pieceCharByte >= 97 && pieceCharByte < 123) {
                    team = Team.Black;
                }

                switch (pieceChar) {
                case 'P':
                case 'p':
                  type = Type.Pawn;
                  break;
                case 'R':
                case 'r':
                  type = Type.Rook;
                  break;
                case 'N':
                case 'n':
                  type = Type.Knight;
                  break;
                case 'B':
                case 'b':
                  type = Type.Bishop;
                  break;
                case 'Q':
                case 'q':
                  type = Type.Queen;
                  break;
                case 'K':
                case 'k':
                  type = Type.King;
                  break;
                }

                state[y][x] = new Piece(team, type);
            }
        }

        return state;
    }
}
