import elements.Branch;
import elements.ElectricalCircuit;
import util.ElectricalCircuitFormatter;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Branch> branches = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
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
            branches.add(b);
        }

        ElectricalCircuit ec = new ElectricalCircuit(branches);
        System.out.println(ElectricalCircuitFormatter.format(ec));
    }
}
