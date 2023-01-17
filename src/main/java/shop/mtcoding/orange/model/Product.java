package shop.mtcoding.orange.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product { // Entity라고 합니다.
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
    private Timestamp createdAt;

}
