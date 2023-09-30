package com.project256.clannad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xingchen.Lin
 * @time 2023/9/30 10:24
 */
@Data
@AllArgsConstructor
public class FinetuneWithCharacter {
    String inputCharactor;
    String outputCharactor;
    Finetune finetune;
}
