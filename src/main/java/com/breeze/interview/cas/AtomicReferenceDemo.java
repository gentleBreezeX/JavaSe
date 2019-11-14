package com.breeze.interview.cas;


import java.util.concurrent.atomic.AtomicReference;

class User{
    String username;
    int age;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public User() {
    }

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }
}

/**
 * 原子引用
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {

        User z3 = new User("z3",22);
        User l4 = new User("l4",26);

        AtomicReference<User> atomicReference = new AtomicReference<>();

        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, l4) +
                "\t" + atomicReference.get().toString());

        System.out.println(atomicReference.compareAndSet(z3, l4) +
                "\t" + atomicReference.get().toString());

    }
}
