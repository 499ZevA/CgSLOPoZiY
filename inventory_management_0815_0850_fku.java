// 代码生成时间: 2025-08-15 08:50:15
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Delete;
# TODO: 优化性能
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.HttpResponse;
import io.micronaut.core.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
# FIXME: 处理边界情况

@Controller("/api/inventory")
public class InventoryManagement {
# TODO: 优化性能

    private final ConcurrentHashMap<Integer, Product> products = new ConcurrentHashMap<>();
    private static int nextId = 1;

    public static class Product {
        private final int id;
        private String name;
# 添加错误处理
        private double price;
        private int quantity;

        public Product(String name, double price, int quantity) {
            this.id = nextId++;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        // Getters and setters
        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    @Post("/add")
    public HttpResponse<Product> addProduct(@Body Product product) {
        products.putIfAbsent(product.getId(), product);
        return HttpResponse.ok(product);
    }

    @Put("/update/{id}")
    public HttpResponse<Product> updateProduct(@Nullable @Body Product product, @PathVariable int id) {
# NOTE: 重要实现细节
        if (product != null) {
            products.replace(id, product);
            return HttpResponse.ok(products.get(id));
# NOTE: 重要实现细节
        } else {
            return HttpResponse.notFound();
        }
    }

    @Delete("/remove/{id}")
# NOTE: 重要实现细节
    public HttpResponse<Void> removeProduct(@PathVariable int id) {
        products.remove(id);
# 改进用户体验
        return HttpResponse.ok();
    }

    @Get("/{id}")
    public HttpResponse<Product> getProduct(@PathVariable int id) {
        return Optional.ofNullable(products.get(id))
            .map(HttpResponse::ok)
            .orElse(HttpResponse.notFound());
    }

    @Get("/list")
    public HttpResponse<List<Product>> listProducts() {
        return HttpResponse.ok(new ArrayList<>(products.values()));
    }
}
