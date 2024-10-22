import exceptions.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Введите цепь:");
        ElectricalCircuit ec = new ElectricalCircuit(System.in);

        System.out.println("Список ветвей:");
        for (Branch branch : ec.branches) {
            System.out.println(branch);
        }

        System.out.println("Список узлов:");
        System.out.println(ec.getAllNodes());

        if (ec.isCircuitContinuous() == false) {
            throw new CircuitIsNotContinuousException("Цепь не замкнута");
        }

        if (ec.hasNoBridges() == false) {
            throw new CircuitHasBridgesException("Цепь содержит мосты");
        }

        int numberOfConnectedComponents = ec.getConnectedComponentsCount();
        System.out.println("Число компонент связности: " + numberOfConnectedComponents);

        CycleSet cs;
        do {
            cs = new CycleSet(ec);
        } while (cs.isSetCorrect(ec,numberOfConnectedComponents) == false);

        System.out.println("Подходящая система циклов:");
        for (Cycle cycle: cs.cycles) {
            System.out.println(cycle);
        }

    }
}
