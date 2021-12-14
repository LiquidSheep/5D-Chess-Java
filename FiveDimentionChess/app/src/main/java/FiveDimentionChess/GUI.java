package FiveDimentionChess;

import java.util.ArrayList;
import java.util.List;

import processing.core.*;
import processing.event.MouseEvent;

public class GUI extends PApplet {

	private float boardSize = 320;
	private PImage pieceSprite;
	private PGraphics fBoardGraphics;
	private PVector fBoardPos;
	private float fBoardScale;
	private PVector mousePressedOffset;
	private boolean posLocked;

	private GameState gameState;
	private FiveBoard fBoard;
	private Coord selectedCoord;
	private List<Coord> possibleMoveCoords;

	public void settings() {
		//size(1600, 960);
		fullScreen();
		pieceSprite = loadImage("D:/Program Folder/5DChess/Java/FiveDimentionChess/app/src/main/resources/FiveDimentionChess/img/Pieces.png");
		gameState = new GameState();
		fBoard = gameState.getFiveBoard();
		possibleMoveCoords = new ArrayList<>();
	}

	public void setup() {
		fBoardGraphics = createGraphics(width * 5, height * 3);
		fBoardPos = new PVector(-width / 2, -boardSize / 2);
		fBoardScale = 1;
		mousePressedOffset = new PVector();
		posLocked = false;
		selectedCoord = new Coord(0, -1, 0, 0, 0);
		drawFiveBoard();
	}

	public void draw() {
		background(127);
		mouseControl();
		translate(width / 2, height / 2);
		scale(fBoardScale);
		image(fBoardGraphics, fBoardPos.x, 
				fBoardPos.y + fBoard.getTimelineTop() * boardSize);
		//drawGameState();
	}

	private void drawGameState() {
		
	}

	private void drawFiveBoard() {
		int lSize = fBoard.getTimelineBottom() - fBoard.getTimelineTop() + 1;
		int tSize = fBoard.getTimeRight() + 1;
		int turnSize = 2;
		fBoardGraphics.beginDraw();
		fBoardGraphics.background(127);
		for (int l = 0; l < lSize; l++) {
			for (int t = 0; t < tSize; t++) {
				for (int turn = 0; turn < turnSize; turn++) {
					Board board = fBoard.getBoard(l + fBoard.getTimelineTop(), t, turn);
					if (board == null) continue;
					if (turn == 0) fBoardGraphics.fill(255);
					else fBoardGraphics.fill(0);
					fBoardGraphics.noStroke();
					fBoardGraphics.rect(boardSize * (t * 2 + turn) + boardSize * 0.05f, 
										boardSize * l + boardSize * 0.05f, 
										boardSize * 0.9f, 
										boardSize * 0.9f);
					List<int[]> possibleTile = new ArrayList<>();
					for (Coord c : possibleMoveCoords) {
						if (c.getTimeline() == l + fBoard.getTimelineTop() && c.getTime() == t && c.getTurn() == turn) {
							possibleTile.add(new int[] {c.getY(), c.getX()});
						}
					}
					drawBoard(board, 
							  boardSize * (t * 2 + turn),
							  boardSize * l,
							  boardSize, 
							  boardSize,
							  selectedCoord.getTimeline() == l + fBoard.getTimelineTop() 
							  && selectedCoord.getTime() == t
							  && selectedCoord.getTurn() == turn,
							  possibleTile);
				}
			}
		}
		fBoardGraphics.endDraw();
	}

	private void drawBoard(Board board, float x, float y, float w, float h, boolean drawSelectedTile, List<int[]> possibleTile) {
		float tileSize = w / 10;
		for (int tx = 0; tx < 8; tx++) {
			for (int ty = 0; ty < 8; ty++) {
				if (abs(tx - ty) % 2 == 0) fBoardGraphics.fill(245, 222, 179);
				else fBoardGraphics.fill(222, 184, 135);
				if (drawSelectedTile && selectedCoord.getX() == (int)tx && selectedCoord.getY() == (int)ty) 
					fBoardGraphics.fill(154, 205, 50);
				fBoardGraphics.noStroke();
				drawTile(x + tileSize * (tx + 1), y + tileSize * (ty + 1), tileSize, tileSize);
				drawPiece(board.getPiece(ty, tx), x + tileSize * (tx + 1), y + tileSize * (ty + 1), tileSize, tileSize);
				for (int[] tile : possibleTile) {
					if (tile[0] == ty && tile[1] == tx) {
						drawCanMove(x + tileSize * (tx + 1), y + tileSize * (ty + 1), tileSize, tileSize);
					}
				}
			}
		}
	}

