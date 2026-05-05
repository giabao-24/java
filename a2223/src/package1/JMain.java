package package1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; // Bổ sung thư viện
import java.sql.ResultSet;       // Bổ sung thư viện
import java.sql.Statement;       // Bổ sung thư viện
import java.util.ArrayList;

public class JMain {
  // 1. Added "throws IOException" so the main method can handle cau2()'s exception
  public static void main(String[] args) throws IOException {
    cau2();
    // Hứng kết quả trả về từ cau3 và truyền vào cau4
    ArrayList<Person> danhSach = cau3(); 
    cau4(danhSach);
    cau5();
  }
  
  public static void cau4(ArrayList<Person> arrL) {
     String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyTruongHoc;encrypt=true;trustServerCertificate=true;";
     String username = "sa";
     String password = "123";
     
     try {
       Connection conn = DriverManager.getConnection(url, username, password);
       
       // Chuẩn bị câu lệnh SQL cho 2 bảng
       String sqlNhanVien = "INSERT INTO NhanVien(MaNV, HoTen, LoaiHopDong, HeSoLuong) VALUES(?, ?, ?, ?)";
       String sqlGiangVien = "INSERT INTO GiangVien(MaGV, HoTen, LoaiHopDong, HeSoLuong, PhuCap) VALUES(?, ?, ?, ?, ?)";
       
       PreparedStatement psNV = conn.prepareStatement(sqlNhanVien);
       PreparedStatement psGV = conn.prepareStatement(sqlGiangVien);
       
       // Duyệt mảng và thêm vào CSDL
       for (Person p : arrL) {
           if (p instanceof NhanVien) {
               psNV.setString(1, p.ma);
               psNV.setString(2, p.hoTen);
               psNV.setString(3, p.loaiHopDong);
               psNV.setDouble(4, p.heSoLuong);
               psNV.executeUpdate(); // Thực thi lệnh chèn
           } else if (p instanceof GiangVien) {
               GiangVien gv = (GiangVien) p;
               psGV.setString(1, gv.ma);
               psGV.setString(2, gv.hoTen);
               psGV.setString(3, gv.loaiHopDong);
               psGV.setDouble(4, gv.heSoLuong);
               psGV.setDouble(5, gv.getPhuCap());
               psGV.executeUpdate(); // Thực thi lệnh chèn
           }
       }
       System.out.println("==> Cau 4: Da luu vao co so du lieu thanh cong!");
       
       // Đóng kết nối
       psNV.close();
       psGV.close();
       conn.close();
       
     } catch(Exception ex) {
         System.out.println("Loi o cau 4: " + ex.getMessage());
     }
  }
    
  public static void cau5() {
      String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyTruongHoc;encrypt=true;trustServerCertificate=true;";
      String username = "sa";
      String password = "123";
      
      try {
          Connection conn = DriverManager.getConnection(url, username, password);
          Statement stmt = conn.createStatement();
          
          // Đếm nhân viên hợp đồng chính thức
          String sqlChinhThuc = "SELECT COUNT(*) AS SoLuong FROM NhanVien WHERE LoaiHopDong = 'chinhthuc'";
          ResultSet rs1 = stmt.executeQuery(sqlChinhThuc);
          int countChinhThuc = 0;
          if(rs1.next()) {
              countChinhThuc = rs1.getInt("SoLuong");
          }
          
          // Đếm nhân viên hợp đồng
          String sqlHopDong = "SELECT COUNT(*) AS SoLuong FROM NhanVien WHERE LoaiHopDong = 'hopdong'";
          ResultSet rs2 = stmt.executeQuery(sqlHopDong);
          int countHopDong = 0;
          if(rs2.next()) {
              countHopDong = rs2.getInt("SoLuong");
          }
          
          System.out.println("==> Cau 5: Thong ke tu CSDL (Bang NhanVien)");
          System.out.println("- Nhan vien chinh thuc: " + countChinhThuc);
          System.out.println("- Nhan vien hop dong: " + countHopDong);
          
          conn.close();
      } catch(Exception ex) {
          System.out.println("Loi o cau 5: " + ex.getMessage());
      }
  }

