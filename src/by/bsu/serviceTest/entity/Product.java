package by.bsu.serviceTest.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Product {

    private long id;

    private String name;

    private int price;

    public Product() {}

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Product(long id, String name, int price) {
        this(name, price);
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
