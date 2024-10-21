import java.util.ArrayList;

public class Cycle {

    public static class BranchInCycle {

        public Branch branchInCycle;
        public Boolean inversion;

        public BranchInCycle(Branch branchInCycle,
                             Boolean inversion
        ) {
            this.branchInCycle = branchInCycle;
            this.inversion = inversion;
        }

        @Override
        public String toString() {
            return "{" + branchInCycle.id + "{" +
                    branchInCycle.startNode +
                    " , " + branchInCycle.endNode +
                    "}: " + inversion +
                    '}';
        }
    }

    public ArrayList<BranchInCycle> branchesInCycles = new ArrayList<>();

    public Cycle(ElectricalCircuit ec, ElectricalCircuit unused) {
        while (true) {
            ElectricalCircuit used = ec.clone();
            Branch initialBranch = unused.branches.get(0);
            addBranchInCycle(new BranchInCycle(initialBranch, false));
            used.removeBranch(initialBranch);
            int begin = initialBranch.startNode;
            int end = initialBranch.endNode;
            ElectricalCircuit inappropriate = used.clone();

            while((begin != end) && (inappropriate.branches.isEmpty() == false)) {
                int indexRandomBranch = (int) (Math.random() * inappropriate.branches.size());
                Branch randomBranch = inappropriate.branches.get(indexRandomBranch);

                if (end == randomBranch.startNode) {
                    addBranchInCycle(new BranchInCycle(randomBranch, false));
                    end = randomBranch.endNode;
                    used.removeBranch(indexRandomBranch);
                    inappropriate = used.clone();
                } else if (end == randomBranch.endNode) {
                    addBranchInCycle(new BranchInCycle(randomBranch, true));
                    end = randomBranch.startNode;
                    used.removeBranch(indexRandomBranch);
                    inappropriate = used.clone();
                } else {
                    inappropriate.removeBranch(indexRandomBranch);
                }
            }

            if (begin == end){
                return;
            }
        }
    }

    public void addBranchInCycle(BranchInCycle b) {
        branchesInCycles.add(b);
    }

    @Override
    public String toString() {
        return "Cycle{" +
                branchesInCycles +
                '}';
    }
}
