package tyl.edu.dean_ck;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tyl.edu.dean_ck.adapter.TruyenAdapterNew;
import tyl.edu.dean_ck.database.databasedoctruyen;
import tyl.edu.dean_ck.model.Truyen;

public class ManTimKiem extends AppCompatActivity {

    EditText edtTimKiem;
    RecyclerView recyclerView;
    ArrayList<Truyen> TruyenArraylist;
    ArrayList<Truyen> filteredList;
    TruyenAdapterNew adapter;
    databasedoctruyen databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_tim_kiem);

        edtTimKiem = findViewById(R.id.timkiem);
        recyclerView = findViewById(R.id.recyclerViewTimKiem);

        databasedoctruyen = new databasedoctruyen(this);
        TruyenArraylist = new ArrayList<>();
        filteredList = new ArrayList<>();

        // Lấy dữ liệu từ database
        Cursor cursor = databasedoctruyen.getData2();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tentruyen = cursor.getString(1);
            String noidung = cursor.getString(2);
            String anh = cursor.getString(3);
            int id_tk = cursor.getInt(4);

            Truyen truyen = new Truyen(id, tentruyen, noidung, anh, id_tk);
            TruyenArraylist.add(truyen);
            filteredList.add(truyen);
        }
        cursor.close();
        //Thiết Lập hiện truyện bằng recycleView
        adapter = new TruyenAdapterNew(this, TruyenArraylist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Lắng nghe sự thay đổi văn bản để tìm kiếm
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        // Bắt sự kiện click item
        adapter.setOnItemClickListener((position) -> {
            Truyen truyen = adapter.getTruyenAtPosition(position);
            Intent intent = new Intent(ManTimKiem.this, ManNoiDung.class);
            intent.putExtra("tentruyen", truyen.getTenTruyen());
            intent.putExtra("noidung", truyen.getNoiDung());
            startActivity(intent);
        });
    }

    //search
    private void filter(String text) {
        //Xóa dữ liệu mảng
        filteredList.clear();
        for (Truyen truyen : TruyenArraylist) {
            if (truyen.getTenTruyen().toLowerCase().contains(text.toLowerCase())) {
                //Thêm item vào filteredList
                filteredList.add(truyen);
            }
        }
        adapter.filterList(filteredList);
    }
}
