package com.sastore.web.dao;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.collections.ProductsCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.utils.Page;
import com.sastore.web.utils.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Objects;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public class ProductsDaoImpl implements ProductsDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ObjCollection<ProductEntity> getProducts(Integer page, Integer limit, ProductFilter filter) {
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
                CallableStatement cstmt = conn.prepareCall("{? = call get_products(?, ?, ?, ?, ?, ?)}")) {

            conn.setAutoCommit(false);

            cstmt.setInt(1, page);
            cstmt.setInt(2, limit);

            if (filter.getProductId() == null) {
                cstmt.setNull(3, Types.BIGINT);
            } else {
                cstmt.setLong(3, filter.getProductId());
            }

            if (StringUtils.isEmpty(filter.getTitle())) {
                cstmt.setString(4, null);
            } else {
                cstmt.setString(4, filter.getTitle());
            }

            cstmt.registerOutParameter(5, Types.BIGINT);
            cstmt.registerOutParameter(6, Types.BIGINT);
            cstmt.registerOutParameter(7, Types.REF_CURSOR);

            cstmt.execute();

            Long total = cstmt.getLong(5);
            Long noPagedTotal = cstmt.getLong(6);
            ResultSet rset = (ResultSet) cstmt.getObject(7);

            ObjCollection<ProductEntity> collection = new ProductsCollection<>();

            while (rset.next()) {
                ProductEntity product = new ProductEntity();
                product.setProductId(rset.getLong("product_id"));
                product.setTitle(rset.getString("title"));
                product.setPrice(rset.getDouble("price"));
                product.setAvailableQuantity(rset.getInt("avail_quant"));
                product.setViews(rset.getInt("views"));
                product.setDescription(rset.getString("description"));
                product.setStatus(rset.getInt("status"));
                product.setMainImage(rset.getString("main_image"));
                product.setAddedOn(rset.getTimestamp("added_on"));
                product.setEditedOn(rset.getTimestamp("edited_on"));

                collection.getCollection().add(product);
            }

            long pagedTotal = collection.getCollection().size();

            int start = (page - 1) * limit + 1;
            int end = start + collection.getCollection().size() - 1;

            Page pageWrapper = new PageImpl();
            pageWrapper.setCurrentPage(page);
            pageWrapper.setMaxElementsPerPage(limit);
            pageWrapper.setStartPage(start);
            pageWrapper.setEndPage(end);
            pageWrapper.setNoPagedTotal(noPagedTotal);
            pageWrapper.setPagedTotal(pagedTotal);
            pageWrapper.setTotal(total);

            collection.setPage(pageWrapper);

            conn.commit();

            return collection;
        } catch (Exception e) {
            log.error("Error getting products!", e);
        }

        return null;
    }
}
