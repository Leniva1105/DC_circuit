package util;

import elements.Cycle;
import elements.CycleSet;
import elements.ElectricalCircuit;

public class ElectricalCircuitFormatter {

    private static final String SEPARATOR = "------------------------------\n";

    public static String format(ElectricalCircuit ec) {
        StringBuilder sb = new StringBuilder();
        sb.append(SEPARATOR)
          .append(ec.isCircuitContinuous() ? "Цепь замкнута\n" : "Цепь не замкнута\n")
          .append(ec.hasNoBridges() ? "Цепь не имеет мостов\n" : "Цепь содержит мосты\n");

        sb.append(SEPARATOR)
          .append("Список ветвей\n");
        for (elements.Branch branch : ec) {
            sb.append(branch).append("\n");
        }

        sb.append(SEPARATOR)
          .append("Список узлов\n")
          .append(ec.getAllNodes())
          .append('\n');

        sb.append(SEPARATOR)
          .append("Число компонент связности: ")
          .append(ec.getConnectedComponentsCount())
          .append('\n');

        CycleSet cs = new CycleSet(ec);

        sb.append(SEPARATOR)
          .append("Подходящая система циклов\n");
        for (Cycle cycle: cs) {
            sb.append(cycle)
              .append("\nСумма ЭДС = ")
              .append(cycle.sumEmf())
              .append('\n');
       }

        sb.append(SEPARATOR)
          .append("Токи в ветвях\n")
          .append(ec.getCurrents());

        return sb.toString();
    }
}
