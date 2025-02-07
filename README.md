<strong>** DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS. ** </strong>

# WESTERN GOVERNORS UNIVERSITY 
## D287 – JAVA FRAMEWORKS

C.  Customize the HTML user interface for your customer’s application. The user interface should include the shop name, the product names, and the names of the parts.

Changes to mainscreen.html:
    line 14: Updated title to "Chris's Computers"
    line 19: Updated h1 to "Chris's Computers"
    line 24: Updated h2 to "System Components"
    line 53: Updated h2 to "Computers"

D.  Add an “About” page to the application to describe your chosen customer’s company to web viewers and include navigation to and from the “About” page and the main screen.

Added src/main/resources/templates/about.html
Added src/main/resources/static/css/styles.css
Changes to mainscreen.html:
    line 13: Added style sheet to page: <link rel="stylesheet" th:href="@{/css/styles.css}">
    line 18: added nav bar
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <a class="navbar-brand" href="/">Chris's Computers</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/about">About Us</a>
                    </li>
                </ul>
                </div>
            </div>
        </nav>
    line 38: changed original h1 to <h1 class="text-align-center">New Inventory!</h1>
    line 112: added footer
        <footer class="bg-primary">
            <p class="mb-0">© 2025 Chris's Computers. All rights reserved.</p>
        </footer>

Changes to src/main/java/com/example/demo/controllers/MainScreenControllerr.java:
    line 56: added route/mapping for about us
        @GetMapping("/about")
            public String about() {
            return "about.html";
        }

E.  Add a sample inventory appropriate for your chosen store to the application. You should have five parts and five products in your sample inventory and should not overwrite existing data in the database.
Note: Make sure the sample inventory is added only when both the part and product lists are empty. When adding the sample inventory appropriate for the store, the inventory is stored in a set so duplicate items cannot be added to your products. When duplicate items are added, make a “multi-pack” part.

Changes to com/example/demo/bootstrap/BootStrapData.java
    line 16: added import java.util.*; for access to hashset.

     line 69: added seed products and parts
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

        InhousePart processor = new InhousePart();
        processor.setId(2);
        processor.setName("AMD 9800X3D");
        processor.setPrice(449.99);
        processor.setInv(20);


        InhousePart motherboard = new InhousePart();
        motherboard.setId(3);
        motherboard.setName("Motherboard");
        motherboard.setPrice(249.99);
        motherboard.setInv(20);


        InhousePart tower = new InhousePart();
        tower.setId(4);
        tower.setName("Case");
        tower.setPrice(49.99);
        tower.setInv(20);


        InhousePart ram = new InhousePart();
        ram.setId(5);
        ram.setName("DDR5 64GB");
        ram.setPrice(199.99);
        ram.setInv(20);

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
Changes to src/main/java/com/example/demo/domain/Product.java:
        line 94: added multiPackProduct method to add multi-pack to product name.  
            // Consumed by AddInHousePartController when adding a part.
            // When a part already exists in an order, we add a multi-pack variant.
            // This method updates the name of the product to include (multi-pack)".
            public void multiPackProduct() {
                this.setName(this.getName() + " (multi-pack)");
            }

Changes to src/main/java/com/example/demo/controllers/AddProductController.java
    line 19: imported  hashset
        import java.util.HashSet;
        import java.util.Set;
    
    line 35: Added listProducts hashset
        private Set<Product> listProducts = new HashSet<>();

    line 36: Added boolean to help manage determine if a product already exists
        private boolean productAlreadyExists = false;

    line 95: Updated submitForm method to include updating a product to multipack if it already exists.
        // Iterate through products to see if our new product matches one already
        // present.
        for (Product thing : listProducts) {
            if (thing.getName().equals(product.getName())) {
                productAlreadyExists = true;
            } else {
                productAlreadyExists = false;
            }
        }

        // If product already exists create a multi-pack version.
        if (productAlreadyExists == true) {
            product.multiPackProduct();
            listProducts.add(product);
        } else {
            listProducts.add(product);
        }

Changes to src/main/java/com/example/demo/domain/Part.java:
    line 53: added multiPackProduct method to add multi-pack to part name.
        public void multiPackPart() { this.setName(this.getName() + " (multi-pack)"); }

Changes to src/main/java/com/example/demo/controllers/AddInhousePartController.java
    line 20: imported hashset and set
        import java.util.HashSet;
        import java.util.Set;

    line 32: Added boolean to help determine if a part already exists
        private boolean partAlreadyExists = false;

    line 33: Added listParts hashset
        private Set<Part> listParts = new HashSet<>();

    line 51: Updated submitForm method to include updating a part to multipack if it already exists.
        // Iterate through parts to see if our new part matches one already
        // present.
        for (Part thing : listParts) {
            if (thing.getName().equals(part.getName())) {
                partAlreadyExists = true;
            } else {
                partAlreadyExists = false;
            }
        }

        // If part already exists create a multi-pack version.
        if (partAlreadyExists == true) {
            part.multiPackPart();
            listParts.add(part);
        } else {
            listParts.add(part);
        }

