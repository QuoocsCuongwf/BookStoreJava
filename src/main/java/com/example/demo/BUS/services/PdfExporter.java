package com.example.demo.BUS.services;

import com.example.demo.model.HoaDon;
import com.example.demo.model.ChiTietHoaDon;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.barcodes.Barcode128;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfExporter {

    public static void xuatHoaDonPDF(HoaDon hoaDon, List<ChiTietHoaDon> chiTietList, String filePath) throws Exception {
        // Kiểm tra và xóa file PDF nếu đã tồn tại
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        // Khởi tạo PDF
        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Load font Unicode (Roboto) để hỗ trợ tiếng Việt
        String fontPath = "D:\\downloads\\Roboto\\Roboto-VariableFont_wdth,wght.ttf";
        PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
        document.setFont(font);

        // Thêm logo ở góc trên bên trái
        String logoPath = "D:\\code\\BookStoreJava\\src\\main\\resources\\asset\\img\\logoSgu.png";
        Image logo = new Image(ImageDataFactory.create(logoPath))
                .setWidth(100)
                .setHeight(100)
                .setFixedPosition(40, pdf.getDefaultPageSize().getHeight() - 90)
                .setMarginBottom(10);
        document.add(logo);

        // Tạo Div bao quanh hóa đơn với viền
        Div invoiceContainer = new Div()
                .setBorder(new SolidBorder(ColorConstants.BLACK, 1))
                .setPadding(10)
                .setMargin(20)
                .setMarginTop(60); // Khoảng cách để tránh chồng lấn logo

        // Tiêu đề hóa đơn
        invoiceContainer.add(new Paragraph("HÓA ĐƠN BÁN HÀNG")
                .setFont(font)
                .setBold()
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

// Tạo Div để chứa toàn bộ thông tin và mã vạch
        Div infoAndBarcode = new Div()
                .setKeepTogether(true)
                .setMarginBottom(10)
                .setHorizontalAlignment(HorizontalAlignment.LEFT); // Đảm bảo căn trái

// Tạo Div chứa thông tin hóa đơn (căn trái)
        Div infoDiv = new Div()
                .setWidth(250)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        infoDiv.add(new Paragraph("Mã Hóa Đơn: " + hoaDon.getMahd())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(5));
        infoDiv.add(new Paragraph("Ngày Lập: " + hoaDon.getNgaylap())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(5));
        infoDiv.add(new Paragraph("Mã Nhân Viên: " + hoaDon.getManv())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(5));
        infoDiv.add(new Paragraph("Mã Khách Hàng: " + hoaDon.getMakh())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(15));

// Tạo bảng với 2 cột: một cho thông tin và một cho mã vạch
        Table tableInfor = new Table(2)
                .setWidth(UnitValue.createPercentValue(100)) // Chiếm toàn bộ chiều rộng
                .setKeepTogether(true);

// Cột 1: Thông tin hóa đơn (căn trái)
        Cell infoCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(5);
        infoCell.add(new Paragraph("Mã Hóa Đơn: " + hoaDon.getMahd())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(5));
        infoCell.add(new Paragraph("Ngày Lập: " + hoaDon.getNgaylap())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(5));
        infoCell.add(new Paragraph("Mã Nhân Viên: " + hoaDon.getManv())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(5));
        infoCell.add(new Paragraph("Mã Khách Hàng: " + hoaDon.getMakh())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(15));

// Cột 2: Mã vạch (căn phải)
        Barcode128 barcode = new Barcode128(pdf);
        barcode.setCode(hoaDon.getMahd());
        barcode.setCodeType(Barcode128.CODE128);
        Image barcodeImage = new Image(barcode.createFormXObject(pdf))
                .setMargins(0,0,0,0)
                .setPaddings(0,0,0,0)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .setWidth(150); // Giữ tỷ lệ tự nhiên


        // Cột 2: Mã vạch (sát viền phải hoàn toàn)
        Cell barcodeCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.TOP)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .setPaddings(0,0,0,50) // Xóa mọi khoảng cách
                .setMarginRight(0) // Đảm bảo không có lề thừa
                .add(barcodeImage);


// Thêm các ô vào bảng
        tableInfor.addCell(infoCell);
        tableInfor.addCell(barcodeCell);

// Thêm bảng vào invoiceContainer
        invoiceContainer.add(tableInfor);
        // Bảng chi tiết hóa đơn
        float[] columnWidths = {50, 150, 100, 100, 100}; // Độ rộng cột
        Table table = new Table(columnWidths)
                .setHorizontalAlignment(HorizontalAlignment.CENTER) // Căn giữa bảng
                .setWidth(500) // Chiều rộng cố định
                .setMarginBottom(15);

        // Tiêu đề bảng
        table.addHeaderCell(new Cell()
                .add(new Paragraph("STT").setBold())
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
        table.addHeaderCell(new Cell()
                .add(new Paragraph("Mã SP").setBold())
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
        table.addHeaderCell(new Cell()
                .add(new Paragraph("Số lượng").setBold())
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
        table.addHeaderCell(new Cell()
                .add(new Paragraph("Đơn giá").setBold())
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
        table.addHeaderCell(new Cell()
                .add(new Paragraph("Thành tiền").setBold())
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));

        // Thêm dữ liệu vào bảng
        int i = 1;
        for (ChiTietHoaDon ct : chiTietList) {
            table.addCell(new Cell()
                    .add(new Paragraph(String.valueOf(i++)))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
            table.addCell(new Cell()
                    .add(new Paragraph(ct.getMasp()))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
            table.addCell(new Cell()
                    .add(new Paragraph(String.valueOf(ct.getSl())))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
            table.addCell(new Cell()
                    .add(new Paragraph(String.valueOf(ct.getDongia())))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
            table.addCell(new Cell()
                    .add(new Paragraph(String.valueOf(ct.getThanhtien())))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
        }

        invoiceContainer.add(table);

        // Tổng tiền
        invoiceContainer.add(new Paragraph("TỔNG TIỀN: " + hoaDon.getTongtien() + " VND")
                .setFont(font)
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(10)
                .setMarginBottom(15));

        // Thêm container vào tài liệu
        document.add(invoiceContainer);

        // Đóng tài liệu
        document.close();
    }

    public static void main(String[] args) throws Exception {
        HoaDon hoaDon = new HoaDon();
        HoaDonServices hoaDonServices = new HoaDonServices();
        hoaDonServices.getHoaDonList();
        hoaDon = hoaDonServices.findByIdHoaDon("HD1");

        ChiTietHoaDonServices chiTietHoaDonServices = new ChiTietHoaDonServices();
        List<ChiTietHoaDon> list = chiTietHoaDonServices.getList("HD1");

        String outputPath = "D:\\code\\BookStoreJava\\src\\main\\resources\\Output\\HoaDon\\" + hoaDon.getMahd() + ".pdf";
        PdfExporter.xuatHoaDonPDF(hoaDon, list, outputPath);
    }
}