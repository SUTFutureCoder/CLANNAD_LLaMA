package com.project256.clannad;

import com.project256.clannad.dto.CharacterQuote;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Xingchen.Lin
 * @time 2023/9/16 16:12
 */
public class Parser {

    private final BufferedReader reader;

    Parser(String file) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
    }

    public List<CharacterQuote> parseLines() throws IOException {
        List<CharacterQuote> parseResult = new ArrayList<>();
        Set<String> characters = new HashSet<>();

        String currName = "";
        StringBuilder currQuote = new StringBuilder();
        boolean inQuote = false;

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (StringUtils.isBlank(line)) {
                continue;
            }

            // check character quote
            if (line.startsWith("【")) {
                // parse character name
                String character = line.substring(1, line.indexOf("】"));
                characters.add(character);
                currName = character;
                inQuote = true;
            } else if (line.contains("「")) {
                int idx = line.indexOf("「");
                if (idx == 0) {
                    continue;
                }
                if (characters.contains(line.substring(0, idx))) {
                    currName = line.substring(0, idx);
                    // replace line name
                    line = "【" + currName + "】" + line.substring(idx);
                    inQuote = true;
                }
            }

            // multirow quote
            if (!inQuote) {
                continue;
            }
            currQuote.append(line);
            if (line.endsWith("」") || line.endsWith("）")) {
                parseResult.add(new CharacterQuote(currName, currQuote.toString()));
                // reset
                inQuote = false;
                currName = "";
                currQuote = new StringBuilder();
            }
        }
        return parseResult;
    }
}
