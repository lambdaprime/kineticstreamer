package id.kineticstreamer.tests;

import id.kineticstreamer.annotations.Streamed;

public class Book {

    @Streamed
    public String name;
    
    @Streamed
    public int price;

    public String ignore = "ignore";

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }
    
}
