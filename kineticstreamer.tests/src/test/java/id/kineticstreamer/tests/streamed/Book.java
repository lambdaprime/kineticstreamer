package id.kineticstreamer.tests.streamed;

import java.util.Objects;

import id.kineticstreamer.annotations.Streamed;

public class Book {

    @Streamed
    public String name;
    
    @Streamed
    public float price;

    @Streamed
    public Integer weight;

    @Streamed
    public Double rating;
    
    public String ignore = "ignore";

    public Book() {

    }

    public Book(String name, float price, double rating, int weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object obj) {
        Book other = (Book) obj;
        System.out.format("%s == %s\n", name, other.name);
        System.out.format("%s == %s\n", price, other.price);
        System.out.format("%s == %s\n", weight, other.weight);
        System.out.format("%s == %s\n", rating, other.rating);
        return Objects.equals(name, other.name) &&
                Objects.equals(price, other.price) &&
                Objects.equals(rating, other.rating) &&
                Objects.equals(weight, other.weight);
    }
}
