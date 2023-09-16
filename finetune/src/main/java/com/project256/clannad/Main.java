package com.project256.clannad;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Xingchen.Lin
 * @time 2023/9/16 16:07
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser("CLANNAD.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter("finetune_json/CLANNAD_LLaMA_finetune.json"));
        out.write(new Gson().toJson(new BuildFinetuneFile(parser.parseLines()).build()));
        out.close();
    }
}