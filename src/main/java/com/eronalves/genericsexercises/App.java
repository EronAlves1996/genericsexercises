package com.eronalves.genericsexercises;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eronalves.genericsexercises.GenericCache.Pair;

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
    }

    private static void csvParser() {
        CSVParser<User> csvParser = new CSVParser<>(User.class);
        var user = csvParser.parseOne(List.of("name", "age"), 1, "José,18");
        if (user.isPresent()) {
            System.out.println(user.get());
        }
    }

    private static void genericCache() {
        GenericCache<CharSequence, Number> gc = new GenericCache<>(50);
        gc.putAll(List.of(new Pair<>("a", 2), new Pair<>("a", 25L)));
    }

    private static void pluginRegistry() {
        var registry = new PluginRegistry();
        registry.registerPlugin(String.class, "AAAA");
        registry.registerPlugin(Collection.class, List.of("a", "b", "c"));
        registry.registerPluginWithDeps(Collection.class, r -> List.of(r.getPlugin(String.class)));
        registry.registerPluginWithDeps(Collection.class, r -> Map.of(r.getPlugin(String.class), 2));
        registry.registerPlugin(Collection.class, new HashMap<String, Integer>());
    }
}
