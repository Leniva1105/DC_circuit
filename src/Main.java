import exceptions.*;

import java.util.ArrayList;

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

        CycleSet cs = new CycleSet(ec);

        System.out.println("Подходящая система циклов:");
        for (Cycle cycle: cs.cycles) {
            System.out.println(cycle + "     sumEmf= " + cycle.sumEmf());
        }
        SLE sle = new SLE(cs,ec);
        ArrayList<Double> contourCurrent = sle.decision(cs);
        Answer answer = new Answer(ec,cs);
        answer.getAnswer(ec);
    }
}
