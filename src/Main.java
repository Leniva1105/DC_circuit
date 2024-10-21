import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        ElectricalCircuit ec = new ElectricalCircuit(System.in);

        for (Branch branch : ec.branches) {
            System.out.println(branch);
        }

        System.out.println(ec.isCircuitContinuous());

        System.out.println(CycleSet.getAllNodes(ec));

        CycleSet cs;
        {
            cs = new CycleSet(ec);
        } while (cs.isSetCorrect(ec) == false);

        for (Cycle cycle: cs.cycles) {
            System.out.println(cycle);
        }
    }
}
