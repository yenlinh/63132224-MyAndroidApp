package tyl.edu.dean_ck;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ManNoiDung extends AppCompatActivity {

    TextView txtTenTruyen, txtNoiDung;
    Button buttonQuayLai, buttonDocTruyen, buttonDung;
    TextToSpeech textToSpeech;
    String currentText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_noi_dung);

        // Ánh xạ View
        txtNoiDung = findViewById(R.id.noidung);
        txtTenTruyen = findViewById(R.id.TenTruyen);
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        buttonDocTruyen = findViewById(R.id.buttonDocTruyen);
        buttonDung = findViewById(R.id.buttonDung);

        // Nhận dữ liệu từ intent
        Intent intent = getIntent();
        String tentruyen = intent.getStringExtra("tentruyen");
        String noidung = intent.getStringExtra("noidung");

        txtTenTruyen.setText(tentruyen);

        // Thêm tab đầu dòng mỗi đoạn
        String formattedContent = noidung.replaceAll("(?m)^", "\t");
        txtNoiDung.setText(formattedContent);
        txtNoiDung.setMovementMethod(new ScrollingMovementMethod());

        // Nút quay lại
        buttonQuayLai.setOnClickListener(v -> finish());

        // Khởi tạo TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(new Locale("vi", "VN"));
                buttonDocTruyen.setEnabled(result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED);
            }
        });

        // Nút Đọc
        buttonDocTruyen.setOnClickListener(v -> {
            currentText = txtNoiDung.getText().toString();
            textToSpeech.speak(currentText, TextToSpeech.QUEUE_FLUSH, null, null);
        });

        // Nút Dừng
        buttonDung.setOnClickListener(v -> {
            textToSpeech.stop();
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
