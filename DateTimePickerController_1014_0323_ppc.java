// 代码生成时间: 2025-10-14 03:23:20
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ViewsRoute;
import io.micronaut.views.view;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

// 控制器类，处理日期时间选择器的请求
@Controller("/datePicker")
public class DateTimePickerController {

    // 定义日期时间格式
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // GET请求处理，返回日期时间选择器的视图
    @Get("/")
    @view("datePickerView") // 使用Micronaut的视图引擎显示页面
    public Map<String, Object> datePickerView() {
        Map<String, Object> model = new HashMap<>();
        model.put("dateTime", LocalDateTime.now()); // 将当前日期时间作为模型数据返回
        return model;
    }

    // POST请求处理，接收用户选择的日期时间
    @Post("/")
    public String dateTimeSelected(
            @QueryValue String dateTime,
            @NotNull @QueryValue("format") String format) {
        try {
            // 尝试解析传入的日期时间字符串
            LocalDateTime dateTimeParsed = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
            // 将解析后的日期时间以指定格式返回
            return "Selected date time is: " + DATE_TIME_FORMATTER.format(dateTimeParsed);
        } catch (Exception e) {
            // 错误处理，返回错误信息
            return "Error parsing date time: " + e.getMessage();
        }
    }
}
