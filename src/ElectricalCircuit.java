import java.util.ArrayList;

public class ElectricalCircuit {
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
