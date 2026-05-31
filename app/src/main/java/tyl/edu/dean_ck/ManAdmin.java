package tyl.edu.dean_ck;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tyl.edu.dean_ck.adapter.TruyenAdapterNew;
import tyl.edu.dean_ck.database.databasedoctruyen;
import tyl.edu.dean_ck.model.Truyen;

public class ManAdmin extends AppCompatActivity {

    RecyclerView recyclerView;
    Button buttonThem;

    ArrayList<Truyen> TruyenArrayList;
    TruyenAdapterNew truyenAdapterNew;

    databasedoctruyen databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_admin);

        recyclerView = findViewById(R.id.recyclerViewAdmin);
        buttonThem = findViewById(R.id.buttonThemtruyen);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        initList();

        Button buttonQuayLai = findViewById(R.id.buttonQuayLai);
        buttonQuayLai.setOnClickListener(v -> finish());

        buttonThem.setOnClickListener(v -> {
            int id = getIntent().getIntExtra("Id", 0);
            Intent intent = new Intent(ManAdmin.this, ManDangBai.class);
            intent.putExtra("Id", id);
            startActivity(intent);
        });
    }

    // Gắn dữ liệu cho RecyclerView
    private void initList() {
        TruyenArrayList = new ArrayList<>();
        databasedoctruyen = new databasedoctruyen(this);

        Cursor cursor1 = databasedoctruyen.getData2();

        while (cursor1.moveToNext()) {
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArrayList.add(new Truyen(id, tentruyen, noidung, anh, id_tk));
        }

        cursor1.close();

        truyenAdapterNew = new TruyenAdapterNew(this, TruyenArrayList);

        // Gắn listener khi long click để xóa
        truyenAdapterNew.setOnItemLongClickListener(position -> showDeleteDialog(position));
        recyclerView.setAdapter(truyenAdapterNew);
    }

    // Hiển thị dialog xác nhận xóa truyện
    private void showDeleteDialog(int position) {
        //Tạo đối tượng dialog
        Dialog dialog = new Dialog(this);
        //Nạp layout vào dialog
        dialog.setContentView(R.layout.dialogdelete);
        //Tắt click ra ngoài là đóng, chỉ click no mới đúng
        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog.findViewById(R.id.buttonYes);
        Button btnNo = dialog.findViewById(R.id.buttonNo);

        btnYes.setOnClickListener(v -> {
            int idtruyen = TruyenArrayList.get(position).getID();
            databasedoctruyen.Delete(idtruyen);
            TruyenArrayList.remove(position);
            truyenAdapterNew.notifyItemRemoved(position);
            Toast.makeText(ManAdmin.this, "Xóa truyện thành công", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnNo.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
