package tyl.edu.dean_ck;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ManThongTin extends AppCompatActivity {

    TextView txtThongtinapp;
    Button buttonQuayLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_thong_tin);

        txtThongtinapp = findViewById(R.id.textviewthongtin);
        buttonQuayLai = findViewById(R.id.buttonQuayLai);

        String thongtin = "Ứng dụng được thiết kế bởi Linh\n"+
                "Ứng dụng được tham khảo từ các nguồn link trên mạng\n"+
                "Cảm ơn mọi người đã sử dụng app";

        txtThongtinapp.setText(thongtin);

        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Trở về màn hình trước (MainActivity)
            }
        });
    }
}