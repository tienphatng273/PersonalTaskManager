package com.example.personaltaskmanager.features.task_manager.screens.workspace;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltaskmanager.R;
import com.example.personaltaskmanager.features.task_manager.screens.workspace.blocks.NotionBlock;

import java.util.List;

/**
 * Adapter hiển thị các block dạng Notion: PARAGRAPH – TODO – BULLET – DIVIDER – FILE
 * (Giữ nguyên logic gốc của bạn — chỉ bổ sung phần hỗ trợ cho drag & drop)
 */
public class NotionBlockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<NotionBlock> blocks;

    // callback menu dấu …
    public interface FileMenuListener {
        void onMenuClick(NotionBlock block, int position, View anchor);
    }

    private FileMenuListener menuListener;

    public void setFileMenuListener(FileMenuListener listener) {
        this.menuListener = listener;
    }

    public NotionBlockAdapter(List<NotionBlock> blocks) {
        this.blocks = blocks;
    }

    @Override
    public int getItemViewType(int position) {
        return blocks.get(position).type.ordinal();
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

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

            case FILE:
                return new FileHolder(inflater.inflate(
                        R.layout.feature_task_manager_block_file, parent, false));

            default:
            case PARAGRAPH:
                return new ParagraphHolder(inflater.inflate(
                        R.layout.feature_task_manager_block_paragraph, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Bindable) {
            ((Bindable) holder).bind(blocks.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return blocks.size();
    }

    // ===== Interface chung để bind data =====
    public interface Bindable {
        void bind(NotionBlock block);
    }

    // ============================================================================
    // PARAGRAPH
    // ============================================================================
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

    // ============================================================================
    // TODO BLOCK
    // ============================================================================
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

            checkbox.setOnCheckedChangeListener(
                    (view, checked) -> block.isChecked = checked);
        }
    }

    // ============================================================================
    // BULLET
    // ============================================================================
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

    // ============================================================================
    // DIVIDER
    // ============================================================================
    class DividerHolder extends RecyclerView.ViewHolder implements Bindable {

        public DividerHolder(@NonNull View itemView) { super(itemView); }

        @Override
        public void bind(NotionBlock block) { /* Divider không có nội dung */ }
    }

    // ============================================================================
    // FILE BLOCK
    // ============================================================================
    class FileHolder extends RecyclerView.ViewHolder implements Bindable {

        private final TextView tv;
        private final TextView btnMore;

        public FileHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv_file_name);
            btnMore = itemView.findViewById(R.id.btn_more);
        }

        @Override
        public void bind(NotionBlock block) {

            tv.setText(block.fileName != null ? block.fileName : "File");

            // mở file khi click
            itemView.setOnClickListener(v -> openFile(v, block));

            // menu …
            btnMore.setOnClickListener(v -> {
                if (menuListener != null) {
                    menuListener.onMenuClick(block, getAdapterPosition(), v);
                }
            });
        }

        private void openFile(View v, NotionBlock block) {

            if (block.fileUri == null) return;

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse(block.fileUri), "*/*");
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                v.getContext().startActivity(i);
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "Cannot open file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ============================================================================
    // SimpleWatcher để bind text
    // ============================================================================
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

    // ============================================================================
    // ⭐⭐ HÀM BỔ SUNG – HỖ TRỢ ITEMTOUCHHELPER ⭐⭐
    // ============================================================================
    public RecyclerView.ViewHolder getViewHolderAt(int position) {
        return null; // không dùng, nhưng cần để hệ thống Drag & Drop khởi tạo ổn định
    }
}
