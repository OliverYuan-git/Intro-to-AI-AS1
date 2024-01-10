import java.util.*;
/**
 * The search method using A* seach
 *
 * @author Oliver Yuan
 * @date 03/02/2023
 */
public class ASearch extends GoalPuzzle{

    private int[] startState;
    private int[] goalState;
    /**
     * size here is the grid length which is 3 in this case
     */
    private int size;
    /**
     * the maximum depth that the search algorithm will explore
     */
    private int maxDepth;
    /**
     * the cost of reaching the goal state from the current state
     */
    private String heuristic;
    /**
     * the maximum number of nodes to be considered during a search.
     */
    private int maxNodes;
    /**
     * set the initial explored node number to be 0.
     */
    private int numNodesExplored = 0;
    /**
     * ASearch Constructor
     *
     * @param startState 
     * @param goalState
     * @param size 
     * @param heuristic
     * @param maxDepth
     * @param maxNodes
     */
    public ASearch(int[] startState, int[] goalState, int size, String heuristic,int maxDepth, int maxNodes) {
        this.startState = startState;
        this.goalState = goalState;
        this.size = size;
        this.heuristic = heuristic;
        this.maxDepth = maxDepth;
        this.maxNodes = maxNodes;
    }

    /**
     * Method solve
     * To check if the state is solvable, and with the priority queue, it exapnds the set of nodes and store the closed set of nodes that have expanded, then show the current state of the puzzle
     * @return -1 if false
     */
    public List<String> solve() {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        Node startNode = new Node(startState, 0, calculateHeuristic(startState), null, "");
        openSet.add(startNode);
        int nodesExpanded = 0;

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            numNodesExplored++;
            if (nodesExpanded >= maxNodes) {
                System.out.println("Maximum number of nodes exceeded");
                return null;
            }
            if (Arrays.equals(currentNode.getState(), goalState)) {
                return generateSolution(currentNode);
            }
            if (currentNode.getG() > maxDepth) {
                continue;
            }
            closedSet.add(currentNode);
            nodesExpanded++;

            List<Node> successors = getSuccessors(currentNode);
            for (Node successor : successors) {
                if (closedSet.contains(successor)) {
                    continue;
                }
                int tentativeGScore = currentNode.getG() + 1;
                boolean isNew = !openSet.contains(successor);
                if (isNew || tentativeGScore < successor.getG()) {
                    successor.parent = currentNode;
                    successor.g = tentativeGScore;
                    successor.h = calculateHeuristic(successor.getState());
                    if (isNew) {
                        openSet.add(successor);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Method getSuccessors
     * the method here is to get the the possible successor
     * @param using the node that expanded to search the possible successors
     * @return the possible successors 
     */
    private List<Node> getSuccessors(Node node) {
        List<Node> successors = new ArrayList<>();
        int index = -1;
        for (int i = 0; i < node.getState().length; i++) {
            if (node.getState()[i] == 0) {
                index = i;
                break;
            }
        }
        int row = index / size;
        int col = index % size;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // up, down, left, right
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                int[] newState = node.getState().clone();
                int newIndex = newRow * size + newCol;
                newState[index] = newState[newIndex];
                newState[newIndex] = 0;
                Node successor = new Node(newState, 0, 0, null, "");
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
     * Method generateSolution
     * the method to get the solution
     * @param node 
     * @return processor of movement
     */
    private List<String> generateSolution(Node node) {
        List<String> solution = new ArrayList<>();
        Node currentNode = node;
        while (currentNode.getParent() != null) {
            int blankIndex = getBlankIndex(currentNode.getState());
            int parentBlankIndex = getBlankIndex(currentNode.getParent().getState());
            int move = blankIndex - parentBlankIndex;
            switch (move) {
                case 0:
                    solution.add("right");
                    break;
                case 1:
                    solution.add("left");
                    break;
                case 2:
                    solution.add("down");
                    break;
                case 3:
                    solution.add("up");
                    break;
                default:
                    return null;
            }
            currentNode = currentNode.getParent();
        }
        Collections.reverse(solution);
        return solution;
    }

    /**
     * Method getBlankIndex
     * the method to get the index number of the blank tile
     * @param state 
     * @return the index of the blank tile or -1 if false
     */
    private int getBlankIndex(int[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1; // Error: the blank tile is missing
    }
} 
