package package1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JMain {
	// 1. Added "throws IOException" so the main method can handle cau2()'s exception
	public static void main(String[] args) throws IOException {
		cau2();
		cau3();
	}
	
    public static void cau3() throws IOException {
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