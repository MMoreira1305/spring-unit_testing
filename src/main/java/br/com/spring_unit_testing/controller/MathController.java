package br.com.spring_unit_testing.controller;

import br.com.spring_unit_testing.exception.ResourceNotFoundException;
import br.com.spring_unit_testing.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {

    private final AtomicLong contador = new AtomicLong();
    
    @Autowired
    private MathService service;

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {
        if(!service.isNumeric(numberOne) || !service.isNumeric(numberTwo))
            throw new ResourceNotFoundException("Please set a numeric value");
        return service.convertToDouble(numberOne) + service.convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/subtract/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public double subtract(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {
        if(!service.isNumeric(numberOne) || !service.isNumeric(numberTwo))
            throw new ResourceNotFoundException("Please set a numeric value");
        return service.convertToDouble(numberOne) - service.convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public double multiplication(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {
        if(!service.isNumeric(numberOne) || !service.isNumeric(numberTwo))
            throw new ResourceNotFoundException("Please set a numeric value");
        return service.convertToDouble(numberOne) * service.convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public double division(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {
        if(!service.isNumeric(numberOne) || !service.isNumeric(numberTwo))
            throw new ResourceNotFoundException("Please set a numeric value");
        return service.convertToDouble(numberOne) / service.convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/sqrt/{numberOne}/", method = RequestMethod.GET)
    public double mean(
            @PathVariable(value = "numberOne") String numberOne
    ) {
        if(!service.isNumeric(numberOne)) throw new ResourceNotFoundException("Please set a numeric value");
        return Math.sqrt(service.convertToDouble(numberOne));
    }
    
}
