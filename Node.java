public class Node {
     int[] state;
     int g;
     int h;
     Node parent;
     String move;

    public Node(int[] state, int g, int h, Node parent, String move) {
        this.state = state;
        this.g = g;
        this.h = h;
        this.parent = parent;
        this.move = move;
    }

    public int[] getState() {
        return state;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return g + h;
    }

    public Node getParent() {
        return parent;
    }

    public String getMove() {
        return move;
    }

    private int getBlankIndex(int[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1;
    }

}

