package com.example.shamsuddha.tourmate;

public class Memories {

    private String id;
    private String memoryDetails;
    private String uri;

    public Memories(String id, String memoryDetails, String uri) {
        this.id = id;
        this.memoryDetails = memoryDetails;
        this.uri = uri;
    }


    public Memories(String memoryDetails, String uri) {
        this.memoryDetails = memoryDetails;
        this.uri = uri;
    }

    public Memories() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemoryDetails() {
        return memoryDetails;
    }

    public void setMemoryDetails(String memoryDetails) {
        this.memoryDetails = memoryDetails;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
