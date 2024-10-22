import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("Введите цепь:");
        ElectricalCircuit ec = new ElectricalCircuit(System.in);

        System.out.println("Список ветвей:");
        for (Branch branch : ec.branches) {
            System.out.println(branch);
        }

        System.out.println("Список узлов:");
        System.out.println(getAllNodes(ec));

        if (ec.isCircuitContinuous() == false) {
            System.out.println("Цепь не замкнута");
        } else {
            System.out.println("Цепь замкнута");
            if (isCircuitNotContainBridges(ec) == false) {
                System.out.println("Цепь содержит мосты");
            } else{
                System.out.println("Цепь не содержит мостов");
                int numberOfConnectedComponents = getNumberOfConnectedComponents(ec);
                System.out.println("Число компонент связности:");
                System.out.println(numberOfConnectedComponents);

                CycleSet cs;
                {
                    cs = new CycleSet(ec);
                } while (cs.isSetCorrect(ec,numberOfConnectedComponents) == false);
                System.out.println("Подходящая система циклов:");
                for (Cycle cycle: cs.cycles) {
                    System.out.println(cycle);
                }
            }
        }
    }

    public static ArrayList<Integer> getAllNodes(ElectricalCircuit ec) {
        Set<Integer> nodesSet = new HashSet<>();

        for (Branch branch : ec.branches) {
            nodesSet.add(branch.startNode);
            nodesSet.add(branch.endNode);
        }

        return new ArrayList<>(nodesSet);
    }

    public static int getNumberOfConnectedComponents(ElectricalCircuit ec) {
        ArrayList<Integer> allNodes = getAllNodes(ec);
        Set<Integer> visited = new HashSet<>();
        int componentsCount = 0;

        for (int node : allNodes) {
            if (!visited.contains(node)) {
                dfs(node, ec, visited);
                componentsCount++;
            }
        }

        return componentsCount;
    }

    public static void dfs(int node, ElectricalCircuit ec, Set<Integer> visited) {
        visited.add(node);

        for (Branch branch : ec.branches) {
            if (branch.startNode == node && !visited.contains(branch.endNode)) {
                dfs(branch.endNode, ec, visited);
            } else if (branch.endNode == node && !visited.contains(branch.startNode)) {
                dfs(branch.startNode, ec, visited);
            }
        }
    }

    public static boolean isCircuitNotContainBridges(ElectricalCircuit ec) {
        ElectricalCircuit ecClone = ec.clone();
        int numberOfConnectedComponents = getNumberOfConnectedComponents(ecClone);
        for (Branch branch: ec.branches) {
            ecClone.removeBranch(branch);
            if (numberOfConnectedComponents < getNumberOfConnectedComponents(ecClone)) {
                return false;
            }
            ecClone.addBranch(branch);
        }
        return true;
    }
}
