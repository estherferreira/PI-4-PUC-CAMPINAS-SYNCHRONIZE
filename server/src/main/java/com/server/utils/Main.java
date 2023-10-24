package com.server.utils;

public class Main {
    public static void main(String[] args) {
        try {
            Data date = new Data((byte) 19, (byte) 1, (short) 2011);
            System.out.println(date);
        } catch (Exception e) {
        }

    }
}
