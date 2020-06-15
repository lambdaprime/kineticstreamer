package id.kineticstreamer.tests.streamed;

import java.util.Objects;

import id.kineticstreamer.annotations.Streamed;

public class Author {

    @Streamed
    public String name;
    
    @Streamed
    public int booksNum;

    public Author() {

    }

    public Author(String name, int booksNum) {
        this.name = name;
        this.booksNum = booksNum;
    }

    @Override
    public boolean equals(Object obj) {
        Author other = (Author) obj;
        System.out.format("%s == %s\n", name, other.name);
        System.out.format("%s == %s\n", booksNum, other.booksNum);
        return Objects.equals(name, other.name) &&
                Objects.equals(booksNum, other.booksNum);
    }
}
