import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class cloudMapLoader {
    private List<double[]> points = new ArrayList<>();

    public cloudMapLoader(String filePath) {
        loadPoints(filePath);
    }

    public List<double[]> getPoints() {
        return points;
    }

    private void loadPoints(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                double x = Double.parseDouble(tokens[0]);
                double y = Double.parseDouble(tokens[1]);
                points.add(new double[]{x, y});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // randomly sample a point from the list
    public double[] samplePoint() {
        Random rand = new Random();
        return points.get(rand.nextInt(points.size()));
    }
}