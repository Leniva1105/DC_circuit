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

class Branch {
    public int startingNode;
    public int endNode;
    public double emf;
    public final double resistance;
    public int number;


    public Branch(int startingNode,
                  int endNode,
                  double emf,
                  double resistance,
                  int number
    ) {
        this.startingNode = startingNode;
        this.endNode = endNode;
        this.emf = emf;
        this.resistance = resistance;
        this.number = number;
    }

    @Override
    public Branch clone() {
        return new Branch(startingNode, endNode, emf, resistance, number);
    }

    @Override
    public String toString() {
        return "Branch{" +
                "startingNode=" + startingNode +
                ", endNode=" + endNode +
                ", emf=" + emf +
                ", resistance=" + resistance +
                '}';
    }
}

class ElectricalCircuit {
    public ArrayList<Branch> circuit = new ArrayList<>();

    public void addBranch(Branch b) {
        circuit.add(b);
    }

    public void removeBranch(int i) {
        circuit.remove(i);
    }

    public void removeBranch(Branch b) {
        for (int i = 0; i < circuit.size(); i++) {
            if (circuit.get(i).number == b.number) {
                circuit.remove(i);
                break;
            }
        }
    }

    @Override
    public ElectricalCircuit clone() {
        ElectricalCircuit newEC = new ElectricalCircuit();

        for (Branch b: circuit) {
            newEC.addBranch(b.clone());
        }

        return newEC;
    }

    @Override
    public String toString() {
        return "ElectricalCircuit{" +
                "addBranch=" + circuit +
                '}';
    }
}

class BranchInCycle {
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

class Cycle {
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
