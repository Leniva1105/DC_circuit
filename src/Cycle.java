import java.util.ArrayList;

public class Cycle {
    ArrayList<BranchInCycle> cycle = new ArrayList<>();

    public void addBranchInCycle(BranchInCycle b) {
        cycle.add(b);
    }

    @Override
    public String toString() {
        return "Cycle{" +
                cycle +
                '}';
    }
}