  public static ArrayList<Person> cau3() throws IOException {
    FileReader fr = new FileReader("D:\\ds_output.txt");
    BufferedReader br = new BufferedReader(fr);
    ArrayList<Person> arrL = new ArrayList<>();
    String line = "";
    
    while((line = br.readLine()) != null) {
      String arr [];
      arr = line.split(",");
      if(arr.length == 4) {
        // Thêm .trim() để phòng trường hợp file txt có chứa dấu cách thừa (space)
        NhanVien a = new NhanVien(arr[0].trim(), arr[1].trim(), arr[2].trim(), Double.parseDouble(arr[3].trim()));
        arrL.add(a);
      } else if(arr.length == 5) {
        GiangVien a = new GiangVien(arr[0].trim(), arr[1].trim(), arr[2].trim(), Double.parseDouble(arr[3].trim()), Double.parseDouble(arr[4].trim()));
        arrL.add(a);
      }
    }
    
    System.out.println("==Nhan vien==");
    for(Person c : arrL) {
      if(c instanceof NhanVien) {
        c.output();
      }
    }
    
    System.out.println("Giang vien");
    for(Person c : arrL) {
      if(c instanceof GiangVien) {
        c.output();
      }
    }
    
    br.close();
    fr.close();
    return arrL;
  }
    
  // 2. Moved cau2() INSIDE the JMain1 class
  public static void cau2() throws IOException {
    // 3. Changed the output file to "ds_output.txt" so it doesn't overwrite your input file
    FileWriter fw = new FileWriter("D:\\ds_output.txt");
    BufferedWriter bw = new BufferedWriter(fw);
    
    FileReader fr = new FileReader("D:\\ds.txt");
    BufferedReader br = new BufferedReader(fr);
    
    String line = "";
    // 4. Changed br.toString() to br.readLine() to actually read the text
    while((line = br.readLine()) != null) {
      String arr [];
      arr = line.split(",");
      if(arr.length == 4) {
        System.out.println(line);
        bw.write(line);
        bw.newLine(); // 5. Added a line break so the file doesn't write onto one single line
      } else if(arr.length == 5) {
        System.out.println(line);
        bw.write(line);
        bw.newLine(); 
      }
    }
    
    // 6. Closed the readers and writers so the program properly saves the file and frees up memory
    br.close();
    bw.close();
  }
}

class Person {
  protected String ma;
  protected String hoTen;
  protected String loaiHopDong;
  protected double heSoLuong;
  
  public Person(String ma, String hoTen, String loaiHopDong, double heSoLuong) {
    super();
    this.ma = ma;
    this.hoTen = hoTen;
    this.loaiHopDong = loaiHopDong;
    this.heSoLuong = heSoLuong;
  }
  
  void output() {
    // Đổi println thành print để các class con có thể in tiếp dữ liệu trên cùng một dòng
    System.out.print("Ma " + this.ma + " ,Ho ten " + this.hoTen + " ,Loai hop dong " + this.loaiHopDong + " ,He so " + this.heSoLuong);
  }
}

class NhanVien extends Person {
  public NhanVien(String ma, String hoTen, String loaiHopDong, double heSoLuong) {
    super(ma, hoTen, loaiHopDong, heSoLuong);
  }
  
  @Override
  void output() {
    super.output(); 
    System.out.println(); // Thêm dòng này để ngắt dòng sau khi in xong Nhân Viên
  }
}

class GiangVien extends Person {
  private double phuCap;
  
  public GiangVien(String ma, String hoTen, String loaiHopDong, double heSoLuong, double phuCap) {
    super(ma, hoTen, loaiHopDong, heSoLuong);
    this.phuCap = phuCap;
  }
  
  @Override
  void output() {
    super.output(); 
    System.out.println(" ,Phu cap " + this.phuCap); 
  }
  
  public double getPhuCap() {
    return phuCap;
  }
  
  public void setPhuCap(double phuCap) {
    this.phuCap = phuCap;
  }
}