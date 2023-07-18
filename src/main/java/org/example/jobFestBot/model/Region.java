package org.example.jobFestBot.model;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
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

    @JsonValue
    private final String nomi; //nomi ? change to english
}
