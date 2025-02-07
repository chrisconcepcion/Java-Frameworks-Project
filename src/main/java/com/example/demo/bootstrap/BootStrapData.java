package com.example.demo.bootstrap;
import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

       /*
        OutsourcedPart o= new OutsourcedPart();
        o.setCompanyName("Western Governors University");
        o.setName("out test");
        o.setInv(5);
        o.setPrice(20.0);
        o.setId(100L);
        outsourcedPartRepository.save(o);
        OutsourcedPart thePart=null;
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            if(part.getName().equals("out test"))thePart=part;
        }

        System.out.println(thePart.getCompanyName());
        */
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            System.out.println(part.getName()+" "+part.getCompanyName());
        }

        /*
        Product bicycle= new Product("bicycle",100.0,15);
        Product unicycle= new Product("unicycle",100.0,15);
        productRepository.save(bicycle);
        productRepository.save(unicycle);
        */

        // Setup hashset for sample products and sample parts.
        Set<Product> seedProducts = new HashSet<Product>();
        Set<Part> seedParts = new HashSet<>();

        // Set seed products.
        Product superStream = new Product(1, "Super Streaming Computer", 5999.00, 5);
        Product topNotch = new Product(2, "Top Notch FPS Gamer Computer", 4999.00, 5);
        Product basicStreamer = new Product(3, "Basic Streamer Computer", 999.00, 5);
        Product basicGamer = new Product(4, "Basic Gaming Computer", 499.00, 5);
        Product basicDad = new Product(5, "Basic Dad Computer", 399.00, 5);

        // Adds seed products to the database.
        seedProducts.add(superStream);
        seedProducts.add(topNotch);
        seedProducts.add(basicStreamer);
        seedProducts.add(basicGamer);
        seedProducts.add(basicDad);

        // Create seed parts,
        InhousePart gpu = new InhousePart();
        gpu.setId(1);
        gpu.setName("RTX 5090");
        gpu.setPrice(2999.99);
        gpu.setInv(20);
        gpu.setMaxInv(20);
        gpu.setMinInv(1);

        InhousePart processor = new InhousePart();
        processor.setId(2);
        processor.setName("AMD 9800X3D");
        processor.setPrice(449.99);
        processor.setInv(20);
        processor.setMaxInv(20);
        processor.setMinInv(1);


        InhousePart motherboard = new InhousePart();
        motherboard.setId(3);
        motherboard.setName("Motherboard");
        motherboard.setPrice(249.99);
        motherboard.setInv(20);
        motherboard.setMaxInv(20);
        motherboard.setMinInv(1);


        InhousePart tower = new InhousePart();
        tower.setId(4);
        tower.setName("Case");
        tower.setPrice(49.99);
        tower.setInv(20);
        tower.setMaxInv(20);
        tower.setMinInv(1);


        InhousePart ram = new InhousePart();
        ram.setId(5);
        ram.setName("DDR5 64GB");
        ram.setPrice(199.99);
        ram.setInv(20);
        ram.setMaxInv(20);
        ram.setMinInv(1);

        // Add seed parts to database.
        seedParts.add(gpu);
        seedParts.add(processor);
        seedParts.add(motherboard);
        seedParts.add(tower);
        seedParts.add(ram);

        // If we have no parts or products in the database
        // seed the database.
        if (partRepository.count() == 0 && productRepository.count() == 0) {
            for (Product product : seedProducts) {
                productRepository.save(product);
            }

            for (Part part : seedParts) {
                partRepository.save(part);
            }
        }

        System.out.println("Started in Bootstrap");
        System.out.println("Number of Products"+productRepository.count());
        System.out.println(productRepository.findAll());
        System.out.println("Number of Parts"+partRepository.count());
        System.out.println(partRepository.findAll());



    }
}
