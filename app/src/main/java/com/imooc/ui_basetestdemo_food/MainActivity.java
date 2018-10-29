package com.imooc.ui_basetestdemo_food;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

/*
 * 步骤
 * 1、初始化控件
 * 2、初始化数据
 * 3、为控件添加监听器
 */
public class MainActivity extends Activity {
    private EditText name;
    private RadioGroup sex;
    private RadioButton rbMan, rbWoman;
    private CheckBox hot, fish, sour;
    private SeekBar seekBar;
    private Button find;
    private ImageView imageView;
    private ToggleButton toggleButton;
    private List<Food> lists_food;
    private List<Food> lists_get;
    private Person person;
    private RadioGroupListener radioGroupListener;
    private boolean isFish;
    private boolean isHot;
    private boolean isSour;
    private CheckBoxListener checkBoxListener;
    private int price = 30;
    private int count = 0;
    private SeekBarListener seekBarListener;
    private ButtonListener buttonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_food);
        // 初始化控件
        initView();
        // 初始化数据
        initData();
        // 为控件添加监听器
        setListener();
    }

    private void setListener() {
        radioGroupListener = new RadioGroupListener();
        sex.setOnCheckedChangeListener(radioGroupListener);
        checkBoxListener = new CheckBoxListener();
        fish.setOnCheckedChangeListener(checkBoxListener);
        hot.setOnCheckedChangeListener(checkBoxListener);
        sour.setOnCheckedChangeListener(checkBoxListener);
        seekBarListener = new SeekBarListener();
        seekBar.setOnSeekBarChangeListener(seekBarListener);
        buttonListener = new ButtonListener();
        find.setOnClickListener(buttonListener);
        toggleButton.setOnClickListener(buttonListener);
    }

    private void initData() {
        person = new Person();
        lists_get = new ArrayList<>();
        lists_food = new ArrayList<>();
        lists_food.add(new Food("麻辣香锅", 55, R.drawable.malaxiangguo, true, false, false));
        lists_food.add(new Food("水煮鱼", 48, R.drawable.shuizhuyu, true, true, false));
        lists_food.add(new Food("麻辣火锅", 80, R.drawable.malahuoguo, true, true, false));
        lists_food.add(new Food("清蒸鲈鱼", 68, R.drawable.qingzhengluyu, false, true, false));
        lists_food.add(new Food("桂林米粉", 15, R.drawable.guilin, false, false, false));
        lists_food.add(new Food("上汤娃娃菜", 28, R.drawable.wawacai, false, false, false));
        lists_food.add(new Food("红烧肉", 60, R.drawable.hongshaorou, false, false, false));
        lists_food.add(new Food("木须肉", 40, R.drawable.muxurou, false, false, false));
        lists_food.add(new Food("酸菜牛肉面", 35, R.drawable.suncainiuroumian, false, false, true));
        lists_food.add(new Food("西芹炒百合", 38, R.drawable.xiqin, false, false, false));
        lists_food.add(new Food("酸辣汤", 40, R.drawable.suanlatang, true, false, true));
    }

    private void initView() {
        name = findViewById(R.id.et_name);
        sex = findViewById(R.id.rg_sex);
        rbMan = findViewById(R.id.rb_man);
        rbWoman = findViewById(R.id.rb_woman);
        hot = findViewById(R.id.cb_hot);
        fish = findViewById(R.id.cb_fish);
        sour = findViewById(R.id.cb_sour);
        seekBar = findViewById(R.id.sb_price);
        seekBar.setProgress(30);
        find = findViewById(R.id.btn_find);
        toggleButton = findViewById(R.id.tb_click);
        toggleButton.setChecked(true);
        imageView = findViewById(R.id.iv_pic);
    }

    class RadioGroupListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // 当用户选择当前RadioGroup组的Button时被触发
            switch (checkedId) {
                case R.id.rb_man:
                    person.setSex("男");
                    break;
                case R.id.rb_woman:
                    person.setSex("女");
                    break;
            }
            System.out.println("性别：" + person.getSex());
        }

    }

    class CheckBoxListener implements android.widget.CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // 当控件状态改变时被触发
            CheckBox cbBox = (CheckBox) buttonView;
            switch (cbBox.getId()) {
                case R.id.cb_fish:
                    isFish = isChecked;
                    break;

                case R.id.cb_sour:
                    isSour = isChecked;
                    break;

                case R.id.cb_hot:
                    isHot = isChecked;
                    break;
            }
            System.out.println("当前喜好：" + "辣：" + isHot + " 海鲜：" + isFish + " 酸" + isSour);
        }
    }

    class SeekBarListener implements OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //价格变动影响符合条件的菜品
            lists_get.clear();
            imageView.setImageResource(R.drawable.ic_launcher);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            price = seekBar.getProgress();
            Toast.makeText(MainActivity.this, "价格：" + price, Toast.LENGTH_SHORT).show();
        }
    }

    class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_find:
                    lists_get.clear();
                    if (name.getText().toString().equals(""))
                        Toast.makeText(MainActivity.this, R.string.find_no_name, Toast.LENGTH_SHORT).show();
                    else if (!rbMan.isChecked() && !rbWoman.isChecked())
                        Toast.makeText(MainActivity.this, R.string.find_no_sex, Toast.LENGTH_SHORT).show();
                    else {
                        person.setName(name.getText().toString());
                        checkData();
                    }
                    break;
                case R.id.tb_click:
                    //TODO
                    if (toggleButton.isChecked()) {
                        //显示菜品信息
                        if (count != lists_get.size() - 1) {
                            count++;
                            showPic(count);
                        } else {
                            Toast.makeText(MainActivity.this, R.string.show_end, Toast.LENGTH_SHORT).show();
                            count = 0;
                            showPic(count);
                        }
                    } else {
                        if (lists_get.size() == 0) {
                            Toast.makeText(MainActivity.this, R.string.not_find_food, Toast.LENGTH_SHORT).show();
                            toggleButton.setChecked(true);
                        } else {
                            person.setFood(lists_get.get(count));
                            Toast.makeText(MainActivity.this, person.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }

        private void checkData() {
            // 找出菜品
            count = 0;
            for (int i = 0; i < lists_food.size(); i++) {
                Food food = lists_food.get(i);
                if ((food.getPrice() <= price) && (food.isFish() == isFish && food.isHot() == isHot
                        && food.isSour() == isSour)) {
                    lists_get.add(food);
                }
            }
//            System.out.println("*********" + lists_get.size());
            if (lists_get.size() == 0) {
                Toast.makeText(MainActivity.this, R.string.find_no_food, Toast.LENGTH_SHORT).show();
            } else {
                showPic(count);
            }
        }

        private void showPic(int count) {
            imageView.setImageResource(lists_get.get(count).getPic());
        }
    }
}
