// JvmInfoReporter - Java Environment Information Reporter
// Author: Venkatesh K
public class JvmInfoReporter {
    public static void main(String[] args) {
        System.out.println("--- Java Environment Information ---");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
        System.out.println("JVM Name: " + System.getProperty("java.vm.name"));
        System.out.println("JVM Version: " + System.getProperty("java.vm.version"));
        System.out.println("JRE Version: " + System.getProperty("java.runtime.version"));
        System.out.println("OS Name: " + System.getProperty("os.name"));
        System.out.println("OS Architecture: " + System.getProperty("os.arch"));
        System.out.println("User Home Directory: " + System.getProperty("user.home"));
        System.out.println("Java Class Path: " + System.getProperty("java.class.path"));
//        System.out.println("System Properties whole:"+ System.getProperties());
        System.out.println("--- End of Information ---");
    }
}
