package be.howest.ti.mars.logic.classes;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Company {
    private final int id;
    private final String name;
    private final String password;
    private final String email;
    private final String phone;
    private String storage;
    private final Map<Resource, Double> resources;
    private final Deque<Notification> notifications;

    public Company(int id, String name, String password, String email, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.resources = new HashMap<>();
        this.notifications = new LinkedList<>();
        this.storage = null;
    }
}
