package ru.shev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Integer id;
    private String name;
    private String date;
    private List<CatDTO> cats;
    public Date getConvertedDate(String userTimeZone) throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone(userTimeZone));
        return dateFormat.parse(this.date);
    }

    public void setDate(Date date, String timeZone) {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        this.date = dateFormat.format(date);
    }
}
