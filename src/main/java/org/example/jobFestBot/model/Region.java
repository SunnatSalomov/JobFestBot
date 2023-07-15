package org.example.jobFestBot.model;

public enum Region {
    ANDIJON("Andijon viloyati"),
    BUXORO("Buxoro viloyati"),
    FARGONA("Farg'ona viloyati"),
    JIZZAX("Jizzax viloyati"),
    XORAZM("Xorazm viloyati"),
    NAMANGAN("Namangan viloyati"),
    NAVOIY("Navoiy viloyati"),
    QASHQADARYO("Qashqadaryo viloyati"),
    QORAQALPOGISTON("Qoraqalpog'iston Respublikasi"),
    SAMARQAND("Samarqand viloyati"),
    SIRDARYO("Sirdaryo viloyati"),
    SURXONDARYO("Surxondaryo viloyati"),
    TOSHKENT("Toshkent viloyati");

    private final String nomi;


    Region(String nomi) {
        this.nomi = nomi;
    }

    public String getNomi() {
        return nomi;
    }
}