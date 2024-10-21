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

    public static ArrayList<Integer> getAllNodes(ElectricalCircuit ec) {
        Set<Integer> nodesSet = new HashSet<>();

        for (Branch branch : ec.branches) {
            nodesSet.add(branch.startNode);
            nodesSet.add(branch.endNode);
        }

        return new ArrayList<>(nodesSet);
    }

    public boolean isSetCorrect(ElectricalCircuit ec) {
        if (cycles.size() == ec.branches.size() - getAllNodes(ec).size() +1) {
            return true;
        } else {
            return false;
        }
    }
}
