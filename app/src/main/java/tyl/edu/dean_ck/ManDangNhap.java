package tyl.edu.dean_ck;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tyl.edu.dean_ck.database.databasedoctruyen;

public class ManDangNhap extends AppCompatActivity {
    EditText edtTaiKhoan,edtMatKhau;
    Button btnDangNhap,btnDangKy;
    //Tạo đối tượng cho databasedoctruyen
    databasedoctruyen databasedoctruyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_nhap);

        AnhXa();
        //Đối tượng database đọc truyện
        databasedoctruyen = new databasedoctruyen(this);
        //Tạo sự kiện click button chuyển sang màn hình đăng ký với Intent
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManDangNhap.this,ManDangKy.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gắn cho các biến là giá trị nhập vào từ EditText
                String tentaikhoan = edtTaiKhoan.getText().toString().trim();
                String matkhau = edtMatKhau.getText().toString().trim();

                // Kiểm tra nếu chưa nhập tài khoản hoặc mật khẩu
                if (tentaikhoan.isEmpty() || matkhau.isEmpty()) {
                    // Hiển thị thông báo yêu cầu nhập đầy đủ
                    Toast.makeText(ManDangNhap.this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sử dụng con trỏ để lấy dữ liệu từ database
                Cursor cursor = databasedoctruyen.getData();
                boolean isLoginSuccessful = false;

                while (cursor.moveToNext()) {
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);

                    if (datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau)) {
                        // Đăng nhập thành công
                        int idd = cursor.getInt(0);
                        String email = cursor.getString(3);
                        int phanquyen = cursor.getInt(4);
                        String tentk = cursor.getString(1);

                        Intent intent = new Intent(ManDangNhap.this, MainActivity.class);
                        intent.putExtra("phanq", phanquyen);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentk);
                        startActivity(intent);

                        isLoginSuccessful = true;
                        break;
                    }
                }

                cursor.close();

                // Nếu không đăng nhập được
                if (!isLoginSuccessful) {
                    Toast.makeText(ManDangNhap.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        edtMatKhau = findViewById(R.id.matkhau);
        edtTaiKhoan = findViewById(R.id.taikhoan);
        btnDangKy = findViewById(R.id.dangky);
        btnDangNhap = findViewById(R.id.dangnhap);

        edtTaiKhoan.setTextColor(Color.parseColor("#FF000000"));
        edtMatKhau.setTextColor(Color.parseColor("#FF000000"));
    }

}