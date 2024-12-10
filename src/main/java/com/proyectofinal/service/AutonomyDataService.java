package com.proyectofinal.service;

import com.proyectofinal.persistence.entities.Autonomy;
import com.proyectofinal.persistence.repositories.AutonomyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Service
public class AutonomyDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AutonomyRepository autonomyRepository;

    // Path to the data.sql file (make sure to place it in resources folder)
    private static final String DATA_SQL_PATH = "src/main/resources/data.sql";

    @Transactional
    public void insertDataFromSQLFile() throws IOException, SQLException {
        // Read the SQL file and extract insert data
        BufferedReader br = new BufferedReader(new FileReader(DATA_SQL_PATH));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("INSERT INTO")) {
                // Extract values within parentheses from the INSERT statement
                String valuesPart = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                // Split the values by "),("
                String[] values = valuesPart.split("\\),\\(");

                for (String value : values) {
                    // Split each value by comma and trim the spaces
                    String[] data = value.split(",");
                    
                    // Ensure the data has 3 parts: latitude, longitude, and name
                    if (data.length == 3) {
                        try {
                            // Parse latitude and longitude as double, and clean up name
                            double latitude = Double.parseDouble(data[0].trim());  // Parse latitude
                            double longitude = Double.parseDouble(data[1].trim()); // Parse longitude
                            String name = data[2].trim().replace("'", "");  // Remove single quotes around name

                            // Check if record exists, and if not, insert it
                            if (!autonomyRepository.existsByNameAndLatitudeAndLongitude(name, latitude, longitude)) {
                                Autonomy autonomy = new Autonomy(name, latitude, longitude);
                                autonomyRepository.save(autonomy);
                            }
                        } catch (NumberFormatException e) {
                            // Handle invalid number format or invalid data
                            System.out.println("Error parsing latitude/longitude: " + Arrays.toString(data));
                        }
                    } else {
                        // Handle the case where the data format is incorrect
                        System.out.println("Invalid line format: " + line);
                    }
                }
            }
        }
        br.close();
    }

}
