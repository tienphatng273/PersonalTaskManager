package com.example.personaltaskmanager.features.calendar_events.screens;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltaskmanager.R;

import java.util.List;

/**
 * Adapter hiển thị lịch tháng dưới dạng lưới (7 cột × 6 hàng).
 * Dựa theo CalendarMonthActivity cung cấp danh sách CalendarDay.
 */
public class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayAdapter.DayViewHolder> {

    private List<CalendarDay> data;
    private OnDayClickListener listener;

    public interface OnDayClickListener {
        void onDayClick(CalendarDay day);
    }

    public CalendarDayAdapter(List<CalendarDay> data, OnDayClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public void updateData(List<CalendarDay> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feature_calendar_item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {

        CalendarDay day = data.get(position);
        holder.tvDay.setText(String.valueOf(day.day));

        // Ngày thuộc tháng khác → mờ đi
        holder.tvDay.setAlpha(day.isInMonth ? 1f : 0.3f);

        // Nếu là today → màu vàng nhạt
        if (day.isToday) {
            holder.tvDay.setBackgroundTintList(ColorStateList.valueOf(
                    holder.itemView.getResources().getColor(R.color.calendar_today_bg)
            ));
        } else {
            holder.tvDay.setBackgroundTintList(null);
        }

        // Nếu được chọn → highlight xanh
        if (day.isSelected) {
            holder.tvDay.setBackgroundResource(R.drawable.bg_calendar_selected);
        } else {
            holder.tvDay.setBackground(null);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onDayClick(day);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_calendar_day);
        }
    }
}
