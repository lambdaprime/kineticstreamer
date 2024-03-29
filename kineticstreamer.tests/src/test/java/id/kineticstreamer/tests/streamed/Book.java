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
package id.kineticstreamer.tests.streamed;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class Book {

    @Secret public String name;

    public float price;

    public Integer weight;

    public Double rating;

    public Author author;

    public transient String ignore = "ignore";

    public boolean hardcover;

    public Integer[] illustratedPages;

    public int[] emptyPages = new int[0];

    public final int[] ignoreFinal = new int[0];

    public char ch;

    public char[] md5 = new char[0];

    public float[] floatArray = new float[0];

    public Book() {}

    public Book(
            String name,
            float price,
            double rating,
            int weight,
            Author author,
            boolean hardcover,
            Integer[] illustratedPages,
            int[] emptyPages,
            char ch,
            char[] md5,
            float[] floatArray) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.rating = rating;
        this.author = author;
        this.hardcover = hardcover;
        this.illustratedPages = illustratedPages;
        this.emptyPages = emptyPages;
        this.ch = ch;
        this.md5 = md5;
        this.floatArray = floatArray;
    }

    @Override
    public boolean equals(Object obj) {
        Book other = (Book) obj;
        System.out.format("%s == %s\n", name, other.name);
        System.out.format("%s == %s\n", price, other.price);
        System.out.format("%s == %s\n", weight, other.weight);
        System.out.format("%s == %s\n", rating, other.rating);
        System.out.format("%s == %s\n", ch, other.ch);
        System.out.format("%s == %s\n", author, other.author);
        System.out.format("%s == %s\n", hardcover, other.hardcover);
        System.out.format(
                "%s == %s\n",
                Arrays.toString(illustratedPages), Arrays.toString(other.illustratedPages));
        System.out.format(
                "%s == %s\n", Arrays.toString(emptyPages), Arrays.toString(other.emptyPages));
        System.out.format("%s == %s\n", Arrays.toString(md5), Arrays.toString(other.md5));
        System.out.format(
                "%s == %s\n", Arrays.toString(floatArray), Arrays.toString(other.floatArray));
        return Objects.equals(name, other.name)
                && Objects.equals(price, other.price)
                && Objects.equals(rating, other.rating)
                && Objects.equals(weight, other.weight)
                && Objects.equals(ch, other.ch)
                && Objects.equals(hardcover, other.hardcover)
                && Arrays.equals(illustratedPages, other.illustratedPages)
                && Arrays.equals(emptyPages, other.emptyPages)
                && Arrays.equals(md5, other.md5)
                && Arrays.equals(floatArray, other.floatArray)
                && Objects.equals(author, other.author);
    }
}
