// ReportGenerator - A simple report generator with a bug in it
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.math.BigDecimal;

public class ReportGenerator {
    public static void main(String[] args) throws Exception {
        String filename = "transactions.csv"; // Assume this is a large file

        // Problematic part: Reads ALL lines into memory at once!
        List<String> allLines = Files.readAllLines(Paths.get(filename));

        BigDecimal total = BigDecimal.ZERO;
        for (String line : allLines) {
            String[] columns = line.split(",");
            if (columns.length > 2) {
                total = total.add(new BigDecimal(columns[2]));
            }
        }

        System.out.println("Total transaction value: " + total);
    }

}