package com.project256.clannad;

import com.project256.clannad.dto.CharacterQuote;
import com.project256.clannad.dto.Finetune;
import com.project256.clannad.dto.FinetuneWithCharacter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xingchen.Lin
 * @time 2023/9/16 17:10
 */
public class BuildFinetuneFile {

    List<CharacterQuote> characterQuotes;

    List<FinetuneWithCharacter> finetuneWithCharacters = new ArrayList<>();

    HashMap<String, List<Finetune>> finetuneMap = new HashMap<>(42);

    BuildFinetuneFile(List<CharacterQuote> quotes) {
        characterQuotes = quotes;
    }

    public BuildFinetuneFile prepare() {
        if (CollectionUtils.isEmpty(characterQuotes)) {
            return this;
        }

        String lastContinuedQuote = "";
        String continuedQuote = "";
        for (int i = 0; i < characterQuotes.size(); i++) {
            if (i > 0 && characterQuotes.get(i).getName().equals(characterQuotes.get(i - 1).getName())) {
                continuedQuote += "\n\n" + characterQuotes.get(i).getQuote();
                continue;
            }
            // name changed submit last quotes
            if (!StringUtils.isEmpty(continuedQuote)) {
                Finetune finetune = new Finetune(lastContinuedQuote, "", continuedQuote);
                finetuneWithCharacters.add(new FinetuneWithCharacter(characterQuotes.get(i - 1).getName(), characterQuotes.get(i).getName(), finetune));
                lastContinuedQuote = continuedQuote;
            }
            continuedQuote = characterQuotes.get(i).getQuote();
        }
        // submit last quotes
        if (!StringUtils.isEmpty(continuedQuote)) {
            Finetune finetune = new Finetune(lastContinuedQuote, "", continuedQuote);
            finetuneWithCharacters.add(new FinetuneWithCharacter(characterQuotes.get(characterQuotes.size() - 2).getName(), characterQuotes.get(characterQuotes.size() - 1).getName(), finetune));

        }
        return this;
    }

    public BuildFinetuneFile map() {
        for (FinetuneWithCharacter finetuneWithCharacter : finetuneWithCharacters) {
            String characterKey = finetuneWithCharacter.getInputCharactor() + "_" + finetuneWithCharacter.getOutputCharactor();
            List<Finetune> finetunes = finetuneMap.getOrDefault(characterKey, new ArrayList<>());
            finetunes.add(finetuneWithCharacter.getFinetune());
            finetuneMap.put(characterKey, finetunes);
        }
        return this;
    }

    public void split(List<Finetune> dataset, List<Finetune> validator) {
        for (String character : finetuneMap.keySet()) {
            List<Finetune> list = finetuneMap.get(character);
            Collections.shuffle(list);
            int splitPoint = (int) (list.size() * 0.7);
            dataset.addAll(list.subList(0, splitPoint));
            validator.addAll(list.subList(splitPoint, list.size()));
        }
    }
}
