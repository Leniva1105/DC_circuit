package elements;

public record Branch(
        int startNode,
        int endNode,
        double emf,
        double resistance,
        int id
) {

    public Branch {
        if (startNode <= 0 ||
            endNode <= 0 ||
            resistance <= 0 ||
            id <= 0
        ) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Branch clone() {
        return new Branch(startNode, endNode, emf, resistance, id);
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
