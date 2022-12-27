package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

public abstract class RepositoryBase<T> {
    abstract String getDatabaseFilePath();

    abstract List<T> getEntities();

    void createsDbFileIfNotExists() throws RepositoryException {
        File dbFile = new File(getDatabaseFilePath());
        dbFile.getParentFile().mkdirs();
        try {
            dbFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RepositoryException("Error in database file", "Error creating database file.");
        }
    }

    void persistEntitiesInFile() throws RepositoryException {
        createsDbFileIfNotExists();

        try {
            String filePath = String.format(getDatabaseFilePath());

            FileWriter writer = new FileWriter(filePath);

            String jsonEntities = new Gson().toJson(getEntities());
            writer.write(jsonEntities);
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    List<T> loadEntitiesFromFile(Class<T[]> clazz) throws RepositoryException {
        createsDbFileIfNotExists();

        // List<T> entities = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(getDatabaseFilePath()));

            StringBuffer dbAsjson = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                dbAsjson.append(line);
            }
            br.close();

            T[] jsonEntities = new Gson().fromJson(dbAsjson.toString(), clazz);
            if (jsonEntities != null) {
                return new ArrayList<>(Arrays.asList(jsonEntities));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
}
