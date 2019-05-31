package bdata;

import akka.dispatch.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WordCount {
    public static void main(String[] args) {
        LocalDate date=LocalDate.now();
        System.out.println(date);
        System.out.println(date.format(DateTimeFormatter.ISO_DATE));
    }

}
