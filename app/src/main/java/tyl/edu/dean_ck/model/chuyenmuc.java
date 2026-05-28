package tyl.edu.dean_ck.model;

public class chuyenmuc {

    private String tenchyenmuc;
    private String hinhanhchuyenmuc;

    public chuyenmuc(String tenchyenmuc, String hinhanhchuyenmuc) {
        this.tenchyenmuc = tenchyenmuc;
        this.hinhanhchuyenmuc = hinhanhchuyenmuc;
    }

    public String getTenchyenmuc() {
        return tenchyenmuc;
    }

    public void setTenchyenmuc(String tenchyenmuc) {
        this.tenchyenmuc = tenchyenmuc;
    }

    public String getHinhanhchuyenmuc() {
        return hinhanhchuyenmuc;
    }

    public void setHinhanhchuyenmuc(String hinhanhchuyenmuc) {
        this.hinhanhchuyenmuc = hinhanhchuyenmuc;
    }
}
