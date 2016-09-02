/**
 * COMMERCIAL USE OF THIS SOFTWARE WITHOUT WARRANTY IS NOT ALLOWED.
 * Use is subject to license terms! You can distribute a copy of this software
 * to others for free. This software is to be a non-profit project in the future.
 * All rights reserved! Owned by Stephen Liu.
 */
package com.github.maya.domain;

/**
 * @author ste7en.liu@gmail.com
 * @since 2016/9/1
 */
public class User {
    private int id;
    private String name;
    private char gender;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
