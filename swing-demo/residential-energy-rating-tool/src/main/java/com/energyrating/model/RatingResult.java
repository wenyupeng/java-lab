package com.energyrating.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RatingResult {
    private final double score;
    private final boolean compliant;
    private final String summary;
}
