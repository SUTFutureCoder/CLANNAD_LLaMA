package com.project256.clannad;

import com.project256.clannad.dto.CharacterQuote;
import com.project256.clannad.dto.Finetune;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xingchen.Lin
 * @time 2023/9/16 17:10
 */
public class BuildFinetuneFile {

    List<CharacterQuote> characterQuotes;

    BuildFinetuneFile(List<CharacterQuote> quotes) {
        characterQuotes = quotes;
    }

    public List<Finetune> build() {
        if (CollectionUtils.isEmpty(characterQuotes)) {
            return null;
        }

        List<Finetune> finetunes = new ArrayList<>();

        String lastContinuedQuote = "";
        String continuedQuote = "";
        for (int i = 0; i < characterQuotes.size(); i++) {
            if (i > 0 && characterQuotes.get(i).getName().equals(characterQuotes.get(i - 1).getName())) {
                continuedQuote += "\n\n" + characterQuotes.get(i).getQuote();
                continue;
            }
            // name changed submit last quotes
            if (!StringUtils.isEmpty(continuedQuote)) {
                finetunes.add(new Finetune(lastContinuedQuote, "", continuedQuote));
                lastContinuedQuote = continuedQuote;
            }
            continuedQuote = characterQuotes.get(i).getQuote();
        }
        // submit last quotes
        if (!StringUtils.isEmpty(continuedQuote)) {
            finetunes.add(new Finetune(lastContinuedQuote, "", continuedQuote));
        }
        return finetunes;
    }
}
