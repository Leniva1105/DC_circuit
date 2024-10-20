import java.util.*;

@SuppressWarnings("preview")
public class Main {

    private static ElectricalCircuit unusedBranches;

    public static void main(String[] args) {
        ElectricalCircuit ec = inputElectricalCircuit();
        for (Branch branch: ec.circuit) {
            System.out.println(branch);
        }
        System.out.println(check(ec));
        for (int i = 0; i < 100; i++) {
        ArrayList<Cycle> sofc = createSetOfCycle(ec);
        for (Cycle cycle: sofc) {
            System.out.println(cycle);
        }
        System.out.println();
        }
    }

    private static ElectricalCircuit inputElectricalCircuit() {
        Scanner scanner = new Scanner(System.in);
        ElectricalCircuit ec = new ElectricalCircuit();
        int i = 0;
        while (true) {
            int startingNode = scanner.nextInt();
            if (startingNode == -1) {
                break;
            }
            Branch b = new Branch(
                    startingNode,
                    scanner.nextInt(),
                    scanner.nextDouble(),
                    scanner.nextDouble(),
                    i
            );
            ec.addBranch(b);
            i++;
        }
        return ec;
    }

    private static boolean check (ElectricalCircuit ec){
        HashMap<Integer, Integer> checkMap = new HashMap<>();

        for (Branch branch: ec.circuit) {
            int currentValueStart = checkMap.get(branch.startingNode) == null ? 0 : checkMap.get(branch.startingNode);
            int currentValueEnd = checkMap.get(branch.endNode) == null ? 0 : checkMap.get(branch.endNode);
            checkMap.put(branch.startingNode, currentValueStart + 1);
            checkMap.put(branch.endNode, currentValueEnd + 1);
        }
        for (int key: checkMap.keySet()) {
            if (checkMap.get(key) == 1) {
                return false;
            }
        }
        return true;
    }

    private static Cycle createCycle(ElectricalCircuit ec, ElectricalCircuit unusedBranches) {
        while (true) {
            ElectricalCircuit usedBranches = ec.clone();
            Cycle cycle = new Cycle();
            Branch initialBranch = unusedBranches.circuit.get(0);
            cycle.addBranchInCycle(new BranchInCycle(initialBranch, false));
            usedBranches.removeBranch(initialBranch);
            int begin = initialBranch.startingNode;
            int end = initialBranch.endNode;
            ElectricalCircuit inappropriateBranches = usedBranches.clone();
            while((begin != end)&&(!inappropriateBranches.circuit.isEmpty())) {
                int indexRandomBranch = (int) (Math.random() * inappropriateBranches.circuit.size());
                Branch randomBranch = inappropriateBranches.circuit.get(indexRandomBranch);
                if (end == randomBranch.startingNode) {
                    cycle.addBranchInCycle(new BranchInCycle(randomBranch, false));
                    end = randomBranch.endNode;
                    usedBranches.removeBranch(indexRandomBranch);
                    inappropriateBranches = usedBranches.clone();
                } else if (end == randomBranch.endNode) {
                    cycle.addBranchInCycle(new BranchInCycle(randomBranch, true));
                    end = randomBranch.startingNode;
                    usedBranches.removeBranch(indexRandomBranch);
                    inappropriateBranches = usedBranches.clone();
                } else {
                    inappropriateBranches.removeBranch(indexRandomBranch);
                }
            }
            if (begin == end){
                return cycle;
            }
        }
    }

    private static ArrayList<Cycle> createSetOfCycle (ElectricalCircuit ec) {
        ElectricalCircuit unusedBranches = ec.clone();
        ArrayList<Cycle> setOfCycle = new ArrayList<>();
        while (!unusedBranches.circuit.isEmpty()) {
            Cycle cycle = createCycle(ec,unusedBranches);
            setOfCycle.add(cycle);
            for (BranchInCycle branchInCycle: cycle.cycle) {
                unusedBranches.removeBranch(branchInCycle.branchInCycle);
            }
        }
        return setOfCycle;
    }
}
