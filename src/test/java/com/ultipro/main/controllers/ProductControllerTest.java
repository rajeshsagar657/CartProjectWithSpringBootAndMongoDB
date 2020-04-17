package com.ultipro.main.controllers;

import com.mongodb.gridfs.GridFSDBFile;
import com.ultipro.main.Beans.Product;
import com.ultipro.main.repository.ProductRepository;
import com.ultipro.main.serivice.SequenceGeneratorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerTest {

    @Mock
    GridFsOperations gridFsOperations;

    @Mock
    SequenceGeneratorService sequenceGeneratorService;

    @Mock
    MultipartFile productImage;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductRepository productRepository;

    private Exception Exception;

    @Test
    public void testGetProductMethodWhenDbReturnsSuccessfulListOfProducts() throws Exception {
        List<Product> list = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductName("product1");
        product1.setInventorySize("200");
        product1.setPriceForOneQuantity("30000");
        list.add(product1);
        GridFSDBFile gridFSDBFile = new GridFSDBFile();
        Mockito.when(productRepository.findAll()).thenReturn(list);
        Mockito.when(gridFsOperations.findOne(Matchers.any())).thenReturn(gridFSDBFile);
        Assert.assertEquals(productController.getProduct(), list);

    }

    @Test
    public void testGetProductByIdMethodWhenDbReturnsProduct() throws Exception {
        Product product = new Product();
        product.setProductName("productName");
        Mockito.when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(product));
        Assert.assertEquals(productController.getProductById(Matchers.anyLong()), java.util.Optional.of(product));

    }

    @Test
    public void testReplaceProductMethodWhenDidChangeAnyDetailsAndDbReturnsProduct() throws Exception {
        Product product = new Product();
        product.setId(33);
        Mockito.when(productRepository.findById(33)).thenReturn(java.util.Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Assert.assertEquals(product, productController.replaceProduct(product, (long) 33));

    }

    @Test
    public void testReplaceProductMethodWhenUserDintChangeAnyDetailsAndDbReturnsProduct() throws Exception {
        Product product = new Product();
        product.setId(33);
        Mockito.when(productRepository.findById(33)).thenReturn(Optional.ofNullable(null));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Assert.assertEquals(product, productController.replaceProduct(product, (long) 33));

    }

    @Test
    public void testReplaceProductMethodWhenUserEnterDetailsToChangeAndDbReturnsProduct() throws Exception {
        Product product = new Product();
        product.setId(33);
        Mockito.when(productRepository.findById(33)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Assert.assertEquals(product, productController.replaceProduct(product, (long) 33));

    }

    @Test
    public void testDeleteProductMethodWhenUserClickOnItem() throws Exception {
        Product product = new Product();
        product.setId(33);
        Mockito.when(productRepository.deleteById((long) 33)).thenReturn(product);
        Assert.assertEquals(product, productController.deleteProduct((long) 33));

    }

    @Test
    public void testCreateProductMethodWhenUserEntersNewProductAndDbReturnsNewProduct() throws Exception {
        Product product = new Product();
        product.setId(33);
        product.setProductName("ProductName");
        product.setPriceForOneQuantity("3000");
        product.setProductDescription("ProductDescription");
        product.setInventorySize("23");
        product.setProductColor("ProductColor");
        GridFSDBFile gridFSDBFile = new GridFSDBFile();
        Mockito.when(sequenceGeneratorService.generateSequence(Matchers.anyString())).thenReturn((long) 44);
        Mockito.when(gridFsOperations.store(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any())).thenReturn(gridFSDBFile);
        Mockito.when(productRepository.save((Product) Matchers.any())).thenReturn(product);
        Assert.assertEquals(product, productController
                .createProduct(productImage, product.getProductDescription(), product.getProductName(), product.getProductColor(),
                               product.getPriceForOneQuantity(), product.getInventorySize()));

    }
}