Changes to src/main/java/com/example/demo/controllers/AddOutsourcedPartController.java
    line 22: imported hashset
        import java.util.HashSet;
        import java.util.Set;
    
    line 33: Added boolean to help determine if a part already exists
        private boolean partAlreadyExists = false;

    line 34: Added listParts hashset
        private Set<Part> listParts = new HashSet<>();

    line 52: Updated submitForm method to include updating a part to multipack if it already exists.
        // Iterate through parts to see if our new parts matches one already
        // present.
        for (Part thing : listParts) {
            if (thing.getName().equals(part.getName())) {
                partAlreadyExists = true;
            } else {
                partAlreadyExists = false;
            }
        }

        // If part already exists create a multi-pack version.
        if (partAlreadyExists == true) {
            part.multiPackPart();
            listParts.add(part);
        } else {
            listParts.add(part);
        }

Changes to static/css/styles.css:
    line 9: updated footer styling


F.  Add a “Buy Now” button to your product list. Your “Buy Now” button must meet each of the following parameters:
•  The “Buy Now” button must be next to the buttons that update and delete products.
•  The button should decrement the inventory of that product by one. It should not affect the inventory of any of the associated parts.
•  Display a message that indicates the success or failure of a purchase.

Added purchase failure template src/main/resources/templates/purchase_failure.html

Added purchase success template src/main/resources/templates/purchase_success.html

Changes to src/main/java/com/example/demo/controllers/AddProductController.java:
    line 199: Added mapping and controller method
        // Decrement the inventory of that product by one.
        @GetMapping("/decrementProduct")
        public String decrementProduct(@RequestParam("productID") int theId, Model theModel) {
                ProductService repo = context.getBean(ProductServiceImpl.class);
                Product product3 = repo.findById(theId);
                int inv = product3.getInv();
                if (inv > 0) {
                    product3.setInv(inv - 1);
                repo.save(product3);
                return "purchase_success";
            } else {
                return "purchase_failure";
            }
        }
            

Changes to src/main/resources/templates/mainscreen.html:
    line 100: added buy button
        <td><a th:href="@{/decrementProduct(productID=${tempProduct.id})}" class="btn btn-primary btn-sm mb-3">Buy Now</a>


G.  Modify the parts to track maximum and minimum inventory by doing the following:
•  Add additional fields to the part entity for maximum and minimum inventory.
    changes to src/main/java/com/example/demo/domain/Part.java
        line 31:
            @Column(name = "MAX_INV")
            int max_inv = 1;
            @Column(name = "MIN_INV")
            int min_inv = 1;
        line 57:
            public Part(String name, double price, int inv, int max_inv, int min_inv) {
                this.name = name;
                this.price = price;
                this.inv = inv;
                this.max_inv = max_inv;
                this.min_inv = min_inv;
            }
    
            public Part(long id, String name, double price, int inv, int max_inv, int min_inv) {
                this.id = id;
                this.name = name;
                this.price = price;
                this.inv = inv;
                this.max_inv = max_inv;
                this.min_inv = min_inv;
            }

        Line 130:
            public int getMaxInv() { return max_inv; }

            public void setMaxInv(int max_inv) { this.max_inv = max_inv; }
        
            public int getMinInv() { return min_inv; }
        
            public void setMinInv(int min_inv) { this.min_inv = min_inv; }

•  Modify the sample inventory to include the maximum and minimum fields.
    Changes to src/main/java/com/example/demo/bootstrap/BootStrapData.java at line 93-129
•  Add to the InhousePartForm and OutsourcedPartForm forms additional text inputs for the inventory so the user can set the maximum and minimum values.
    Changes lines 16-25 in src/main/resources/templates/InhousePartForm.html to include labels for each field and added min and max inventory fields.
    Changes lines 16-26 in src/main/resources/templates/OutsourcedPartForm.html to include labels for each field and added min and max inventory fields.
•  Rename the file the persistent storage is saved to.
    Commented out old db line 6 and added new line with content: spring.datasource.url=jdbc:h2:file:~/chriscomputers
•  Modify the code to enforce that the inventory is between or at the minimum and maximum value.
    Added validation method to src/main/java/com/example/demo/domain/Part.java:
        line 142:
            public void validateInvLimits() {
                if (this.inv < this.min_inv) {
                    throw new RuntimeException("This value is less than required minimum inventory.");
                }
                else if (this.inv > this.max_inv) {
                    throw new RuntimeException("This value is more than required maximum inventory.");
                }
            }
    updated src/main/java/com/example/demo/service/PartServiceImpl.java line 58 to save method to call validateInvLimits() prior to saving.
    updated src/main/java/com/example/demo/service/OutsourcedPartServiceImpl.java line 52 to save method to call validateInvLimits() prior to saving.
    updated src/main/java/com/example/demo/service/InhousePartServiceImpl.java line 54 to save method to call validateInvLimits() prior to saving.