	private void drawTile(float x, float y, float w, float h) {
		fBoardGraphics.rect(x, y, w, h);
	}

	private void drawPiece(Piece piece, float x, float y, float w, float h) {
		PImage pieceImage = pieceSprite.get(piece.imagePosition, (piece.getTeam().getId() - 1) * 320, 320, 320);
		pieceImage.resize((int) w, (int) h);
		fBoardGraphics.image(pieceImage, x, y);
	}

	private void drawCanMove(float x, float y, float w, float h) {
		fBoardGraphics.fill(154, 205, 50, 192);
		fBoardGraphics.ellipse(x + w/ 2, y + h / 2, w / 2, h / 2);
	}

	public void mouseControl() {
		if (mousePressed && mouseButton == RIGHT) {
			if (!posLocked) {
				mousePressedOffset = PVector.sub(new PVector(mouseX, mouseY), fBoardPos);
				posLocked = true;
			}
			fBoardPos = PVector.sub(new PVector(mouseX, mouseY), mousePressedOffset);
		} else {
			posLocked = false;
		}
	}

	public void mouseWheel(MouseEvent e) {
		if (e.getCount() > 0) {
			fBoardScale -= 0.1;
		} else if (e.getCount() < 0) {
			fBoardScale += 0.1;
		}
	}

	public void mousePressed() {

		if (mouseButton == LEFT) {
			int lSize = fBoard.getTimelineBottom() - fBoard.getTimelineTop() + 1;
			int tSize = fBoard.getTimeRight() + 1;
			int turnSize = 2;
			for (int l = 0; l < lSize; l++) {
				for (int t = 0; t < tSize; t++) {
					for (int turn = 0; turn < turnSize; turn++) {
						Board board = fBoard.getBoard(l + fBoard.getTimelineTop(), t, turn);
						if (board == null) continue;
						float x = boardSize * (t * 2 + turn);
						float y = boardSize * l;
						float tileSize = boardSize / 10;
						for (int tx = 0; tx < 8; tx++) {
							for (int ty = 0; ty < 8; ty++) {
								if (mouseX - fBoardPos.x - width / 2 >= x + tileSize * (tx + 1) 
									&& mouseX - fBoardPos.x - width / 2 < x + tileSize * (tx + 2)
									&& mouseY - fBoardPos.y - height / 2 - fBoard.getTimelineTop() * boardSize >= y + tileSize * (ty + 1) 
									&& mouseY - fBoardPos.y - height / 2 - fBoard.getTimelineTop() * boardSize < y + tileSize * (ty + 2)) {
									if (selectedCoord.getTime() == -1) {
										selectedCoord = new Coord(l + fBoard.getTimelineTop(), t, turn, ty, tx);
										// if it's enemy turn
										if (false) break;
										List<Move> possibleMoves = MoveGenerator.generateMoves(fBoard, selectedCoord);
										System.out.println(possibleMoves.toString());
										possibleMoveCoords = possibleMoves.stream().map(m -> m.getTo()).toList();
										drawFiveBoard();
									} else if (selectedCoord.getTimeline() == l + fBoard.getTimelineTop()
												&& selectedCoord.getTime() == t
												&& selectedCoord.getTurn() == turn
												&& selectedCoord.getY() == ty
												&& selectedCoord.getX() == tx) {
										selectedCoord = new Coord(0, -1, 0, 0, 0);
										possibleMoveCoords = new ArrayList<>();
										drawFiveBoard();
									} else {
										Coord moveToCoord = new Coord(l + fBoard.getTimelineTop(), t, turn, ty, tx);
										if (possibleMoveCoords.contains(moveToCoord)) {
											fBoard.movePiece(new Move(selectedCoord, moveToCoord));
											selectedCoord = new Coord(0, -1, 0, 0, 0);
											possibleMoveCoords = new ArrayList<>();
											drawFiveBoard();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
