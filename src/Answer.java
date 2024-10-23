import java.util.ArrayList;

public class Answer {

    ArrayList<Double> answer;

    public Answer(ElectricalCircuit ec, CycleSet cs) {
        SLE sle = new SLE(cs,ec);
        ArrayList<Double> contourCurrents = sle.decision(cs);
        int branchesCount = ec.branches.size();
        int cyclesCount = cs.cycles.size();
        ArrayList<Double> answer = new ArrayList<>();

        for (int i = 0; i < branchesCount; i++) {
            answer.add(0.0);

            for (int j = 0; j < cyclesCount; j++) {
               answer.set(i, answer.get(i) + cs.cycles.get(j).hasBranch(ec.branches.get(i)) * contourCurrents.get(j));
            }
        }
        this.answer = answer;
    }

    public void getAnswer(ElectricalCircuit ec) {
        int branchesCount = ec.branches.size();

        for (int i = 0; i < branchesCount; i++) {
            System.out.println("I(" + ec.branches.get(i).id + ")= " + answer.get(i));
        }
    }
}
