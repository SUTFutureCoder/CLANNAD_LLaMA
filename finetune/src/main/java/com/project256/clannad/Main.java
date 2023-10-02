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
        Parser parser = new Parser("CLANNAD_filted.txt");
        List<Finetune> dataset = new ArrayList<>();
        List<Finetune> validator = new ArrayList<>();
        new BuildFinetuneFile(parser.parseLines()).prepare().map().split(dataset, validator);

        BufferedWriter finetuneOut = new BufferedWriter(new FileWriter("finetune_json/CLANNAD_LLaMA_finetune.json"));
        BufferedWriter validatorOut = new BufferedWriter(new FileWriter("validator_json/CLANNAD_LLaMA_validator.json"));

        finetuneOut.write(new Gson().toJson(dataset));
        validatorOut.write(new Gson().toJson(validator));

        finetuneOut.close();
        validatorOut.close();
    }
}