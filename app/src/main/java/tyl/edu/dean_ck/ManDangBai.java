package tyl.edu.dean_ck;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tyl.edu.dean_ck.database.databasedoctruyen;
import tyl.edu.dean_ck.model.Truyen;

public class ManDangBai extends AppCompatActivity {

    EditText edtTenTruyen,edtNoiDung,edtAnh;
    Button btnDangBai;
    databasedoctruyen databasedoctruyen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_bai);

        edtAnh = findViewById(R.id.dbimg);
        edtTenTruyen = findViewById(R.id.dbTenTruyen);
        edtNoiDung = findViewById(R.id.dbnoidung);
        btnDangBai = findViewById(R.id.dbdangbai);

        edtAnh.setTextColor(Color.parseColor("#FF000000"));
        edtTenTruyen.setTextColor(Color.parseColor("#FF000000"));
        edtNoiDung.setTextColor(Color.parseColor("#FF000000"));

        databasedoctruyen = new databasedoctruyen(this);
        //button đăng bài
        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tentruyen = edtTenTruyen.getText().toString().trim();
                String noidung = edtNoiDung.getText().toString().trim();
                String img = edtAnh.getText().toString().trim();

                if (tentruyen.isEmpty() || noidung.isEmpty() || img.isEmpty()) {
                    Toast.makeText(ManDangBai.this, "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    Log.e("ERR :", "Nhập đầy đủ thông tin");
                } else {
                    Truyen truyen = CreateTruyen();
                    databasedoctruyen.AddTruyen(truyen);
                    Toast.makeText(ManDangBai.this, "Thêm truyện thành công! Đang trở về màn hình quản lý...", Toast.LENGTH_LONG).show();

                    // Delay chuyển về màn hình ManAdmin sau 2 giây
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(ManDangBai.this, ManAdmin.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },
                            2000 // 2 giây
                    );
                }
            }
        });


    }
    private Truyen CreateTruyen(){

        String tentruyen = edtTenTruyen.getText().toString();
        String noidung = edtNoiDung.getText().toString();
        String img = edtAnh.getText().toString();

        Intent intent= getIntent();

        int id = intent.getIntExtra("Id",0);

        Truyen truyen = new Truyen(tentruyen,noidung,img,id);
        return truyen;

    }
}