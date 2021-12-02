import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeGsonExample {

    public static void main(String[] args) throws IOException {
        Employee emp = createEmployee();

        // create a Gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // read Json file data as string
        String fileData = new String(Files.readAllBytes(Paths.get("C:\\Users\\xuyin\\IdeaProjects\\GSON\\src\\main\\resources\\employee.txt")));

        // parse json string to object
        Employee employee1 = gson.fromJson(fileData, Employee.class);

        // print Object data
        System.out.println("\n\nEmployee Object\n\n" + employee1);

        // create JSON String from Object
        String jsonEmp = gson.toJson(emp);
        System.out.print(jsonEmp);
    }

    private static Employee createEmployee() {
        Employee emp = new Employee();
        emp.setId(100);
        emp.setName("Xuying");
        emp.setPermanent(false);
        emp.setPhoneNumbers(new long[]{123456, 987654});
        emp.setRole("Manager");

        Address add = new Address();
        add.setCity("Bangalore");
        add.setStreet("BTM 1st Stage");
        add.setZipcode(560100);
        emp.setAddress(add);

        List<String> cities = new ArrayList<String>();
        cities.add("Los Angeles");
        cities.add("New York");
        emp.setCities(cities);

        Map<String, String> props = new HashMap<String, String>();
        props.put("salary", "1000 Rs");
        props.put("age", "28 years");
        emp.setProperties(props);

        return emp;
    }
}
