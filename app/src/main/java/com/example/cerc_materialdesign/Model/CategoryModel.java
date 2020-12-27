package com.example.cerc_materialdesign.Model;

public class CategoryModel {
    private String desc, image, name, job1, job2, job3, job4, job5, job6, job7, job8, job9, job10, codeProduct;
    //HashMap<String, Object> cat =

    public CategoryModel(){}

    public CategoryModel(String desc, String image, String gaji, String name, String codeProduct, String job1, String job2, String job3, String job4, String job5, String job6, String job7, String job8, String job9, String job10) {
        this.desc = desc;
        this.image = image;
        this.name = name;
        this.job1 = job1;
        this.job2 = job2;
        this.job3 = job3;
        this.job4 = job4;
        this.job5 = job5;
        this.job6 = job6;
        this.job7 = job7;
        this.job8 = job8;
        this.job9 = job9;
        this.job10 = job10;
        this.codeProduct = codeProduct;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob1() { return job1; }

    public void setJob1(String job1) { this.job1 = job1; }

    public String getJob2() { return job2; }

    public void setJob2(String job2) { this.job2 = job2; }

    public String getJob3() { return job3; }

    public void setJob3(String job3) { this.job3 = job3; }

    public String getJob4() { return job4; }

    public void setJob4(String job4) { this.job4 = job4; }

    public String getJob5() { return job5; }

    public void setJob5(String job5) { this.job5 = job5; }

    public String getJob6() { return job6; }

    public void setJob6(String job6) { this.job6 = job6; }

    public String getJob7() { return job7; }

    public void setJob7(String job7) { this.job7 = job7; }

    public String getJob8() { return job8; }

    public void setJob8(String job8) { this.job8 = job8; }

    public String getJob9() { return job9; }

    public void setJob9(String job9) { this.job9 = job9; }

    public String getJob10() { return job10; }

    public void setJob10(String job10) { this.job10 = job10; }
}

