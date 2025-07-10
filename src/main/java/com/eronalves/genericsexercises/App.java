package com.eronalves.genericsexercises;

import java.util.List;

/**
 * Hello world!
 */
public class App {

    public static class User {
        private String age;
        private String name;

        public User() {
        }

        public String getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User [age=" + age + ", name=" + name + "]";
        }

    }

    public static void main(String[] args) {
        CSVParser<User> csvParser = new CSVParser<>(User.class);
        var user = csvParser.parseOne(List.of("name", "age"), 1, "José,18");
        if (user.isPresent()) {
            System.out.println(user.get());
        }
    }
}
