package model;

public enum State {
    VERTICAL,
    EMPTY,
    HORIZONTAL,
    CONNECTOR,
    START,
    END;

    public boolean checkConnection(State comparing, int currentX, int currentY, int neighborghX, int neighborghY) {
        switch (this) {
            case VERTICAL -> {

                if (comparing == CONNECTOR || comparing == START || comparing == END || comparing == VERTICAL) {
                    return true;
                }
            }
            case HORIZONTAL -> {
                if (comparing == CONNECTOR || comparing == START || comparing == END || comparing == HORIZONTAL) {
                    return true;
                }
            }
            case CONNECTOR, START, END -> {
                if (comparing == HORIZONTAL || comparing == VERTICAL) {
                    return  true;
                }
            }case EMPTY -> {
                return false;
            }
        }

        return false;
    }


}
