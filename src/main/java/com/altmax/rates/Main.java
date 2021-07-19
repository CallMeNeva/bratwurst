package com.altmax.rates;

import javafx.application.Application;

/* For more information: https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing */
public final class Main {

    public static void main(String[] args) {
        /* Don't really wanna bother with the CLI in this project */
        if (args.length > 0) {
            System.out.println("Usage: java " + App.getName().toLowerCase());
        } else {
            Application.launch(App.class, args);
        }
    }
}
