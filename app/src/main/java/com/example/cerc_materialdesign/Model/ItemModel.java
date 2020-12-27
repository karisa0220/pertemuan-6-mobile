package com.example.cerc_materialdesign.Model;

public class ItemModel {
    private String desc, image, gaji, name, req1, req2, req3, req4, req5, req6, req7, req8, req9, req10, category, codeProduct;

    public  ItemModel(){}

    public ItemModel(String desc, String image, String gaji, String name, String category, String codeProduct, String req1, String req2, String req3, String req4, String req5, String req6, String req7, String req8, String req9, String req10) {
        this.desc = desc;
        this.image = image;
        this.name = name;
        this.gaji = gaji;
        this.req1 = req1;
        this.req1 = req2;
        this.req2 = req3;
        this.req3 = req4;
        this.req4 = req5;
        this.req5 = req6;
        this.req6 = req7;
        this.req7 = req8;
        this.req8 = req8;
        this.req9 = req9;
        this.req10 = req10;
        this.category = category;
        this.codeProduct = codeProduct;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGaji() {
        return gaji;
    }

    public void setGaji(String gaji) {
        this.gaji = gaji;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReq1() { return req1; }

    public void setReq1(String req1) { this.req1 = req1; }

    public String getReq2() { return req2; }

    public void setReq2(String req2) { this.req2 = req2; }

    public String getReq3() { return req3; }

    public void setReq3(String req3) { this.req3 = req3; }

    public String getReq4() { return req4; }

    public void setReq4(String req4) { this.req4 = req4; }

    public String getReq5() { return req5; }

    public void setReq5(String req5) { this.req5 = req5; }

    public String getReq6() { return req6; }

    public void setReq6(String req6) { this.req6 = req6; }

    public String getReq7() { return req7; }

    public void setReq7(String req7) { this.req7 = req7; }

    public String getReq8() { return req8; }

    public void setReq8(String req8) { this.req8 = req8; }

    public String getReq9() { return req9; }

    public void setReq9(String req9) { this.req9 = req9; }

    public String getReq10() { return req10; }

    public void setReq10(String req110) { this.req10 = req10; }
}

