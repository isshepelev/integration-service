package ppr.ru.integrationservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
public class HomeController {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${grpc.server.port:9090}")
    private String grpcPort;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("serverPort", serverPort);
        model.addAttribute("grpcPort", grpcPort);
        return "index";
    }

    @GetMapping("/graphql-playground")
    public String graphqlPlayground() {
        return "graphql-playground";
    }

    @GetMapping("/graphql-examples")
    public String graphqlExamples() {
        return "graphql-examples";
    }

    @GetMapping("/grpc-examples")
    public String grpcExamples(Model model) {
        model.addAttribute("grpcPort", grpcPort);
        return "grpc-examples";
    }
}
