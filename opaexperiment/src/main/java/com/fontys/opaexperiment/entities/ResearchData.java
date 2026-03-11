package com.fontys.opaexperiment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Just some basic mock data types for the experiment to make it look somewhat convincing
public class ResearchData {
    public int subjectID;
    public String subjectFirstName;
    public String subjectLastName;
    public int subjectAge;
    public String subjectCountry;
    public String dateOfRecording;
    public int averageHeartBeat;

    public ResearchData (int subjectID, String subjectFirstName, String subjectLastName, int subjectAge, String subjectCountry, String dateOfRecording, int averageHeartBeat){
        this.subjectID = subjectID;
        this.subjectFirstName = subjectFirstName;
        this.subjectLastName = subjectLastName;
        this.subjectAge = subjectAge;
        this.subjectCountry = subjectCountry;
        this.dateOfRecording = dateOfRecording;
        this.averageHeartBeat = averageHeartBeat;
    }

    public ResearchData(ResearchData other) {
        this.subjectID = other.subjectID;
        this.subjectFirstName = other.subjectFirstName;
        this.subjectLastName = other.subjectLastName;
        this.subjectAge = other.subjectAge;
        this.subjectCountry = other.subjectCountry;
        this.dateOfRecording = other.dateOfRecording;
        this.averageHeartBeat = other.averageHeartBeat;
    }

    @JsonIgnore
    public String getCompiledResearchDataString(){
        return "\n"
                + "Name of subject: " + subjectFirstName + " " + subjectLastName + "\n"
                + "Age of subject: " + subjectAge + "\n"
                + "Country of origin: " + subjectCountry + "\n"
                + "Date of recording: " + dateOfRecording + "\n"
                + "Average Heartbeat: " + averageHeartBeat;
    }
}
