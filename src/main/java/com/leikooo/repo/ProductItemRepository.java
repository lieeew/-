package com.leikooo.repo;


import com.leikooo.pojo.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
    // 向数据库之中添加新的商品类目
    @Modifying
    @Query(value = "INSERT INTO PRODUCT_ITEM (id, name, pid) values ((select max(id) + 1 from PRODUCT_ITEM), ?1, ?2)", nativeQuery = true)
    void addItem(String name, int pid);

    // 删除商品类目及其子目录
    @Modifying
    @Query(value = "delete from PRODUCT_ITEM where id = ?1 or pid = ?2", nativeQuery = true)
    void delItem(int id, int pid);

    // 根据类的 name 和 pid 进行查询商品
    ProductItem findByNameAndPid(String name, int pid);
}
