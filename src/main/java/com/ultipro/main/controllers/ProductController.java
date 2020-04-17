package com.ultipro.main.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.ultipro.main.Beans.Product;
import com.ultipro.main.repository.ProductRepository;
import com.ultipro.main.serivice.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController {

    Logger logger;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    GridFsOperations gridFsOperations;

    @PostMapping(value = "/product")
    public Product createProduct(@RequestParam("productImage") MultipartFile productImage, @RequestParam("productDescription") String productDescription,
                                 @RequestParam("productName") String productName, @RequestParam("productColor") String productColor,
                                 @RequestParam("priceForOneQuantity") String priceForOneQuantity, @RequestParam("inventorySize") String inventorySize)
            throws IOException {
        MultipartFile multipartFile = productImage;
        Object imageFileId;

        DBObject metaData = new BasicDBObject();
        metaData.put("organization", "Nisum");
        metaData.put("type", "image");

        // Store file to MongoDB
        imageFileId = gridFsOperations.store(multipartFile.getInputStream(), productName, "image/png", metaData).getId();

        Product product = new Product();
        product.setProductImage((imageFileId != null) ? imageFileId.toString() : "");
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setProductColor(productColor);
        product.setPriceForOneQuantity(priceForOneQuantity);
        product.setInventorySize(inventorySize);
        product.setId(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
        return productRepository.save(product);
    }

    @GetMapping("/product")
    public List getProduct() throws IOException {
        List<Product> list = productRepository.findAll();
        for (Product k : list) {
            GridFSDBFile imageFile = gridFsOperations.findOne(new Query(Criteria.where("_id").is(k.getProductImage())));
            imageFile.writeTo("/Users/nisum/work/react-spring-boot-with-mongodb/shopholic-stop/src/asserts/" + k.getProductImage() + ".png");
        }
        return list;
    }

    @GetMapping("/product/{id}")
    public Optional<Product> getProductById(@RequestParam long id) {
        return productRepository.findById(id);
    }

    @PutMapping("/product/{id}")
    public Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {

        return productRepository.findById(id).map(product -> {
            product.setInventorySize(newProduct.getInventorySize());
            product.setProductColor(newProduct.getProductColor());
            product.setProductDescription(newProduct.getProductDescription());
            product.setProductName(newProduct.getProductName());
            product.setPriceForOneQuantity(newProduct.getPriceForOneQuantity());
            return productRepository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(id);
            return productRepository.save(newProduct);
        });
    }

    @DeleteMapping("/product/{id}")
    public Product deleteProduct(@PathVariable Long id) {
        return productRepository.deleteById(id);
    }
}
