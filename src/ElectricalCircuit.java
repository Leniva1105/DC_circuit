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
        int id = 1;

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

    public ArrayList<Integer> getAllNodes() {
        Set<Integer> nodesSet = new HashSet<>();

        for (Branch branch : branches) {
            nodesSet.add(branch.startNode);
            nodesSet.add(branch.endNode);
        }

        return new ArrayList<>(nodesSet);
    }

    public boolean hasNoBridges() {
        int numberOfConnectedComponents = getConnectedComponentsCount();
        ElectricalCircuit ecClone = clone();

        for (Branch branch: branches) {
            ecClone.removeBranch(branch);

            if (numberOfConnectedComponents < ecClone.getConnectedComponentsCount()) {
                return false;
            }
            ecClone.addBranch(branch);
        }
        return true;
    }

    public int getConnectedComponentsCount() {
        ArrayList<Integer> allNodes = getAllNodes();
        Set<Integer> visited = new HashSet<>();
        int componentsCount = 0;

        for (int node : allNodes) {
            if (!visited.contains(node)) {
                dfs(node, visited);
                componentsCount++;
            }
        }

        return componentsCount;
    }

    private void dfs(int node, Set<Integer> visited) {
        visited.add(node);

        for (Branch branch : branches) {
            if (branch.startNode == node && !visited.contains(branch.endNode)) {
                dfs(branch.endNode, visited);
            } else if (branch.endNode == node && !visited.contains(branch.startNode)) {
                dfs(branch.startNode, visited);
            }
        }
    }

    public void addBranch(Branch b) {
        branches.add(b);
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
