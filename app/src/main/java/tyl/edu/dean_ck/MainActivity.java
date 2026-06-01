package tyl.edu.dean_ck;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tyl.edu.dean_ck.adapter.TruyenAdapterNew;
import tyl.edu.dean_ck.adapter.adapterchuyenmuc;
import tyl.edu.dean_ck.adapter.adapterthongtin;
import tyl.edu.dean_ck.database.databasedoctruyen;
import tyl.edu.dean_ck.model.TaiKhoan;
import tyl.edu.dean_ck.model.Truyen;
import tyl.edu.dean_ck.model.chuyenmuc;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView, listViewThongTin;
    RecyclerView recyclerViewNew;
    DrawerLayout drawerLayout;

    String email;
    String tentaikhoan;
    ArrayList<Truyen> TruyenArraylist;
    TruyenAdapterNew adapterTruyen;
    ArrayList<chuyenmuc> chuyenmucArrayList;
    ArrayList<TaiKhoan> taiKhoanArrayList;
    databasedoctruyen databasedoctruyen;
    adapterchuyenmuc adapterchuyenmuc;
    adapterthongtin adapterthongtin;

    int idd;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasedoctruyen = new databasedoctruyen(this);
        // Nhận dữ liệu ở màn đăng nhập gửi
        Intent intentpq = getIntent();
        i = intentpq.getIntExtra("phanq", 0);
        idd = intentpq.getIntExtra("idd", 0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        AnhXa();
        ActionBar();
        ActionViewFlipper();

        // Thiết lập RecyclerView cho danh sách truyện mới theo dạng ngang 1 item
        adapterTruyen = new TruyenAdapterNew(this, TruyenArraylist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewNew.setLayoutManager(layoutManager);
        recyclerViewNew.setAdapter(adapterTruyen);

        adapterTruyen.setOnItemClickListener((position) -> {
            Truyen truyen = TruyenArraylist.get(position);
            Intent intent = new Intent(MainActivity.this, ManNoiDung.class);
            intent.putExtra("tentruyen", truyen.getTenTruyen());
            intent.putExtra("noidung", truyen.getNoiDung());
            startActivity(intent);
        });


        // Bắt click item cho listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Đăng bài
                if (position == 0) {
                    if (i == 2) {
                        Intent intent = new Intent(MainActivity.this, ManAdmin.class);
                        // Gửi id tài khoản qua màn admin
                        intent.putExtra("Id", idd);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Bạn không có quyền đăng bài", Toast.LENGTH_SHORT).show();
                        Log.e("Đăng bài : ", "Bạn không có quyền");
                    }
                }
                // Nếu vị trí ấn vào là thông tin thì sẽ chuyển qua màn hình thông tin app
                else if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, ManThongTin.class);
                    startActivity(intent);
                }
                // Đăng xuất
                else if (position == 2) {
                    finish();
                }
            }
        });
    }

    // Khi quay trở lại màn hình chính sẽ tải lại danh sách truyện (nếu có thay đổi)
    @Override
    protected void onResume() {
        super.onResume();
        reloadTruyen();
    }

    // Hàm load lại danh sách truyện
    private void reloadTruyen() {
        TruyenArraylist.clear();
        Cursor cursor = databasedoctruyen.getData1();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tentruyen = cursor.getString(1);
            String noidung = cursor.getString(2);
            String anh = cursor.getString(3);
            int id_tk = cursor.getInt(4);
            TruyenArraylist.add(new Truyen(id, tentruyen, noidung, anh, id_tk));
        }
        cursor.close();
        // Thông báo adapter của RecyclerView cập nhật lại dữ liệu
        adapterTruyen.notifyDataSetChanged();
    }

    // Thanh actionbar với toolbar
    private void ActionBar() {
        // Hàm hỗ trợ toolbar
        setSupportActionBar(toolbar);
        // set nút cho actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Tạo icon cho toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        // Thêm sự kiện click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    // Phương thức cho chạy quảng cáo với ViewFlipper
    private void ActionViewFlipper() {
        // mảng chứa tấm ảnh cho quảng cáo
        ArrayList<String> mangquangcao = new ArrayList<>();
        // Add ảnh vào mảng
        mangquangcao.add("https://toplist.vn/images/800px/coc-kien-troi-1101127.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/nang-tien-oc-1101131.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/cu-cai-trang-230181.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/de-den-va-de-trang-230182.jpg");

        // Thực hiện vòng lặp for gán ảnh vào ImageView , rồi từ imgview lên app
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView((getApplicationContext()));
            // Sử dụng hàm thư viện
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            // phương thức chỉnh tấm hình vào khung quảng cáo
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            // Thêm ảnh từ imageview vào ViewFlipper
            viewFlipper.addView(imageView);
        }

        // Thiết lập tự động chạy cho viewFlipper chạy trong 4 giây
        viewFlipper.setFlipInterval(4000);
        // run auto viewFlipper
        viewFlipper.setAutoStart(true);
        // Gọi Animation cho vào và ra
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        // Gọi Animation vào Flipper
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void AnhXa() {

        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewNew = findViewById(R.id.recyclerViewNew);

        listView = findViewById(R.id.listviewmanhinhchinh);

        listViewThongTin = findViewById(R.id.listviewthongtin);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        TruyenArraylist = new ArrayList<>();

        Cursor cursor = databasedoctruyen.getData1();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tentruyen = cursor.getString(1);
            String noidung = cursor.getString(2);
            String anh = cursor.getString(3);
            int id_tk = cursor.getInt(4);

            TruyenArraylist.add(
                    new Truyen(
                            id,
                            tentruyen,
                            noidung,
                            anh,
                            id_tk
                    )
            );
        }

        cursor.close();

        adapterTruyen = new TruyenAdapterNew(
                getApplicationContext(),
                TruyenArraylist
        );

        // THÔNG TIN
        taiKhoanArrayList = new ArrayList<>();

        taiKhoanArrayList.add(
                new TaiKhoan(
                        tentaikhoan,
                        "",
                        email,
                        i
                )
        );

        adapterthongtin = new adapterthongtin(
                this,
                R.layout.navigation_thongtin,
                taiKhoanArrayList
        );

        listViewThongTin.setAdapter(adapterthongtin);

        // CHUYÊN MỤC
        chuyenmucArrayList = new ArrayList<>();

        chuyenmucArrayList.add(
                new chuyenmuc(
                        "Đăng bài",
                        "https://cdn0.iconfinder.com/data/icons/influencer-line/48/blogger_blog_post_article-512.png"
                )
        );

        chuyenmucArrayList.add(
                new chuyenmuc(
                        "Thông tin",
                        "https://cdn.pixabay.com/photo/2012/04/24/23/56/information-41225_1280.png"
                )
        );

        chuyenmucArrayList.add(
                new chuyenmuc(
                        "Đăng xuất",
                        "https://cdn-icons-png.flaticon.com/512/1828/1828479.png"
                )
        );

        adapterchuyenmuc = new adapterchuyenmuc(
                this,
                R.layout.chuyenmuc,
                chuyenmucArrayList
        );

        listView.setAdapter(adapterchuyenmuc);
    }

    // Nạp 1 menu tìm kiếm vào ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Nếu click vào icon tìm kiếm thì sẽ chuyển sang màn hình tìm kiếm
        if (item.getItemId() == R.id.menu1) {
            Intent intent = new Intent(MainActivity.this, ManTimKiem.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
