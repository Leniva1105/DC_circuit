package elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class Cycle implements Iterable<Cycle.BranchInCycle> {

    public record BranchInCycle(Branch branch, boolean reverse) {
        @Override
        public String toString() {
            return branch +
                    ", инвертирована = " + (reverse ? "Да" : "Нет");
        }
    }

    private final ArrayList<BranchInCycle> branchesInCycles = new ArrayList<>();

    public Cycle(ElectricalCircuit ec, ElectricalCircuit unused) {
        while (true) {
            ElectricalCircuit used = ec.clone();
            Branch initialBranch = unused.get(0);
            addBranchInCycle(new BranchInCycle(initialBranch, false));
            used.removeBranch(initialBranch);
            int begin = initialBranch.startNode();
            int end = initialBranch.endNode();
            ElectricalCircuit inappropriate = used.clone();

            while((begin != end) && (inappropriate.isEmpty() == false)) {
                int indexRandomBranch = (int) (Math.random() * inappropriate.size());
                Branch randomBranch = inappropriate.get(indexRandomBranch);

                boolean reverse = end == randomBranch.endNode();
                if (end == randomBranch.startNode() || reverse) {
                    addBranchInCycle(new BranchInCycle(randomBranch, reverse));
                    end = reverse ? randomBranch.startNode() : randomBranch.endNode();
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

    public double hasBranch(Branch b) {
        for (BranchInCycle branchInCycle: branchesInCycles) {
            if (branchInCycle.branch.id() == b.id()) {

                if (branchInCycle.reverse() == false) {
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
    public Iterator<BranchInCycle> iterator() {
        return branchesInCycles.iterator();
    }

    @Override
    public void forEach(Consumer<? super BranchInCycle> action) {
        Iterable.super.forEach(action);
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        branchesInCycles.forEach((BranchInCycle bic) ->
                result.append('\t')
                      .append(bic)
                      .append('\n'));

        return "Цикл {\n" + result + "}";
    }
}
