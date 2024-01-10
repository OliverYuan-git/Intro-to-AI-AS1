import java.util.*;

public class BeamSearch extends GoalPuzzle{
    private int[] startState;
    private int[] goalState;
    private String heuristic;
    private int size;
    private int maxNodes;
    private int k;
    private Random random;
    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public BeamSearch(int[] startState, int[] goalState, int size, int k, int maxNodes) {
        this.startState = startState;
        this.goalState = goalState;
        this.heuristic = heuristic;
        this.size = size;
        this.k = k;
        this.maxNodes = maxNodes;
        this.random = new Random();
    }

    public List<String> solve() {
        List<Node> currentStates = new ArrayList<>();
        currentStates.add(new Node(startState, 0, calculateHeuristic(startState), null, ""));
        int nodesExpanded = 0;

        while (!currentStates.isEmpty() && nodesExpanded < maxNodes) {
            List<Node> nextStates = new ArrayList<>();
            for (Node currentState : currentStates) {
                if (Arrays.equals(currentState.getState(), goalState)) {
                    return generateSolution(currentState);
                }
                List<Node> successors = getSuccessors(currentState);
                nextStates.addAll(successors);
                nodesExpanded++;
            }
            currentStates = selectBestStates(nextStates, k);
        }

        System.out.println("Goal not found within " + maxNodes + " nodes");
        return null;
    }

    private List<Node> selectBestStates(List<Node> states, int k) {
        states.sort(Comparator.comparingInt(Node::getF));
        return states.subList(0, Math.min(k, states.size()));
    }

    private List<Node> getSuccessors(Node node) {
        List<Node> successors = new ArrayList<>();
        int index = getBlankIndex(node.getState());
        int x = index / size;
        int y = index % size;
        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int nx = x + dx;
            int ny = y + dy;
            if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                int ni = nx * size + ny;
                int[] newState = node.getState().clone();
                newState[index] = newState[ni];
                newState[ni] = 0;
                Node successor = new Node(newState, 0 ,node.getG() + 1, node, getMove(x, y, nx, ny));
                successors.add(successor);
            }
        }
        return successors;
    }

    /**
     * Method calculateHeuristic
     * the method to get h1  number of misplaced tiles
     * @param current state
     * @return  number of misplaced tiles
     */
    private int calculateHeuristic(int[] state) {
        if (heuristic.equals("h1")) {
            return calculateMisplacedTiles(state);
        } else if (heuristic.equals("h2")) {
            return calculateManhattanDistance(state);
        } else {
            return 0;
        }
    }
    /**
     * Method indexOf
     * the method to find the index of the a number
     * @param array 
     * @param value
     * @return the index value or -1 if false
     */
    private int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Method calculateMisplacedTiles
     * the method to get h2 the sum of the distances of the tiles from their goal positions.
     * @param current state
     * @return the sum of the distances of the tiles from their goal positions.
     */
    private int calculateMisplacedTiles(int[] state) {
        int misplacedTiles = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i] != goalState[i]) {
                misplacedTiles++;
            }
        }
        return misplacedTiles;
    }

    /**
     * Method calculateManhattanDistance
     * the method to get f
     * @param current state
     * @return f
     */
    private int calculateManhattanDistance(int[] state) {
        int manhattanDistance = 0;
        for (int i = 0; i < state.length; i++) {
            int value = state[i];
            if (value != 0) {
                int goalIndex = indexOf(goalState, value);
                int rowDistance = Math.abs(i / size - goalIndex / size);
                int colDistance = Math.abs(i % size - goalIndex % size);
                manhattanDistance += rowDistance + colDistance;
            }
        }
        return manhattanDistance;
    }
    
    private String getMove(int x1, int y1, int x2, int y2) {
        if (x1 < x2) {
            return "down";
        } else if (x1 > x2) {
            return "up";
        } else if (y1 < y2) {
            return "right";
        } else {
            return "left";
        }
    }

    private int getBlankIndex(int[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    private List<String> generateSolution(Node node) {
        List<String> solution = new ArrayList<>();
        Node current = node;
        while (current.getParent() != null) {
            solution.add(0, current.getMove());
            current = current.getParent();
        }
         System.out.println("Moves: " + solution.size());
        System.out.println("Solution: " + solution);
        return solution;
    }
}
