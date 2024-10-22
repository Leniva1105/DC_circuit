import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CycleSet {

    public ArrayList<Cycle> cycles;

    public CycleSet(ElectricalCircuit ec) {
        ElectricalCircuit unused = ec.clone();
        cycles = new ArrayList<>();

        while (unused.branches.isEmpty() == false) {
            Cycle cycle = new Cycle(ec, unused);
            cycles.add(cycle);

            for (Cycle.BranchInCycle branchInCycle: cycle.branchesInCycles) {
                unused.removeBranch(branchInCycle.branchInCycle);
            }
        }
    }

    public boolean isSetCorrect(ElectricalCircuit ec, int numberOfConnectedComponents) {
        return cycles.size() == ec.branches.size() - Main.getAllNodes(ec).size() + numberOfConnectedComponents;
    }

}
