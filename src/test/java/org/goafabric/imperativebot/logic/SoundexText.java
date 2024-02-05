package org.goafabric.imperativebot.logic;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.ColognePhonetic;
import org.junit.jupiter.api.Test;

public class SoundexText {
    @Test
    public void test() throws EncoderException {
        ColognePhonetic phonetic = new ColognePhonetic();
        /*
        Soundex soundex = new Soundex();
        System.err.println( soundex.difference("meyers", "meiers"));

        System.out.println(phonetic.isEncodeEqual("meier", "meyer"));
        System.out.println(phonetic.isEncodeEqual("müller", "mueller"));

         */

        System.out.println("meyer: " + phonetic.encode("meyer"));
        System.out.println("meier: " + phonetic.encode("meier"));

        System.out.println("müller: " + phonetic.encode("müller"));
        System.out.println("mueller: " + phonetic.encode("mueller"));


        System.out.println("müller, hans: " + phonetic.encode("müller, hans"));
        System.out.println("mueller, hans: " + phonetic.encode("müller, hans"));
    }
}
