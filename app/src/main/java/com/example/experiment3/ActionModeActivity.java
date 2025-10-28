package com.example.experiment3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class ActionModeActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Set<Integer> selectedItems;

    // 视图绑定
    private LinearLayout actionModeHeader;
    private ImageView checkIcon;
    private TextView selectedCountText;
    private ImageButton deleteButton;

    private final String[] items = {"One", "Two", "Three", "Four", "Five"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_mode);

        // 初始化视图
        initViews();

        // 设置现代的后退处理
        setupBackPressHandler();

        // 设置其他功能
        setupActionModeHeader();
        setupListView();
    }

    private void initViews() {
        listView = findViewById(R.id.listView);
        actionModeHeader = findViewById(R.id.actionModeHeader);
        checkIcon = findViewById(R.id.checkIcon);
        selectedCountText = findViewById(R.id.selectedCountText);
        deleteButton = findViewById(R.id.deleteButton);
        selectedItems = new HashSet<>();
    }

    private void setupBackPressHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (!selectedItems.isEmpty()) {
                    clearSelection();
                } else {
                    setEnabled(false);
                    ActionModeActivity.super.onBackPressed();
                }
            }
        });
    }

    private void setupActionModeHeader() {
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());
        deleteButton.setOnLongClickListener(v -> {
            Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupListView() {
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_action_mode, R.id.itemText, items) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setBackgroundColor(selectedItems.contains(position) ?
                        getColor(android.R.color.holo_blue_light) :
                        getColor(android.R.color.white));
                return view;
            }
        };

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView.setOnItemClickListener((parent, view, position, id) -> toggleSelection(position));
    }

    private void toggleSelection(int position) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position);
        } else {
            selectedItems.add(position);
        }
        updateActionModeHeader();
        adapter.notifyDataSetChanged();
    }

    private void updateActionModeHeader() {
        int count = selectedItems.size();
        if (count > 0) {
            actionModeHeader.setVisibility(View.VISIBLE);
            selectedCountText.setText(count + " selected");
            checkIcon.setImageResource(count == items.length ?
                    android.R.drawable.checkbox_on_background :
                    android.R.drawable.presence_online);
        } else {
            actionModeHeader.setVisibility(View.GONE);
        }
    }

    private void showDeleteConfirmationDialog() {
        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "请先选择要删除的项目", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("确定要删除选中的 " + selectedItems.size() + " 个项目吗？")
                .setPositiveButton("删除", (dialog, which) -> performDelete())
                .setNegativeButton("取消", (dialog, which) ->
                        Toast.makeText(this, "取消删除", Toast.LENGTH_SHORT).show())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void performDelete() {
        int count = selectedItems.size();
        selectedItems.clear();
        updateActionModeHeader();
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "成功删除 " + count + " 个项目", Toast.LENGTH_SHORT).show();
    }

    private void clearSelection() {
        selectedItems.clear();
        updateActionModeHeader();
        adapter.notifyDataSetChanged();
    }
}