package com.example.personaltaskmanager.features.task_manager.screens.workspace;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltaskmanager.R;
import com.example.personaltaskmanager.features.task_manager.data.model.Task;
import com.example.personaltaskmanager.features.task_manager.screens.workspace.blocks.NotionBlock;
import com.example.personaltaskmanager.features.task_manager.screens.workspace.blocks.NotionBlockParser;
import com.example.personaltaskmanager.features.task_manager.viewmodel.TaskViewModel;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class TaskWorkspaceActivity extends AppCompatActivity {

    /** VIEW */
    private RecyclerView rvWorkspace;
    private Chip btnAddParagraph, btnAddTodo, btnAddBullet, btnAddDivider;
    private ImageButton btnBack;

    private TextView tvTaskTitle, tvTaskDeadline;

    /** DATA */
    private final List<NotionBlock> blocks = new ArrayList<>();
    private NotionBlockAdapter adapter;
    private TaskViewModel vm;
    private Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_task_manager_workspace);

        vm = new ViewModelProvider(this).get(TaskViewModel.class);

        int taskId = getIntent().getIntExtra("task_id", -1);
        task = vm.getTaskById(taskId);

        initViews();
        initRecycler();
        applyTaskInfo();
        loadBlocks();
        setupActions();
    }


    /** INIT UI */
    private void initViews() {

        rvWorkspace = findViewById(R.id.rv_workspace);

        btnAddParagraph = findViewById(R.id.btn_add_paragraph);
        btnAddTodo = findViewById(R.id.btn_add_todo);
        btnAddBullet = findViewById(R.id.btn_add_bullet);
        btnAddDivider = findViewById(R.id.btn_add_divider);

        btnBack = findViewById(R.id.btn_back_ws);

        tvTaskTitle = findViewById(R.id.tv_task_title);
        tvTaskDeadline = findViewById(R.id.tv_task_deadline);
    }


    /** INIT RECYCLER */
    private void initRecycler() {
        rvWorkspace.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotionBlockAdapter(blocks);
        rvWorkspace.setAdapter(adapter);
    }


    /** SHOW TASK INFO IN TOP BAR */
    private void applyTaskInfo() {

        if (task != null) {

            tvTaskTitle.setText(task.getTitle());

            long dl = task.getDeadline();

            if (dl > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                tvTaskDeadline.setText(sdf.format(dl));
            } else {
                tvTaskDeadline.setText("No deadline");
            }
        }
    }



    /** LOAD BLOCKS */
    private void loadBlocks() {
        blocks.clear();
        blocks.addAll(NotionBlockParser.fromJson(task.getNotesJson()));
        adapter.notifyDataSetChanged();
    }


    /** HANDLE BUTTON CLICKS */
    private void setupActions() {

        btnBack.setOnClickListener(v -> {
            save();
            finish();
        });

        btnAddParagraph.setOnClickListener(v ->
                addBlock(NotionBlock.Type.PARAGRAPH));

        btnAddTodo.setOnClickListener(v ->
                addBlock(NotionBlock.Type.TODO));

        btnAddBullet.setOnClickListener(v ->
                addBlock(NotionBlock.Type.BULLET));

        btnAddDivider.setOnClickListener(v ->
                addBlock(NotionBlock.Type.DIVIDER));
    }


    /** ADD BLOCK */
    private void addBlock(NotionBlock.Type type) {

        blocks.add(new NotionBlock(
                UUID.randomUUID().toString(),
                type,
                "",
                false
        ));

        adapter.notifyItemInserted(blocks.size() - 1);
        rvWorkspace.scrollToPosition(blocks.size() - 1);
    }


    /** SAVE BLOCKS */
    private void save() {

        String json = NotionBlockParser.toJson(blocks);
        task.setNotesJson(json);

        vm.updateTask(
                task,
                task.getTitle(),
                task.getDescription(),
                task.getDeadline()
        );
    }
}
