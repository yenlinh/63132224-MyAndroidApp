package tyl.edu.dean_ck;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tyl.edu.dean_ck.database.databasedoctruyen;
import tyl.edu.dean_ck.model.TaiKhoan;

public class ManDangKy extends AppCompatActivity {

    EditText edtDKTaiKhoan, edtDKMatKhau, edtDKEmail;
    Button btnDKDangNhap, btnDKDangKy;

    databasedoctruyen databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_ky);

        databasedoctruyen = new databasedoctruyen(this);

        AnhXa();

        // Button đăng ký
        btnDKDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taikhoan = edtDKTaiKhoan.getText().toString().trim();
                String matkhau = edtDKMatKhau.getText().toString().trim();
                String email = edtDKEmail.getText().toString().trim();

                // Kiểm tra rỗng
                if (taikhoan.isEmpty() || matkhau.isEmpty() || email.isEmpty()) {

                    Toast.makeText(ManDangKy.this,
                            "Vui lòng nhập đầy đủ thông tin",
                            Toast.LENGTH_SHORT).show();

                } else {

                    TaiKhoan taiKhoan = CreateTaiKhoan();

                    databasedoctruyen.AddTaiKhoan(taiKhoan);

                    Toast.makeText(ManDangKy.this,
                            "Đăng ký thành công",
                            Toast.LENGTH_SHORT).show();

                    // quay về đăng nhập
                    finish();
                }
            }
        });

        // Trở về đăng nhập
        btnDKDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Tạo tài khoản
    private TaiKhoan CreateTaiKhoan() {

        String taikhoan = edtDKTaiKhoan.getText().toString();
        String matkhau = edtDKMatKhau.getText().toString();
        String email = edtDKEmail.getText().toString();

        int phanquyen = 1;

        return new TaiKhoan(
                taikhoan,
                matkhau,
                email,
                phanquyen
        );
    }

    // Ánh xạ
    private void AnhXa() {

        edtDKTaiKhoan = findViewById(R.id.dktaikhoan);
        edtDKMatKhau = findViewById(R.id.dkmatkhau);
        edtDKEmail = findViewById(R.id.dkemail);

        btnDKDangKy = findViewById(R.id.dkdangky);
        btnDKDangNhap = findViewById(R.id.dkdangnhap);

        edtDKTaiKhoan.setTextColor(Color.BLACK);
        edtDKMatKhau.setTextColor(Color.BLACK);
        edtDKEmail.setTextColor(Color.BLACK);
    }
}