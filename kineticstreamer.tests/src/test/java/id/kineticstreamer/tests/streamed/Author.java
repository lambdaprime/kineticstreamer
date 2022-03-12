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

import java.util.Objects;

/** @author lambdaprime intid@protonmail.com */
public class Author {

    public String name;

    public int booksNum;

    private int ignoredField = 10;

    public Author() {}

    public Author(String name, int booksNum) {
        this.name = name;
        this.booksNum = booksNum;
    }

    @Override
    public boolean equals(Object obj) {
        Author other = (Author) obj;
        System.out.format("%s == %s\n", name, other.name);
        System.out.format("%s == %s\n", booksNum, other.booksNum);
        return Objects.equals(name, other.name) && Objects.equals(booksNum, other.booksNum);
    }
}