H.  Add validation for between or at the maximum and minimum fields. The validation must include the following:
•  Display error messages for low inventory when adding and updating parts if the inventory is less than the minimum number of parts.
•  Display error messages for low inventory when adding and updating products lowers the part inventory below the minimum.
•  Display error messages when adding and updating parts if the inventory is greater than the maximum.
Created MaximumInvValidator at src/main/java/com/example/demo/validators/MaximumInvValidator.java:
    package com.example.demo.validators;
    
    import com.example.demo.domain.Part;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.ApplicationContext;
    
    import javax.validation.ConstraintValidator;
    import javax.validation.ConstraintValidatorContext;
    
    /**
    *
      *
      *
      *
    */
    public class MaximumInvValidator implements ConstraintValidator<ValidMaximumInv, Part> {
    @Autowired
    private ApplicationContext context;
    public static ApplicationContext myContext;
    
        @Override
        public void initialize(ValidMaximumInv constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }
    
        @Override
        public boolean isValid(Part part, ConstraintValidatorContext constraintValidatorContext) {
            return part.getInv() <= part.getMaxInv();
        }
    }

Created MinimumInvValidator at src/main/java/com/example/demo/validators/MinimumInvValidator.java:
    package com.example.demo.validators;
    
    import com.example.demo.domain.Part;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.ApplicationContext;
    import javax.validation.ConstraintValidator;
    import javax.validation.ConstraintValidatorContext;
    
    /**
    *
      *
      *
      *
    */
    public class MinimumInvValidator implements ConstraintValidator<ValidMinimumInv, Part> {
    @Autowired
    private ApplicationContext context;
    public static ApplicationContext myContext;
    
        @Override
        public void initialize(ValidMinimumInv constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }
    
        @Override
        public boolean isValid(Part part, ConstraintValidatorContext constraintValidatorContext) {
            return part.getInv() >= part.getMinInv();
        }
    
    }

Created ValidMaximumInv at src/main/java/com/example/demo/validators/ValidMaximumInv.java:
    package com.example.demo.validators;
    
    import javax.validation.Constraint;
    import javax.validation.Payload;
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    
    /**
    *
      *
      *
      *
    */
    @Constraint(validatedBy = {MaximumInvValidator.class})
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ValidMaximumInv {
        String message() default "Inventory cannot be greater than Max Inventory.";
        Class<?> [] groups() default {};
        Class<? extends Payload> [] payload() default {};
    }

Created ValidMinimumInv at src/main/java/com/example/demo/validators/ValidMinimumInv.java:
    package com.example.demo.validators;
    
    import javax.validation.Constraint;
    import javax.validation.Payload;
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    
    /**
    *
      *
      *
      *
    */
    @Constraint(validatedBy = {MinimumInvValidator.class})
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ValidMinimumInv {
        String message() default "Inventory cannot be less than Min Inventory.";
        Class<?> [] groups() default {};
        Class<? extends Payload> [] payload() default {};
    }

Added field to display error in OutsourcedPartForm.html on line 23:
    <div th:if="${#fields.hasErrors()}">
        <ul>
            <li th:each="err : ${#fields.allErrors()}" th:text="${err}" class="error"/>
        </ul>
    </div>

Added field to display error in InhousePartForm.html on line 21:
    <div th:if="${#fields.hasErrors()}">
        <ul>
            <li th:each="err : ${#fields.allErrors()}" th:text="${err}" class="error"/>
        </ul>
    </div>

Uploaded logic in isValid method found in src/main/java/com/example/demo/validators/EnufPartsValidator.java:
    line 36-44:
            if (p.getInv() < (product.getInv() - myProduct.getInv())) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Part count too low for: " + p.getName()).addConstraintViolation();
                return false;
            }
        }
    return true;
    }
    return false;
    
Added validators to src/main/java/com/example/demo/domain/Part.java:
    line 4-5:
        import com.example.demo.validators.ValidMaximumInv;
        import com.example.demo.validators.ValidMinimumInv;
    
    line 19-20:
        @ValidMinimumInv
        @ValidMaximumInv


I.  Add at least two unit tests for the maximum and minimum fields to the PartTest class in the test package.
    Added tests to src/test/java/com/example/demo/domain/PartTest.java at lines 160-195.

J.  Remove the class files for any unused validators in order to clean your code.


K.  Demonstrate professional communication in the content and presentation of your submission.