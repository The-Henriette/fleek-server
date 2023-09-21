package run.fleek.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.certification.AwsRekognitionWrapper;
import run.fleek.application.certification.dto.RekognitionResponseDto;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final AwsRekognitionWrapper awsRekognitionWrapper;

    @GetMapping("/hello")
    public String getHello() {

//        return awsRekognitionWrapper.compareFaces("fleek/samplesource.jpeg", "fleek/sampletarget.png");

        return "Fuck Yeah! - 9-18";
    }


}
