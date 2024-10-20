public class Branch {
    public int startingNode;
    public int endNode;
    public double emf;
    public final double resistance;
    public int number;


    public Branch(int startingNode,
                  int endNode,
                  double emf,
                  double resistance,
                  int number
    ) {
        this.startingNode = startingNode;
        this.endNode = endNode;
        this.emf = emf;
        this.resistance = resistance;
        this.number = number;
    }

    @Override
    public Branch clone() {
        return new Branch(startingNode, endNode, emf, resistance, number);
    }

    @Override
    public String toString() {
        return "Branch{" +
                "startingNode=" + startingNode +
                ", endNode=" + endNode +
                ", emf=" + emf +
                ", resistance=" + resistance +
                '}';
    }
}
