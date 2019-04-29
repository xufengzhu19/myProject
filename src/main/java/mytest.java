import utils.DateBuilder;
import utils.NumberUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class mytest {

    public static void main(String[] args) {
        LocalDateTime targetHour = LocalDateTime.now().minusHours(17);
        LocalDate targetDate = targetHour.toLocalDate();
        String targetDateStr = targetDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(targetDateStr);
    }
}
