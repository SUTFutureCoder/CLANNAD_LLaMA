package com.project256.clannad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xingchen.Lin
 * @time 2023/9/16 16:11
 */
@Data
@AllArgsConstructor
public class Finetune {
    String introduction;
    String input;
    String output;
}