package br.com.spring_unit_testing.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content){
        this.id = id;
        this.content = content;
    }
}
