package app.virus_simulation.memento;

import app.virus_simulation.inter.IPopulation;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class MementoCareTaker {

    private PopulationMemento populationMemento;

    public void create(IPopulation population) {
        populationMemento = population.takeSnapshot();
    }

    public void restore(IPopulation population) {
        population.restoreMemento(populationMemento);
    }

    private final String SERIALIZED_FILE_NAME = "snapshot.xml";

    /**
     * Serializes current mementos to file
     */
    public void encode() {
        XMLEncoder encoder=null;
        try{
            encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
        }catch(FileNotFoundException fileNotFound){
            System.out.println("ERROR: While Creating or Opening the File " + SERIALIZED_FILE_NAME);
        }
        encoder.writeObject(populationMemento);
        encoder.close();

    }

    /**
     * Deserializes data from file as mementos
     */
    public void decode() {
        XMLDecoder decoder=null;
        try {
            decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File " + SERIALIZED_FILE_NAME + " not found");
        }
        populationMemento = (PopulationMemento) decoder.readObject();

    }

}
