// FixedReportGenerator - the bug is fixed
import java.nio.file.Files;
import java.nio.file.Paths;
import java.math.BigDecimal;
import java.util.stream.Stream;

public class FixedReportGenerator {
    public static void main(String[] args) throws Exception {
        String filename = "transactions.csv";

        BigDecimal total;
        // Use a try-with-resources block to ensure the file stream is closed
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            total = lines
                    .map(line -> line.split(","))
                    .filter(columns -> columns.length > 2)
                    .map(columns -> new BigDecimal(columns[2]))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        System.out.println("Total transaction value: " + total);
    }
}