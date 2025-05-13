//package com.example.demo.databaseAccesssObject.deserialize;
//
//import com.example.demo.model.NhaXuatBan;
//import com.example.demo.model.SanPham;
//import com.example.demo.model.TacGia;
//import com.example.demo.model.TheLoai;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.ObjectCodec;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//
//import java.io.IOException;
//
//public class SanPhamDeserializer extends StdDeserializer<SanPham> {
//
//    public SanPhamDeserializer() {
//        this(null);
//    }
//
//    public SanPhamDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public SanPham deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
//        ObjectCodec codec = jp.getCodec();
//        JsonNode node = codec.readTree(jp);
//
//        SanPham sp = new SanPham();
//        sp.setMasp(node.get("MASP").asText());
//        sp.setTensp(node.get("TENSP").asText());
//        sp.setSl(node.get("SL").asInt());
//        sp.setNamxb(node.get("NAMXB").asInt());
//        sp.setDongia(node.get("DONGIA").asInt());
//        sp.setSotrang(node.get("SOTRANG").asInt());
//        sp.setAnhbia(node.get("ANHBIA").asText().trim());
//
//        // TheLoai
//        JsonNode theLoaiNode = node.get("MATL");
//
//        TheLoai theLoai;
//        if (theLoaiNode.isObject()) {
//            // JSON lồng
//            theLoai = new TheLoai(
//                    theLoaiNode.get("MATL").asText(),
//                    theLoaiNode.get("TENTL").asText()
//            );
//        } else {
//            // JSON phẳng
//            theLoai = new TheLoai(
//                    theLoaiNode.asText(),
//                    node.get("TENTL").asText()
//            );
//        }
//        sp.setMatl(theLoai);
//
//        // TacGia
//        TacGia tg ;
//        JsonNode nodeTG=node.get("MATG");
//        if (nodeTG.isObject()) {
//            tg=new TacGia (
//                    nodeTG.get("MATG").asText(),
//                    nodeTG.get("HOTG").asText(),
//                    nodeTG.get("TENTG").asText(),
//                    nodeTG.get("QUEQUAN").asText(),
//                    nodeTG.get("NAMSINH").asInt()
//            );
//        } else {
//            tg=new TacGia ();
//            tg.setMatg(node.get("MATG").asText());
//            tg.setHotg(node.get("HOTG").asText());
//            tg.setTentg(node.get("TENTG").asText());
//            tg.setQuequan(node.get("QUEQUAN").asText());
//            tg.setNamsinh(node.get("NAMSINH").asInt());
//        }
//
//        sp.setMatg(tg);
//
//        // NhaXuatBan
//        NhaXuatBan nxb;
//        JsonNode nodeNXB=node.get("MANXB");
//        if (nodeNXB.isObject()) {
//            nxb=new NhaXuatBan (
//                    nodeNXB.get("MANXB").asText(),
//                    nodeNXB.get("TENNXB").asText(),
//                    nodeNXB.get("DIACHI").asText(),
//                    nodeNXB.get("SDT").asText()
//            );
//        } else {
//            nxb=new NhaXuatBan ();
//            nxb.setManxb(node.get("MANXB").asText());
//            nxb.setTennxb(node.get("TENNXB").asText());
//            nxb.setDiachi(node.get("DIACHI").asText());
//            nxb.setSdt(node.get("SDT").asText());
//        }
//
//        sp.setManxb(nxb);
//
//        return sp;
//    }
//}
