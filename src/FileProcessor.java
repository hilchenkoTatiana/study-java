import java.io.*;
import java.util.*;

public class FileProcessor {

    public static void main(String[] args) {
        String inputFilePath = "src/input.txt";
        String outputFilePath = "src/output.txt";

        try {
            // Read lines from the input file
            List<String> lines = readLinesFromFile(inputFilePath);

            // Parse lines into Person objects
            List<Person> persons = new ArrayList<>();
            for (String line : lines) {
                persons.add(parseLineToPerson(line));
            }

            // Sort persons using TreeSet with custom comparator
            TreeSet<Person> sortedPersons = new TreeSet<>(new PersonComparator());
            sortedPersons.addAll(persons);

            // Write sorted persons to the output file
            writePersonsToFile(sortedPersons, outputFilePath);

            System.out.println("Processing complete. Check the output file: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }

    private static List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static Person parseLineToPerson(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        int age = Integer.parseInt(parts[2].trim());
        return new Person(id, name, age);
    }

    private static void writePersonsToFile(Collection<Person> persons, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Person person : persons) {
                writer.write(person.toString());
                writer.newLine();
            }
        }
    }
}

class Person {
    private final int id;
    private final String name;
    private final int age;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + age;
    }
}

class PersonComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        int nameComparison = p1.getName().compareTo(p2.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }
        return Integer.compare(p1.getAge(), p2.getAge());
    }
}