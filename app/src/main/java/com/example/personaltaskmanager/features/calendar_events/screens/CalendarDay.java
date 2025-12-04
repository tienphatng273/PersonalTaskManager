package com.example.personaltaskmanager.features.calendar_events.screens;

/**
 * Model biểu diễn 1 ngày trong lịch tháng.
 * - day: số ngày (1..31)
 * - month / year
 * - isToday: có phải hôm nay?
 * - isSelected: đang được chọn?
 * - isInMonth: có thuộc tháng đang xem không? (để làm mờ các ngày thuộc tháng trước/sau)
 */
public class CalendarDay {

    public int day;
    public int month;
    public int year;

    public boolean isToday;
    public boolean isSelected;
    public boolean isInMonth;

    public CalendarDay(int day, int month, int year, boolean isToday, boolean isSelected, boolean isInMonth) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.isToday = isToday;
        this.isSelected = isSelected;
        this.isInMonth = isInMonth;
    }
}
