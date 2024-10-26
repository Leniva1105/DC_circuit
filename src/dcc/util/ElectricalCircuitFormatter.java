package dcc.util;

import dcc.elements.Cycle;
import dcc.elements.ElectricalCircuit;

public class ElectricalCircuitFormatter {

    private static final String SEPARATOR = "------------------------------\n";

    public static String format(ElectricalCircuit ec) {
        StringBuilder sb = new StringBuilder();

        sb.append(SEPARATOR)
          .append("Список ветвей\n");
        for (dcc.elements.Branch branch : ec) {
            sb.append(branch).append("\n");
        }

        String nodesString = ec.getAllNodes().toString();
        sb.append(SEPARATOR)
          .append("Список узлов\n")
          .append(nodesString, 1, nodesString.length() - 1)
          .append('\n');

        sb.append(SEPARATOR)
          .append("Число компонент связности: ")
          .append(ec.getConnectedComponentsCount())
          .append('\n');

        sb.append(SEPARATOR)
          .append(ec.isCircuitContinuous() ? "Цепь замкнута\n" : "Цепь не замкнута\n")
          .append(ec.hasNoBridges() ? "Цепь не имеет мостов\n" : "Цепь содержит мосты\n");

        sb.append(SEPARATOR)
          .append("Подходящая система циклов\n");
        for (Cycle cycle: ec.getCycleSet()) {
            sb.append(cycle)
              .append('\n');
       }

        sb.append(SEPARATOR)
          .append("Система линейных уравнений\n");
        SLE sle = new SLE(ec.getCycleSet(), ec);
        sb.append(sle);

        String contourCurrentsString = ec.getContourCurrents().toString();
        sb.append(SEPARATOR)
          .append("Контурные токи\n")
          .append(contourCurrentsString, 1, contourCurrentsString.length() - 1)
          .append('\n');

        String currentsString = ec.getCurrents().toString();
        sb.append(SEPARATOR)
          .append("Токи в ветвях\n")
          .append(currentsString, 1, currentsString.length() - 1);

        return sb.toString();
    }
}
