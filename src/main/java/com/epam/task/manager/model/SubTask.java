package com.epam.task.manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.TextIndexed;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubTask {

    @TextIndexed(weight = 3)
    private String name;
    private String description;
}


