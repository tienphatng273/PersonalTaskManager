package com.example.personaltaskmanager.features.calendar_events.screens;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltaskmanager.R;
import com.example.personaltaskmanager.features.calendar_events.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * CalendarMonthActivity
 * -----------------------
 * Màn hình lịch tháng:
 *  - Chuyển tháng trước/sau
 *  - Chọn ngày
 *  - Hiển thị ngày chọn bên dưới
 *  - Dùng RecyclerView (7 cột)
 */
public class CalendarMonthActivity extends AppCompatActivity {

    private TextView tvMonthTitle, tvSelectedDate;
    private ImageButton btnPrev, btnNext;
    private RecyclerView rvCalendar;

    private CalendarDayAdapter adapter;

    private int selectedDay = -1;
    private int selectedMonth = -1;
    private int selectedYear = -1;

    private Calendar current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_calendar_month);

        setLightStatusBar();

        current = Calendar.getInstance();

        initViews();
        setupRecyclerView();
        loadMonth();

        btnPrev.setOnClickListener(v -> {
            current.add(Calendar.MONTH, -1);
            loadMonth();
        });

        btnNext.setOnClickListener(v -> {
            current.add(Calendar.MONTH, 1);
            loadMonth();
        });
    }

    private void initViews() {
        tvMonthTitle = findViewById(R.id.tv_month_title);
        tvSelectedDate = findViewById(R.id.tv_selected_date);

        btnPrev = findViewById(R.id.btn_prev_month);
        btnNext = findViewById(R.id.btn_next_month);
        rvCalendar = findViewById(R.id.rv_calendar_days);
    }

    private void setupRecyclerView() {
        rvCalendar.setLayoutManager(new GridLayoutManager(this, 7));
        adapter = new CalendarDayAdapter(new ArrayList<>(), this::onDayClick);
        rvCalendar.setAdapter(adapter);
    }

    private void onDayClick(CalendarDay day) {
        selectedDay = day.day;
        selectedMonth = day.month;
        selectedYear = day.year;

        tvSelectedDate.setText("Công việc ngày: " +
                DateUtils.formatYMD(selectedYear, selectedMonth, selectedDay));

        loadMonth();
    }

    /**
     * Sinh toàn bộ danh sách ngày theo tháng hiện tại.
     */
    private void loadMonth() {

        int month = current.get(Calendar.MONTH);
        int year = current.get(Calendar.YEAR);

        tvMonthTitle.setText(current.getDisplayName(Calendar.MONTH, Calendar.LONG, getResources().getConfiguration().locale)
                + " " + year);

        List<CalendarDay> days = new ArrayList<>();

        Calendar temp = (Calendar) current.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK) - 1; // 1=Sun → 0
        int daysInMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Add padding from previous month
        for (int i = 0; i < firstDayOfWeek; i++) {
            days.add(new CalendarDay(0, month, year, false, false, false));
        }

        // Add actual days
        for (int d = 1; d <= daysInMonth; d++) {
            boolean isToday = DateUtils.isToday(year, month, d);
            boolean isSelected =
                    d == selectedDay &&
                            month == selectedMonth &&
                            year == selectedYear;

            days.add(new CalendarDay(d, month, year, isToday, isSelected, true));
        }

        adapter.updateData(days);
    }

    private void setLightStatusBar() {
        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController controller = window.getInsetsController();
            if (controller != null) {
                controller.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            }
        } else {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
        }
    }
}
