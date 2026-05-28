package tyl.edu.dean_ck;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtXinChao, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtXinChao =
                findViewById(R.id.txtXinChao);

        txtEmail =
                findViewById(R.id.txtEmail);

        Intent intent = getIntent();

        String tentaikhoan =
                intent.getStringExtra("tentaikhoan");

        String email =
                intent.getStringExtra("email");

        txtXinChao.setText(
                "Xin chào: " + tentaikhoan
        );

        txtEmail.setText(
                "Email: " + email
        );
    }
}