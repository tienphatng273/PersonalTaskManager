package com.example.personaltaskmanager.features.task_manager.screens.workspace;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltaskmanager.R;
import com.example.personaltaskmanager.features.task_manager.screens.workspace.blocks.NotionBlock;

import java.util.Collections;
import java.util.List;

public class NotionBlockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // ---------------------------------------------------------
    // DATA SOURCE
    // ---------------------------------------------------------
    private final List<NotionBlock> blocks;

    public NotionBlockAdapter(List<NotionBlock> blocks) {
        this.blocks = blocks;
    }

    // ---------------------------------------------------------
    // DRAG & DROP SUPPORT
    // ---------------------------------------------------------
    public void onItemMove(int fromPos, int toPos) {
        if (fromPos < 0 || toPos < 0 || fromPos >= blocks.size() || toPos >= blocks.size()) return;
        Collections.swap(blocks, fromPos, toPos);
        notifyItemMoved(fromPos, toPos);
    }

    // ---------------------------------------------------------
    // VIEW TYPE
    // ---------------------------------------------------------
    @Override
    public int getItemViewType(int position) {
        return blocks.get(position).type.ordinal();
    }

    // ---------------------------------------------------------
    // CREATE HOLDER
    // ---------------------------------------------------------
    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NotionBlock.Type type = NotionBlock.Type.values()[viewType];

        switch (type) {

            case TODO:
                return new TodoHolder(inflater.inflate(
                        R.layout.feature_task_manager_block_todo, parent, false));

            case BULLET:
                return new BulletHolder(inflater.inflate(
                        R.layout.feature_task_manager_block_bullet, parent, false));

            case DIVIDER:
                return new DividerHolder(inflater.inflate(
                        R.layout.feature_task_manager_block_divider, parent, false));

            default:
            case PARAGRAPH:
                return new ParagraphHolder(inflater.inflate(
                        R.layout.feature_task_manager_block_paragraph, parent, false));
        }
    }

    // ---------------------------------------------------------
    // BIND HOLDER
    // ---------------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Bindable) {
            ((Bindable) holder).bind(blocks.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return blocks.size();
    }

    // ---------------------------------------------------------
    // INTERFACE
    // ---------------------------------------------------------
    public interface Bindable {
        void bind(NotionBlock block);
    }

    // =====================================================================
    // 4 HOLDER — KHÔNG ĐỔI UI, CHỈ THÊM LOGIC LƯU DỮ LIỆU REAL-TIME
    // =====================================================================


    // ---------------- PARAGRAPH ----------------
    class ParagraphHolder extends RecyclerView.ViewHolder implements Bindable {

        private final EditText edt;
        private TextWatcher watcher;

        public ParagraphHolder(@NonNull View itemView) {
            super(itemView);
            edt = itemView.findViewById(R.id.edt_paragraph);
        }

        @Override
        public void bind(NotionBlock block) {

            if (watcher != null) edt.removeTextChangedListener(watcher);
            edt.setText(block.text);

            watcher = new SimpleWatcher(text -> block.text = text);
            edt.addTextChangedListener(watcher);
        }
    }


    // ---------------- TODO ----------------
    class TodoHolder extends RecyclerView.ViewHolder implements Bindable {

        private final EditText edt;
        private final CheckBox checkbox;
        private TextWatcher watcher;

        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            edt = itemView.findViewById(R.id.edt_todo);
            checkbox = itemView.findViewById(R.id.check_todo);
        }

        @Override
        public void bind(NotionBlock block) {

            if (watcher != null) edt.removeTextChangedListener(watcher);

            edt.setText(block.text);
            checkbox.setChecked(block.isChecked);

            watcher = new SimpleWatcher(text -> block.text = text);
            edt.addTextChangedListener(watcher);

            checkbox.setOnCheckedChangeListener((btn, checked) -> block.isChecked = checked);
        }
    }


    // ---------------- BULLET ----------------
    class BulletHolder extends RecyclerView.ViewHolder implements Bindable {

        private final EditText edt;
        private TextWatcher watcher;

        public BulletHolder(@NonNull View itemView) {
            super(itemView);
            edt = itemView.findViewById(R.id.edt_bullet);
        }

        @Override
        public void bind(NotionBlock block) {

            if (watcher != null) edt.removeTextChangedListener(watcher);

            edt.setText(block.text);

            watcher = new SimpleWatcher(text -> block.text = text);
            edt.addTextChangedListener(watcher);
        }
    }


    // ---------------- DIVIDER ----------------
    class DividerHolder extends RecyclerView.ViewHolder implements Bindable {

        public DividerHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(NotionBlock block) {
            // Divider không có nội dung
        }
    }

    // =====================================================================
    // SIMPLE WATCHER (GIÚP GỌN CODE)
    // =====================================================================

    private static class SimpleWatcher implements TextWatcher {

        interface Listener {
            void onTextChanged(String text);
        }

        private final Listener listener;

        SimpleWatcher(Listener l) { this.listener = l; }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            listener.onTextChanged(s.toString());
        }

        @Override public void afterTextChanged(Editable s) {}
    }
}
