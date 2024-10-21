public class Main {

    public static void main(String[] args) {
        ElectricalCircuit ec = new ElectricalCircuit(System.in);

        for (Branch branch: ec.branches) {
            System.out.println(branch);
        }

        System.out.println(ec.isCircuitContinuous());

        for (int i = 0; i < 100; i++) {
            CycleSet sofc = new CycleSet(ec);
            for (Cycle cycle: sofc.cycles) {
                System.out.println(cycle);
            }
            System.out.println();
        }
    }
}
