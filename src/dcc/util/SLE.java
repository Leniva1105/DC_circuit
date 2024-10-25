package dcc.util;

import dcc.elements.CycleSet;
import dcc.elements.ElectricalCircuit;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Formatter;

public class SLE {

    private final SimpleMatrix matrixSLE;
    private final SimpleMatrix vectorFreeFactors;

    public SLE(CycleSet cs, ElectricalCircuit ec) {
        SimpleMatrix c = cs.C(ec);
        SimpleMatrix ct = c.transpose();
        SimpleMatrix z = cs.Z(ec);
        SimpleMatrix e = cs.E(ec);
        this.matrixSLE = c.mult(z.mult(ct));
        this.vectorFreeFactors = c.mult(e);
    }

    public ArrayList<Double> solve(CycleSet cs) {
        SimpleMatrix answerMatrix = matrixSLE.solve(vectorFreeFactors);
        ArrayList<Double> answer = new ArrayList<>();
        int size = cs.size();

        for (int i = 0; i < size; i++) {
            answer.add(answerMatrix.get(i));
        }
        return answer;
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();

        for (int i = 0; i < matrixSLE.getNumRows(); i++) {
            for (int j = 0; j < matrixSLE.getNumCols(); j++) {
                formatter.format("% .1f⋅i%d ", matrixSLE.get(i, j), j + 1);

                if (j != matrixSLE.getNumCols() - 1) {
                    formatter.format("+ ");
                }
            }
            formatter.format("= % .1f\n", vectorFreeFactors.get(i, 0));
        }

        return formatter.toString();
    }
}
