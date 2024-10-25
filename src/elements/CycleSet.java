package elements;

import exceptions.CircuitHasBridgesException;
import exceptions.CircuitIsNotContinuousException;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class CycleSet implements Iterable<Cycle> {

    private ArrayList<Cycle> cycles;

    public CycleSet(ElectricalCircuit ec) {
        if (ec.isCircuitContinuous() == false) {
            throw new CircuitIsNotContinuousException("Цепь не замкнута");
        }

        if (ec.hasNoBridges() == false) {
            throw new CircuitHasBridgesException("Цепь содержит мосты");
        }

        int numberOfConnectedComponents = ec.getConnectedComponentsCount();

        do {
            ElectricalCircuit unused = ec.clone();
            cycles = new ArrayList<>();

            while (unused.isEmpty() == false) {
                Cycle cycle = new Cycle(ec, unused);
                cycles.add(cycle);

                for (Cycle.BranchInCycle branchInCycle : cycle) {
                    unused.removeBranch(branchInCycle.branch());
                }
            }
        } while (isSetCorrect(ec, numberOfConnectedComponents) == false);
    }

    public Cycle get(int i) {
        return cycles.get(i);
    }

    public int size() {
        return cycles.size();
    }

    public boolean isSetCorrect(ElectricalCircuit ec, int numberOfConnectedComponents) {
        return cycles.size() == ec.size() - ec.getAllNodes().size() + numberOfConnectedComponents;
    }

    public SimpleMatrix C(ElectricalCircuit ec) {
        int numRows = cycles.size();
        int numCols = ec.size();
        SimpleMatrix c = new SimpleMatrix(numRows,numCols);

        for (int i = 0; i < numRows; i++){

            for (int j = 0; j < numCols; j++){
            c.set(i,j,cycles.get(i).hasBranch(ec.get(j)));
            }
        }
        return c;
    }

    public SimpleMatrix Z(ElectricalCircuit ec) {
        int numRowsAndCols = ec.size();
        SimpleMatrix z = new SimpleMatrix(numRowsAndCols, numRowsAndCols);

        for (int i = 0; i < numRowsAndCols; i++) {
            z.set(i,i,ec.get(i).resistance());
        }
        return  z;
    }

    public SimpleMatrix E(ElectricalCircuit ec) {
        int numRows = ec.size();
        SimpleMatrix e = new SimpleMatrix(numRows, 1);

        for (int i = 0; i < numRows; i++) {
            e.set(i,0,ec.get(i).emf());
        }
        return e;
    }

    @Override
    public Iterator<Cycle> iterator() {
        return cycles.iterator();
    }

    @Override
    public void forEach(Consumer<? super Cycle> action) {
        Iterable.super.forEach(action);
    }
}
