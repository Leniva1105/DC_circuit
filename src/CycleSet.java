import java.util.ArrayList;

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
}
