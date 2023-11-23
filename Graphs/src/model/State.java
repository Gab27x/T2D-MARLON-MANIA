package model;

public enum State {
    VERTICAL,
    EMPTY,
    HORIZONTAL,
    CONNECTOR,
    START,
    END;

    public boolean checkConnection(State comparing, int x, int y, int nX, int nY) {

        if (this == EMPTY || comparing == EMPTY) {
            return false;
        }

        switch (this) {
            case VERTICAL:
                return (comparing == VERTICAL && (x == nX) && (y == nY + 1 || y == nY - 1));
            case HORIZONTAL:
                return (comparing == HORIZONTAL && (y == nY) && (x == nX + 1 || x == nX - 1));
            case CONNECTOR,START,END:
                return (comparing == HORIZONTAL && y == nY && (x == nX + 1 || x == nX - 1)) || (comparing == VERTICAL && x == nX && (y == nY + 1 || y == nY - 1));
            default:
                return false;
        }
    }
}
