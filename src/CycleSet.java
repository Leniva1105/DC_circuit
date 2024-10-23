import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class CycleSet {

    public ArrayList<Cycle> cycles;

    public CycleSet(ElectricalCircuit ec) {
        int numberOfConnectedComponents = ec.getConnectedComponentsCount();

        do {
            ElectricalCircuit unused = ec.clone();
            cycles = new ArrayList<>();

            while (unused.branches.isEmpty() == false) {
                Cycle cycle = new Cycle(ec, unused);
                cycles.add(cycle);

                for (Cycle.BranchInCycle branchInCycle : cycle.branchesInCycles) {
                    unused.removeBranch(branchInCycle.branchInCycle);
                }
            }
        } while (isSetCorrect(ec, numberOfConnectedComponents) == false);
    }

    public boolean isSetCorrect(ElectricalCircuit ec, int numberOfConnectedComponents) {
        return cycles.size() == ec.branches.size() - ec.getAllNodes().size() + numberOfConnectedComponents;
    }

    public SimpleMatrix C(ElectricalCircuit ec) {
        int numRows = cycles.size();
        int numCols = ec.branches.size();
        SimpleMatrix c = new SimpleMatrix(numRows,numCols);

        for (int i = 0; i < numRows; i++){

            for (int j = 0; j < numCols; j++){
            c.set(i,j,cycles.get(i).hasBranch(ec.branches.get(j)));
            }
        }
        return c;
    }

    public SimpleMatrix Z(ElectricalCircuit ec) {
        int numRowsAndCols = ec.branches.size();
        SimpleMatrix z = new SimpleMatrix(numRowsAndCols, numRowsAndCols);

        for (int i = 0; i < numRowsAndCols; i++) {
            z.set(i,i,ec.branches.get(i).resistance);
        }
        return  z;
    }

    public SimpleMatrix E(ElectricalCircuit ec) {
        int numRows = ec.branches.size();
        SimpleMatrix e = new SimpleMatrix(numRows, 1);

        for (int i = 0; i < numRows; i++) {
            e.set(i,0,ec.branches.get(i).emf);
        }
        return e;
    }
}
