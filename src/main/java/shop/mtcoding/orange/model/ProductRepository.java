package shop.mtcoding.orange.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

// interface 가지는 조건? 인터페이스는 필드를 가질 수 없고, 추상메서드를 가진다.
@Mapper
public interface ProductRepository { // ->ProductRepositoryImpl 이라는 파일이 만들어지면서 ioc에 올라간다.
    public List<Product> findAll(); // findAll = select id값

    public Product findOne(int id);

    public int insert(@Param("name") String name, @Param("price") int price, @Param("qty") int qty);
    // -1 DB에러, 1 변경된 행이 1건, 0 변경된 행이 없다.

    public int delete(@Param("id") int id);

    public int update(@Param("id") int id, @Param("name") String name, @Param("price") int price,
            @Param("qty") int qty);
}
