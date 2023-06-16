package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class SearchFoodItemDigest {

    private String foodName; //  Butter, Pure Irish, Unsalted,
    private String brandName; //  Kerrygold,
    private String brandNameItemName;
    private Integer servingQty; //  1,
    private String servingUnit; //  tbsp,
    private Integer calories; //  100,
    private String nixBrandName; //  Kerrygold,
    private String nixBrandId; //  51db37b7176fe9790a8989b4,
    private String nixItemId; //  52a15041d122497b50000a75,
    private PhotoDigest photo;
}
