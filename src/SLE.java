import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class SLE {

    SimpleMatrix matrixSLE;
    SimpleMatrix vectorFreeFactors;

    public SLE(CycleSet cs, ElectricalCircuit ec) {
        SimpleMatrix c = cs.C(ec);
        SimpleMatrix ct = c.transpose();
        SimpleMatrix z = cs.Z(ec);
        SimpleMatrix e = cs.E(ec);
        this.matrixSLE = c.mult(z.mult(ct));
        this.vectorFreeFactors = c.mult(e);
    }

    public ArrayList<Double> decision(CycleSet cs) {
        SimpleMatrix answerMatrix = matrixSLE.solve(vectorFreeFactors);
        ArrayList<Double> answer = new ArrayList<>();
        int size = cs.cycles.size();

        for (int i = 0; i < size; i++) {
            answer.add(answerMatrix.get(i));
        }
        return answer;
    }
}
