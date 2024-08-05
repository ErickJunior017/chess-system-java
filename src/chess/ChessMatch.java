package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.chess.pieces.King;
import chess.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;


public class ChessMatch {
    private Color currentPlayer;
    private int turn;
    private Board board;

    List<Piece> piecesOnTheBoard = new ArrayList<>();
    List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch(){
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
    };


    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++){
            for (int j = 0; j < board.getColumns(); j++){
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    //vai ser respondavel por validar as posições(sourcePosition, targetPosition);
    //também por receber a peça capturada;
    //E alterar o turno;
    //A maioria das funcionalidades está encapsulada;
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    //metodo utilizado para remover a peça que se moveu da sua posição original(source) e devolvela na posição (target)
    //e a peça que foi capturada pela a outra peça que se moveu vai ser removida do tabuleiro
    private Piece makeMove(Position source, Position target){
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        if (capturedPiece != null){
            capturedPieces.add(capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }
        return capturedPiece;
    }

    //Método para validar a se exite alguma peça nessa posição
    private void validateSourcePosition(Position position){
        if (!board.thereIsAPiece(position)){
            throw new ChessException("There is no piece on source position");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()){
            throw new ChessException("The chosen piece is not yours");
        }
        if (!board.piece(position).isThereAnyPossibleMove()){// se não existem uma peça nessa posição
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target){
        if (!board.piece(source).possibleMove(target)){
            throw new ChessException("The chose piece can't move to target position");
        }
    }

    //metodo utilizado para mudar o turno e também o openante
    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE)?Color.BLACK: Color.WHITE;
    }

    //metodo utilizado para adicionar no board as peças de acordo com os argumentos paçados
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    //Vai inicializa o board com essa peças abaixo
    private void initialSetup(){
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));

    }
}
