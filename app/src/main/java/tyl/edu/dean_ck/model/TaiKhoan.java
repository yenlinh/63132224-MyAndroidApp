package tyl.edu.dean_ck.model;
public class TaiKhoan {

    private String tenTaiKhoan;
    private String matKhau;
    private String email;
    private int phanQuyen;

    public TaiKhoan(String tenTaiKhoan,
                    String matKhau,
                    String email,
                    int phanQuyen) {

        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.email = email;
        this.phanQuyen = phanQuyen;
    }

    public String getmTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getmMatkhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getmEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getmPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(int phanQuyen) {
        this.phanQuyen = phanQuyen;
    }
}