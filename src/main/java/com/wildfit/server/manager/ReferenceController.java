package com.wildfit.server.manager;

import com.wildfit.server.domain.ReferenceDigest;
import com.wildfit.server.service.ReferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Reference API")
@RequestMapping("v1/reference")
public class ReferenceController {
    private final ReferenceService referenceService;

    @Operation(summary = "Get the IngredientType references")
    @GetMapping(value = "/ingredientType", produces = "application/json")
    public List<ReferenceDigest> getIngredientTypes() {
        return referenceService.getIngredientTypes();
    }

}
