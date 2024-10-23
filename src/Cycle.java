import java.util.ArrayList;

public class Cycle {

    public static class BranchInCycle {

        public Branch branchInCycle;
        public Boolean reverse;

        public BranchInCycle(Branch branchInCycle,
                             Boolean reverse
        ) {
            this.branchInCycle = branchInCycle;
            this.reverse = reverse;
        }

        @Override
        public String toString() {
            return "{" + branchInCycle.id + "{" +
                    branchInCycle.startNode +
                    " , " + branchInCycle.endNode +
                    "}: " + reverse +
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

                boolean reverse = end == randomBranch.endNode;
                if (end == randomBranch.startNode || reverse) {
                    addBranchInCycle(new BranchInCycle(randomBranch, reverse));
                    end = reverse ? randomBranch.startNode : randomBranch.endNode;
                    used.removeBranch(randomBranch);
                    inappropriate = used.clone();
                } else {
                    inappropriate.removeBranch(randomBranch);
                }
            }

            if (begin == end){
                return;
            } else {
                branchesInCycles.clear();
            }
        }
    }

    public double sumEmf() {
        double sumEmf = 0;

        for (BranchInCycle branchInCycle: branchesInCycles) {

            if (branchInCycle.reverse == false){
                sumEmf += branchInCycle.branchInCycle.emf;
            } else {
                sumEmf -= branchInCycle.branchInCycle.emf;
            }
        }
        return sumEmf;
    }

    public double hasBranch(Branch b) {
        for (BranchInCycle branchInCycle: branchesInCycles) {

            if (branchInCycle.branchInCycle.id == b.id) {

                if (branchInCycle.reverse == false) {
                    return 1.0;
                } else {
                    return -1.0;
                }
            }
        }
        return 0.0;
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
