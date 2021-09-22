package com.example.banhangonline.Model;

public class News {
    public int maTinTuc;
    public String tenTinTuc;
    public String noiDung;
    public String ngayDang;
    public String anhMinhHoa;
    public boolean kichHoat;
    public String ghiChu;
    public int maLoaiTin;

    public News() {
    }

    public News(int maTinTuc, String tenTinTuc, String noiDung, String ngayDang, String anhMinhHoa, boolean kichHoat, String ghiChu, int maLoaiTin) {
        this.maTinTuc = maTinTuc;
        this.tenTinTuc = tenTinTuc;
        this.noiDung = noiDung;
        this.ngayDang = ngayDang;
        this.anhMinhHoa = anhMinhHoa;
        this.kichHoat = kichHoat;
        this.ghiChu = ghiChu;
        this.maLoaiTin = maLoaiTin;
    }

    public int getMaTinTuc() {
        return maTinTuc;
    }

    public void setMaTinTuc(int maTinTuc) {
        this.maTinTuc = maTinTuc;
    }

    public String getTenTinTuc() {
        return tenTinTuc;
    }

    public void setTenTinTuc(String tenTinTuc) {
        this.tenTinTuc = tenTinTuc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public String getAnhMinhHoa() {
        return anhMinhHoa;
    }

    public void setAnhMinhHoa(String anhMinhHoa) {
        this.anhMinhHoa = anhMinhHoa;
    }

    public boolean isKichHoat() {
        return kichHoat;
    }

    public void setKichHoat(boolean kichHoat) {
        this.kichHoat = kichHoat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getMaLoaiTin() {
        return maLoaiTin;
    }

    public void setMaLoaiTin(int maLoaiTin) {
        this.maLoaiTin = maLoaiTin;
    }
}
