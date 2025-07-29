public class CelsiusToFahrenheit {
    public static void main(String[] args) {
        // Declare and initialize a double variable to hold the Celsius temperature.
        double celsius = 25.0;

        // Perform the temperature conversion using the formula: F = C × 9/5 + 32
        // 9.0/5.0 ensures floating-point division and accurate results.
        double fahrenheit = celsius * 9.0 / 5.0 + 32;

        // Concatenate and print the results in a readable format.
        System.out.println(celsius + " degrees Celsius is equal to " + fahrenheit + " degrees Fahrenheit.");
    }
}
