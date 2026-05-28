package tyl.edu.dean_ck;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tyl.edu.dean_ck.adapter.TruyenAdapterNew;
import tyl.edu.dean_ck.model.Truyen;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerTruyen;

    ArrayList<Truyen> listTruyen;

    TruyenAdapterNew adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        listTruyen = new ArrayList<>();

        // Dữ liệu test
        listTruyen.add(new Truyen(
                "One Piece",
                "Nội dung One Piece",
                "https://upload.wikimedia.org/wikipedia/en/6/65/One_Piece_Logo.svg",
                1
        ));

        listTruyen.add(new Truyen(
                "Naruto",
                "Nội dung Naruto",
                "https://upload.wikimedia.org/wikipedia/en/9/94/NarutoCoverTankobon1.jpg",
                1
        ));

        listTruyen.add(new Truyen(
                "Dragon Ball",
                "Nội dung Dragon Ball",
                "https://upload.wikimedia.org/wikipedia/en/c/c9/DB_Tank%C5%8Dbon.png",
                1
        ));

        adapter = new TruyenAdapterNew(this, listTruyen);

        recyclerTruyen.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerTruyen.setAdapter(adapter);
    }

    private void AnhXa() {
        recyclerTruyen = findViewById(R.id.recyclerTruyen);
    }
}