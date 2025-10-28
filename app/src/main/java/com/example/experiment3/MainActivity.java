package com.example.experiment3;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // 动物数据 - 与图片完全一致
    private final String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private final int[] animalImages = {
            R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant
    };

    // 通知渠道ID
    private static final String CHANNEL_ID = "animal_notification_channel";

    private AlertDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化所有组件
        initializeComponents();
    }

    /**
     * 初始化所有组件
     */
    private void initializeComponents() {
        createNotificationChannel();  // 创建通知渠道
        initAnimalList();            // 初始化动物列表
        setupDialogButton();         // 设置对话框按钮
        setupMenuTestButton();       // 设置菜单测试按钮
        setupActionModeButton();     // 设置ActionMode演示按钮
    }

    /**
     * 初始化动物列表 - 与图片完全一致
     */
    private void initAnimalList() {
        // 准备数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < animalNames.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", animalNames[i]);
            map.put("image", animalImages[i]);
            dataList.add(map);
        }

        // 创建适配器
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                dataList,
                R.layout.list_item,
                new String[]{"name", "image"},
                new int[]{R.id.animal_name, R.id.animal_icon}
        );

        // 设置列表视图
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // 设置点击事件 - 点击后显示删除确认对话框
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(position);
            }
        });
    }

    /**
     * 显示删除确认对话框 - 根据第二张图片的功能需求
     */
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除")
                .setMessage("确定要删除 \"" + animalNames[position] + "\" 吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 执行删除操作
                        deleteAnimalItem(position);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("取消删除");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * 删除动物项目
     */
    private void deleteAnimalItem(int position) {
        if (position >= 0 && position < animalNames.length) {
            String deletedAnimal = animalNames[position];
            showToast("已删除: " + deletedAnimal);

            // 这里可以添加实际的数据删除逻辑
            // 例如：从数据源中移除项目并刷新列表
        }
    }

    /**
     * 设置ActionMode演示按钮
     */
    private void setupActionModeButton() {
        Button actionModeButton = findViewById(R.id.goToActionModeButton);
        actionModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动ActionMode演示Activity
                Intent intent = new Intent(MainActivity.this, ActionModeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置对话框按钮
     */
    private void setupDialogButton() {
        Button showDialogButton = findViewById(R.id.showDialogButton);
        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    /**
     * 设置菜单测试按钮
     */
    private void setupMenuTestButton() {
        Button menuTestButton = findViewById(R.id.goToMenuTestButton);
        menuTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MenuTestActivity.class));
            }
        });
    }

    /**
     * 显示自定义对话框
     */
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);

        builder.setView(dialogView);
        customDialog = builder.create();

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        initDialogViews(dialogView);
        customDialog.show();
        resizeDialog();
    }

    /**
     * 初始化对话框视图
     */
    private void initDialogViews(View dialogView) {
        EditText usernameEditText = dialogView.findViewById(R.id.usernameEditText);
        EditText passwordEditText = dialogView.findViewById(R.id.passwordEditText);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button signinButton = dialogView.findViewById(R.id.signinButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                showToast("登录已取消");
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin(usernameEditText, passwordEditText);
            }
        });
    }

    /**
     * 处理登录逻辑
     */
    private void handleLogin(EditText usernameEditText, EditText passwordEditText) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty()) {
            showToast("请输入用户名");
            usernameEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            showToast("请输入密码");
            passwordEditText.requestFocus();
            return;
        }

        customDialog.dismiss();
        showToast("登录成功: " + username);
    }

    /**
     * 调整对话框尺寸
     */
    private void resizeDialog() {
        if (customDialog.getWindow() != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(customDialog.getWindow().getAttributes());
            layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            customDialog.getWindow().setAttributes(layoutParams);
        }
    }

    /**
     * 创建通知渠道
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "动物通知",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("显示选中动物的通知");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * 发送通知
     */
    private void sendNotification(String animalName, int position) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(animalName)
                .setContentText("这是关于" + animalName + "的详细介绍")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), animalImages[position]))
                .setColor(Color.BLUE)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(position, builder.build());
        }
    }

    /**
     * 显示Toast
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }
}