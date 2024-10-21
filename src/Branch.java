public class Branch {

    public int startNode;
    public int endNode;
    public double emf;
    public final double resistance;
    public int id;

    public Branch(int startNode,
                  int endNode,
                  double emf,
                  double resistance,
                  int id
    ) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.emf = emf;
        this.resistance = resistance;
        this.id = id;
    }

    @Override
    public Branch clone() {
        return new Branch(startNode, endNode, emf, resistance, id);
    }

    @Override
    public String toString() {
        return "Branch{" +
                "startingNode=" + startNode +
                ", endNode=" + endNode +
                ", emf=" + emf +
                ", resistance=" + resistance +
                '}';
    }
}
