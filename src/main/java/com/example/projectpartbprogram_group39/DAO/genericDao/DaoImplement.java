package com.example.projectpartbprogram_group39.DAO.genericDao;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.EntityMapper;
import com.example.projectpartbprogram_group39.Models.Workouts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DaoImplement<T> implements DaoInterface<T>{

    private final String filePath;
    private final EntityMapper<T> entityMapper;

    public DaoImplement(String filePath, EntityMapper<T> entityMapper) {
        this.filePath = filePath;
        this.entityMapper = entityMapper;
    }

    @Override
    public void add(Object object) throws IOException {
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(entityMapper.toString((T) object));
            writer.newLine();
        }

    }

    @Override
    public List getAll() throws IOException {
        List<T> objects = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return objects;
        }

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            objects.add(entityMapper.fromString(line));
        }
        return objects;
    }

    @Override
    public void delete(T object) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            return;
        }

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            T existingObject = entityMapper.fromString(line);
            if (!entityMapper.equals(object, existingObject)) {
                updatedLines.add(line);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }
    }

    @Override
    public boolean exists(T object) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            T existingObject = entityMapper.fromString(line);

            if (object instanceof Workouts) {
                Workouts temp = (Workouts) object;
                Workouts existingWorkout = (Workouts) existingObject;

                if (existingWorkout.getType().equals(temp.getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(Object oldObject, Object newObject) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            return;
        }

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            T existingObject = entityMapper.fromString(line);
            if (entityMapper.equals((T) oldObject, existingObject)) {
                updatedLines.add(entityMapper.toString((T) newObject));
            } else {
                updatedLines.add(line);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }
    }
}
