package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.HashSet;

public class Stats {
    
    private HashSet<String> platsDistansSet;
    private HashSet<String> uniSet; 
    private HashSet<String> ortSet;
    private HashSet<String> utbildningstypSet;
    private HashSet<String> examenSet;

    private BufferedWriter platsDistansWriter;
    private BufferedWriter uniWriter;
    private BufferedWriter ortWriter;
    private BufferedWriter utbildningstypWriter;
    private BufferedWriter examenWriter;

    public Stats (){
        this.uniSet = new HashSet<>();
        this.ortSet = new HashSet<>();
        this.utbildningstypSet = new HashSet<>();
        this.examenSet = new HashSet<>();
        this.platsDistansSet = new HashSet<>();
        try{
            this.platsDistansWriter = new BufferedWriter(new FileWriter("C:\\Users\\oxxer\\projekt\\studera\\demo\\src\\main\\resources\\Stats\\PlatsDistans.json"));
            this.examenWriter = new BufferedWriter(new FileWriter("C:\\Users\\oxxer\\projekt\\studera\\demo\\src\\main\\resources\\Stats\\Examen.json"));
            this.ortWriter = new BufferedWriter(new FileWriter("C:\\Users\\oxxer\\projekt\\studera\\demo\\src\\main\\resources\\Stats\\Ort.json"));
            this.utbildningstypWriter = new BufferedWriter(new FileWriter("C:\\Users\\oxxer\\projekt\\studera\\demo\\src\\main\\resources\\Stats\\Utbildningstyp.json"));
            this.uniWriter = new BufferedWriter(new FileWriter("C:\\Users\\oxxer\\projekt\\studera\\demo\\src\\main\\resources\\Stats\\Universitet.json"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void doStats(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(new File("C:/Users/oxxer/projekt/studera/demo/src/main/resources/jsonUtbildningar.json"));
            
            if (rootNode.isArray()) {
                for (JsonNode courseNode : rootNode) {
                    // Access specific fields
                    String namn = courseNode.path("Namn").asText();
                    String universitet = courseNode.path("Universitet").asText();
                    String ort = courseNode.path("Ort").asText();
                    String utbildningstyp = courseNode.path("Utbildningstyp").asText();
                    String platsDistans = courseNode.path("PlatsDistans").asText();
                    String link = courseNode.path("Link").asText();
                    String examen = courseNode.path("Examen").asText();
                    // Add to sets
                    uniSet.add(universitet);
                    ortSet.add(ort);
                    utbildningstypSet.add(utbildningstyp);
                    examenSet.add(examen);
                    platsDistansSet.add(platsDistans);

                    

                    // Print or process the fields
                    System.out.println("Course Name: " + namn);
                    System.out.println("University: " + universitet);
                    System.out.println("Location: " + ort);
                    System.out.println("Type: " + utbildningstyp);
                    System.out.println("Distance or On-site: " + platsDistans);
                    System.out.println("Link: " + link);
                    System.out.println("-------------------------");
                }
            }

            printAllData();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAllData(){
        try{
            for(String s : uniSet){
                uniWriter.write(s + "\n");
            }

            for(String s : ortSet){
                ortWriter.write(s + "\n");
            }

            for(String s : utbildningstypSet){
                utbildningstypWriter.write(s + "\n");
            }

            for(String s : examenSet){
                examenWriter.write(s + "\n");
            }

            for(String s : platsDistansSet){
                platsDistansWriter.write(s + "\n");
            }

            examenWriter.flush();
            ortWriter.flush();
            utbildningstypWriter.flush();
            uniWriter.flush();
            platsDistansWriter.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }

        
    }

}
