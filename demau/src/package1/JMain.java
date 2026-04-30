package package1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class JMain {
  public static void main(String[] args) throws IOException {
    cau2();
    cau3();
  }
//cau2
static void cau2() throws IOException {
   FileReader fr = new FileReader("D:\\nhansu_bank.txt");
   BufferedReader br = new BufferedReader(fr);

   FileWriter fw = new FileWriter("D:\\du_lieu_chuan.txt");
   BufferedWriter bw = new BufferedWriter(fw);
   String line = "";
   while((line = br.readLine()) != null) {
    String arr [];
    arr = line.split(",");
    if((arr.length != 4 && arr.length != 6 )|| !checkLuongCoBan(arr[3])) {
      continue;
    }
    System.out.println(line);
    bw.write(line);
    bw.newLine();
   }
   br.close();
    bw.close();
}
//cau3
static void cau3() throws IOException {
  ArrayList<CanBo> list = new ArrayList<>();
  FileReader fr = new FileReader("D:\\du_lieu_chuan.txt");
  BufferedReader br = new BufferedReader(fr);
  String line = "";
  while((line = br.readLine()) != null) {
    String arr [];
    arr = line.split(",");
    if(arr.length == 4) {
      CanBo canBo = new CanBo(arr[0], arr[1], arr[2], Double.parseDouble(arr[3]));
      list.add(canBo);
    }
    else {
      QuanLy quanLy = new QuanLy(arr[0], arr[1], arr[2], Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5]));
      list.add(quanLy);
    }
  }
  Collections.sort(list,(a,b) -> Double.compare(b.TongThuNhap(), a.TongThuNhap()));
  System.out.println("--- Danh sach Can bo ---");
  for(CanBo canBo : list) {
      // Dùng dấu ! để loại trừ những người là Quản lý
      if(!(canBo instanceof QuanLy)) { 
          System.out.println(canBo.ma + " | " + canBo.hoTen + " | " + canBo.phongBan + " | Thu nhập: " + canBo.TongThuNhap());
      }
  }

  System.out.println("\n--- Danh sach Quan ly ---");
  for(CanBo canBo : list) {
      if(canBo instanceof QuanLy) {
          System.out.println(canBo.ma + " | " + canBo.hoTen + " | " + canBo.phongBan + " | Thu nhập: " + canBo.TongThuNhap());
      }
  }
  br.close();
}
static boolean checkLuongCoBan(String luongCoBan) {
  for(int i = 0;i < luongCoBan.length();i++) {
    if(luongCoBan.charAt(i) < '0' || luongCoBan.charAt(i) > '9') {
      return false;
    }
  }
  double luong = Double.parseDouble(luongCoBan);
  if(luong < 0) {
    return false;
  }
  return true;
}
}
class CanBo {
  protected String ma;
  protected String hoTen;
  protected String phongBan;
  protected double luongCoBan;
  CanBo(String ma, String hoTen, String phongBan, double luongCoBan) {
    super();
    this.ma = ma;
    this.hoTen = hoTen;
    this.phongBan = phongBan;
    this.luongCoBan = luongCoBan;
  }
  double TongThuNhap() {
    return luongCoBan;
  }
}
class QuanLy extends CanBo {
  private double phuCap;
  private double thuongKPI;
  QuanLy(String ma, String hoTen, String phongBan, double luongCoBan, double phuCap, double thuongKPI) {
    super(ma, hoTen, phongBan, luongCoBan);
    this.phuCap = phuCap;
    this.thuongKPI = thuongKPI;
  }
  @Override
  double TongThuNhap() {
    return luongCoBan + phuCap + thuongKPI;
  }

}