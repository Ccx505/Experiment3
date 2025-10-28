package com.example.experiment3;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MenuTestActivity extends AppCompatActivity {

    private TextView testTextView;
    private Button menuButton;

    // 字体大小常量
    private static final int FONT_SIZE_SMALL = 10;
    private static final int FONT_SIZE_MEDIUM = 16;
    private static final int FONT_SIZE_LARGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        testTextView = findViewById(R.id.testTextView);
        menuButton = findViewById(R.id.menuButton);
    }

    private void setupClickListeners() {
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());

        // 设置菜单项点击监听器
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return handleMenuItemClick(item);
            }
        });

        popupMenu.show();
    }

    private boolean handleMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_normal) {
            // 普通菜单项 - 显示Toast
            Toast.makeText(this, "您点击了普通菜单项", Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_font_small) {
            // 小字体
            testTextView.setTextSize(FONT_SIZE_SMALL);
            Toast.makeText(this, "字体大小设置为小", Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_font_medium) {
            // 中字体
            testTextView.setTextSize(FONT_SIZE_MEDIUM);
            Toast.makeText(this, "字体大小设置为中", Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_font_large) {
            // 大字体
            testTextView.setTextSize(FONT_SIZE_LARGE);
            Toast.makeText(this, "字体大小设置为大", Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_color_red) {
            // 红色字体
            testTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            Toast.makeText(this, "字体颜色设置为红色", Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_color_black) {
            // 黑色字体
            testTextView.setTextColor(getResources().getColor(android.R.color.black));
            Toast.makeText(this, "字体颜色设置为黑色", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
