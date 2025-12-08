package com.example.personaltaskmanager.features.task_manager.screens.workspace.blocks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltaskmanager.features.task_manager.screens.workspace.MoveHandler;

public class BlockDragCallback extends ItemTouchHelper.Callback {

    private final MoveHandler handler;

    public BlockDragCallback(MoveHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true; // cho phép nhấn giữ để kéo block
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false; // không cho swipe để tránh xung đột UI
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {

        handler.onItemMove(
                viewHolder.getAdapterPosition(),
                target.getAdapterPosition()
        );

        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // không hỗ trợ swipe
    }
}
