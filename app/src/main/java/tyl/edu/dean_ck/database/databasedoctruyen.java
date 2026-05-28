package tyl.edu.dean_ck.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import tyl.edu.dean_ck.model.TaiKhoan;

public class databasedoctruyen extends SQLiteOpenHelper {

    // Tên database
    private static final String DATABASE_NAME = "doctruyen";

    // Version
    private static final int VERSION = 1;

    // Bảng tài khoản
    private static final String TABLE_TAIKHOAN = "taikhoan";

    private static final String ID_TAIKHOAN = "idtaikhoan";
    private static final String TEN_TAIKHOAN = "tentaikhoan";
    private static final String MATKHAU = "matkhau";
    private static final String EMAIL = "email";
    private static final String PHANQUYEN = "phanquyen";

    // Câu lệnh tạo bảng
    private String SQL_TAIKHOAN =
            "CREATE TABLE " + TABLE_TAIKHOAN + " ("
                    + ID_TAIKHOAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TEN_TAIKHOAN + " TEXT UNIQUE, "
                    + MATKHAU + " TEXT, "
                    + EMAIL + " TEXT, "
                    + PHANQUYEN + " INTEGER )";

    // Insert dữ liệu mẫu
    private String INSERT_ADMIN =
            "INSERT INTO taikhoan VALUES(null,'admin','admin','admin@gmail.com',2)";

    private String INSERT_USER =
            "INSERT INTO taikhoan VALUES(null,'hoai','hoai','hoai@gmail.com',1)";

    public databasedoctruyen(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_TAIKHOAN);

        db.execSQL(INSERT_ADMIN);

        db.execSQL(INSERT_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Lấy toàn bộ tài khoản
    public Cursor getData() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery("SELECT * FROM " + TABLE_TAIKHOAN, null);

        return cursor;
    }

    // Thêm tài khoản
    public void AddTaiKhoan(TaiKhoan taiKhoan) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TEN_TAIKHOAN,
                taiKhoan.getTenTaiKhoan());

        values.put(MATKHAU,
                taiKhoan.getMatKhau());

        values.put(EMAIL,
                taiKhoan.getEmail());

        values.put(PHANQUYEN,
                taiKhoan.getPhanQuyen());

        db.insert(TABLE_TAIKHOAN,
                null,
                values);

        db.close();
    }
}
