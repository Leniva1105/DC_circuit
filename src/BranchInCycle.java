public class BranchInCycle {
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
        return "{" + branchInCycle.number + "{" +
                branchInCycle.startingNode +
                " , " + branchInCycle.endNode +
                "}: " + inversion +
                '}';
    }
}
