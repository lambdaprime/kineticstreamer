/*
 * Copyright 2020 kineticstreamer project
 * 
 * Website: https://github.com/lambdaprime/kineticstreamer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Authors:
 * - lambdaprime <intid@protonmail.com>
 */
package id.kineticstreamer.tests.streamed;

import java.util.Arrays;
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
    
    @Streamed
    public Author author;
    
    public String ignore = "ignore";

    @Streamed
    public boolean hardcover;
    
    @Streamed
    public Integer[] illustratedPages;
    
    @Streamed
    public int[] emptyPages;

    public Book() {

    }

    public Book(String name, float price, double rating, int weight, Author author, boolean hardcover,
            Integer[] illustratedPages, int[] emptyPages) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.rating = rating;
        this.author = author;
        this.hardcover = hardcover;
        this.illustratedPages = illustratedPages;
        this.emptyPages = emptyPages;
    }

    @Override
    public boolean equals(Object obj) {
        Book other = (Book) obj;
        System.out.format("%s == %s\n", name, other.name);
        System.out.format("%s == %s\n", price, other.price);
        System.out.format("%s == %s\n", weight, other.weight);
        System.out.format("%s == %s\n", rating, other.rating);
        System.out.format("%s == %s\n", author, other.author);
        System.out.format("%s == %s\n", hardcover, other.hardcover);
        System.out.format("%s == %s\n", Arrays.toString(illustratedPages), Arrays.toString(other.illustratedPages));
        System.out.format("%s == %s\n", Arrays.toString(emptyPages), Arrays.toString(other.emptyPages));
        return Objects.equals(name, other.name) &&
                Objects.equals(price, other.price) &&
                Objects.equals(rating, other.rating) &&
                Objects.equals(weight, other.weight) &&
                Objects.equals(hardcover, other.hardcover) &&
                Arrays.equals(illustratedPages, other.illustratedPages) &&
                Arrays.equals(emptyPages, other.emptyPages) &&
                Objects.equals(author, other.author);
    }
}
