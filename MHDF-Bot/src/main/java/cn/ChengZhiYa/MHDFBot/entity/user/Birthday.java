package cn.ChengZhiYa.MHDFBot.entity.user;

import lombok.Data;

@Data
public final class Birthday {
    int year;
    int month;
    int day;

    public Birthday(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
