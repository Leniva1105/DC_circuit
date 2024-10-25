package dcc.elements;

public class Branch {

    private static int branchesCount = 1;
    private final int id;

    private final int startNode;
    private final int endNode;
    private final double emf;
    private final double resistance;

    public Branch(int startNode, int endNode, double emf, double resistance) {
        if (startNode <= 0 ||
            endNode <= 0 ||
            resistance < 0
        ) {
            throw new IllegalArgumentException();
        }

        this.startNode = startNode;
        this.endNode = endNode;
        this.emf = emf;
        this.resistance = resistance;
        this.id = branchesCount++;
    }

    private Branch(int id, int startNode, int endNode, double emf, double resistance) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.emf = emf;
        this.resistance = resistance;
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static void resetIdCounter() {
        branchesCount = 1;
    }

    public int startNode() {
        return startNode;
    }

    public int endNode() {
        return endNode;
    }

    public double emf() {
        return emf;
    }

    public double resistance() {
        return resistance;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Branch clone() {
        return new Branch(id, startNode, endNode, emf, resistance);
    }

    @Override
    public String toString() {
        return "Ветка " + id + ": " +
                "узел 1 = " + startNode +
                ", узел 2 = " + endNode +
                ", ЭДС = " + emf +
                ", сопротивление = " + resistance;
    }
}
