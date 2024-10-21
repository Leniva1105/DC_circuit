import java.io.InputStream;
import java.util.*;

public class ElectricalCircuit {

    public ArrayList<Branch> branches;

    public ElectricalCircuit() {
        branches = new ArrayList<>();
    }

    public ElectricalCircuit(InputStream is) {
        branches = new ArrayList<>();
        Scanner scanner = new Scanner(is);
        int id = 0;

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
                    id++
            );
            addBranch(b);
        }
    }

    public boolean isCircuitContinuous() {
        HashMap<Integer, Integer> checkMap = new HashMap<>();

        for (Branch branch: branches) {
            int currentValueStart = checkMap.get(branch.startNode) == null ? 0 : checkMap.get(branch.startNode);
            int currentValueEnd = checkMap.get(branch.endNode) == null ? 0 : checkMap.get(branch.endNode);
            checkMap.put(branch.startNode, currentValueStart + 1);
            checkMap.put(branch.endNode, currentValueEnd + 1);
        }
        for (int key: checkMap.keySet()) {
            if (checkMap.get(key) == 1) {
                return false;
            }
        }
        return true;
    }

    public void addBranch(Branch b) {
        branches.add(b);
    }

    public void removeBranch(int i) {
        branches.remove(i);
    }

    public void removeBranch(Branch b) {
        for (int i = 0; i < branches.size(); i++) {
            if (branches.get(i).id == b.id) {
                branches.remove(i);
                break;
            }
        }
    }

    @Override
    public ElectricalCircuit clone() {
        ElectricalCircuit newEC = new ElectricalCircuit();

        for (Branch b: branches) {
            newEC.addBranch(b.clone());
        }

        return newEC;
    }

    @Override
    public String toString() {
        return "ElectricalCircuit{" +
                "addBranch=" + branches +
                '}';
    }
}
