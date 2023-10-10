package com.project256.clannad;

import com.google.gson.Gson;
import com.project256.clannad.dto.Finetune;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xingchen.Lin
 * @time 2023/9/16 16:07
 */
public class Main {
    public static void main(String[] args) throws IOException {
        List<Finetune> dataset = new ArrayList<>();
        List<Finetune> validator = new ArrayList<>();

        Parser parser = new Parser("pt_txt/CLANNAD_filted.txt");
        new BuildFinetuneFile(parser.parseLines()).prepare().map().split(dataset, validator);

        BufferedWriter finetuneOut = new BufferedWriter(new FileWriter("finetune_json/CLANNAD_LLaMA_finetune.json"));
        BufferedWriter validatorOut = new BufferedWriter(new FileWriter("validator_json/CLANNAD_LLaMA_validator.json"));

        finetuneOut.write(new Gson().toJson(dataset));
        validatorOut.write(new Gson().toJson(validator));

        finetuneOut.close();
        validatorOut.close();
    }
}